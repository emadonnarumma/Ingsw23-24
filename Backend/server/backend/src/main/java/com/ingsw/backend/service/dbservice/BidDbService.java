package com.ingsw.backend.service.dbservice;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.ingsw.backend.model.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.BidStatus;
import com.ingsw.backend.repository.AuctionRepository;
import com.ingsw.backend.repository.BidRepository;
import com.ingsw.backend.service.BidService;

@Service("mainBidService")
public class BidDbService implements BidService {
	
	private final BidRepository bidRepository;
	
	private final AuctionRepository auctionRepository;


	public BidDbService(BidRepository bidRepository, AuctionRepository auctionRepository) {
		this.bidRepository = bidRepository;
		this.auctionRepository = auctionRepository;
	}

	@Override
	public DownwardBid getWinningDownwardBidByAuctionId(Integer auctionId) {
		return bidRepository.getWinningDownwardBidByAuctionId(auctionId);
	}

	@Override
	public ReverseBid getWinningReverseBidByAuctionId(Integer auctionId) {
		return bidRepository.getWinningReverseBidByAuctionId(auctionId);
	}

	@Override
	public SilentBid getWinningSilentBidByAuctionId(Integer auctionId) {
        return bidRepository.getWinningSilentBidByAuctionId(auctionId);
	}

	@Override
	public List<SilentBid> getAllSilentBidsByBuyer(Buyer buyer) {
	    return bidRepository.findAllSilentBidsByBuyer(buyer);
	}

	@Override
	public List<ReverseBid> getAllReverseBidsBySeller(Seller seller) {
	    return bidRepository.findAllReverseBidsBySeller(seller);
	}

	@Override
	public List<SilentBid> getAllSilentBidsBySilentAuction(SilentAuction auction) {
	    return bidRepository.findAllSilentBidsBySilentAuction(auction);
	}

	@Override
	public ReverseBid getMinReverseBidByReverseAuctionId(Integer id) {
		ReverseBid reverseBid = bidRepository.findPendingReverseBidByAuctionId(id);
		return reverseBid;
	}

	@Override
	public Boolean delete(Integer id) {
	    
		if (!bidRepository.existsById(id)) {
	        return false;
	    }
	    
	    bidRepository.deleteById(id);
	    
	    return true;
	}

	@Override
	public SilentBid addSilentBid(SilentBid silentBid) {
		
		return bidRepository.save(silentBid);
	}

	@Override
	public ReverseBid addReverseBid(ReverseBid reverseBid) {
		
		List<ReverseBid> oldReverseBids = reverseBid.getReverseAuction().getReceivedBids();
		
		for (ReverseBid oldBid: oldReverseBids) {
			
			if (!oldBid.equals(reverseBid)) {
				
				oldBid.setStatus(BidStatus.DECLINED);
			}			
		}
		
		bidRepository.saveAll(oldReverseBids);
		
		return bidRepository.save(reverseBid);
	}

	@Override
	public DownwardBid makeDownwardAuctionPayment(DownwardBid downwardBid) {

		DownwardAuction auction = downwardBid.getDownwardAuction();

		auction.setStatus(AuctionStatus.SUCCESSFUL);

		downwardBid.setStatus(BidStatus.ACCEPTED);

		auctionRepository.save(auction);

		return bidRepository.save(downwardBid);
	}


	@Override
	public Boolean acceptSilentBid(Integer id) {
		
		Optional<Bid> optionalBid = bidRepository.findById(id);
		
		if (optionalBid.isEmpty() || !(optionalBid.get() instanceof SilentBid)){
		
			return false;
		} 
		
		SilentBid acceptedBid = (SilentBid) optionalBid.get();
		
		acceptedBid.setStatus(BidStatus.ACCEPTED);
		
		SilentAuction auction = acceptedBid.getSilentAuction();
		
		auction.setStatus(AuctionStatus.SUCCESSFUL);
		
		
		List<SilentBid> receivedBids = auction.getReceivedBids();
		
		for (SilentBid receivedBid: receivedBids) {
			
			if (!receivedBid.equals(acceptedBid)) {
			
				receivedBid.setStatus(BidStatus.DECLINED);
				
			}
		}
		
		
		bidRepository.save(acceptedBid);
		
		bidRepository.saveAll(receivedBids);
		
		
		auctionRepository.save(auction);
		
		return true;
		
	}

	@Override
	public Boolean declineSilentBid(Integer id) {
		
		Optional<Bid> optionalBid = bidRepository.findById(id);
		
		if (optionalBid.isEmpty() || !(optionalBid.get() instanceof SilentBid)){
		
			return false;
		} 
		
		SilentBid declinedBid = (SilentBid) optionalBid.get();
		
		declinedBid.setStatus(BidStatus.DECLINED);
		
		bidRepository.save(declinedBid);
		
		return true;
	}
	
	@Override
	public Boolean isSilentBidWithdrawable(Integer id) {
		
		Optional<Bid> optionalBid = bidRepository.findById(id);
		
		if (optionalBid.isEmpty() || !(optionalBid.get() instanceof SilentBid)){
		
			return false;
		} 
		
	    SilentBid bid = (SilentBid) optionalBid.get();
	    
	    long currentTime = System.currentTimeMillis();
	    
	    long bidElapsedTime = currentTime - bid.getTimestamp().getTime();
	    
	    long withdrawalTimeMillis = bid.getSilentAuction().getWithdrawalTime() * 1000;
	    
	    return (bidElapsedTime <= withdrawalTimeMillis);
	    
	}
	
	@Scheduled(fixedRate = 60000) //executed every minute
	@Transactional
	public void updateSilentBidStatuses() {
	    
		List<SilentBid> expiringBids = bidRepository.findExpiredSilentBidsByStatus(BidStatus.PENDING, new Timestamp(System.currentTimeMillis()));
		
		for (SilentBid bid: expiringBids) {
			
			bid.setStatus(BidStatus.EXPIRED);
			
			bidRepository.save(bid);
		}
	}
}
