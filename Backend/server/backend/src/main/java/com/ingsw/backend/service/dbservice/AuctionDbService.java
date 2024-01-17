package com.ingsw.backend.service.dbservice;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.BidStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.repository.AuctionRepository;
import com.ingsw.backend.repository.BidRepository;
import com.ingsw.backend.service.AuctionService;

@Service("mainAuctionService")
public class AuctionDbService implements AuctionService {
	
	private final AuctionRepository auctionRepository;
	
	private final BidRepository bidRepository;


	public AuctionDbService(AuctionRepository auctionRepository, BidRepository bidRepository) {
		this.auctionRepository = auctionRepository;
		this.bidRepository = bidRepository;
	}

	@Override
	public List<Auction> getAllAuctions() {
		
		return auctionRepository.findAll();
	}

	@Override
	public List<Auction> getAuctionsByCategory(Category category) {
		
		return auctionRepository.findAllByCategory(category);
	}

	@Override
	public List<Auction> getAuctionsByTitleContaining(String keyword) {
		
		return auctionRepository.findAllByTitleContainingIgnoreCase(keyword);
	}

	@Override
	public List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category) {
		
		return auctionRepository.findAllByTitleContainingIgnoreCaseAndCategory(keyword, category);
	}

	@Override
	public Auction addAuction(Auction auction) {
		
		return auctionRepository.save(auction);
	}

	@Override
	public Boolean delete(int id) {
	    
	    if (!auctionRepository.existsById(id)) {
	        return false;
	    }
	    
	    auctionRepository.deleteById(id);
	    
	    return true;
	}

	@Override
	public Optional<Auction> findById(Integer auctionId) {
		
		return auctionRepository.findById(auctionId);
	}
	
	@Override
	public Boolean buyDownwardAuctionNow(Integer auctionId) {
		
		Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
		
		if (optionalAuction.isEmpty() || !(optionalAuction.get() instanceof DownwardAuction)){
			
			return false;
		} 
		
		DownwardAuction auction = (DownwardAuction) optionalAuction.get();
		
		auction.setStatus(AuctionStatus.SUCCESSFUL);
		
		auctionRepository.save(auction);
		
		return true;
	}

	
	
	@Scheduled(fixedRate = 60000) //executed every minute
	@Transactional
	public void updateSilentAuctionsStatus() {
	    
		List<SilentAuction> expiringAuctions = auctionRepository.findSilentByStatusAndExpirationDateBefore(AuctionStatus.IN_PROGRESS, new Timestamp(System.currentTimeMillis()));
	    
	    for (SilentAuction auction : expiringAuctions) {
	    		    				            
    		auction.setStatus(AuctionStatus.FAILED);
            
    		auctionRepository.save(auction);        
	    }
	}
	
	@Scheduled(fixedRate = 60000) //executed every minute
	@Transactional
	public void updateDownwardAuctionsDetails() {
	    
		List<DownwardAuction> auctions = auctionRepository.findAllByStatus(AuctionStatus.IN_PROGRESS);
	    
	    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	    
	    for (DownwardAuction auction : auctions) {
	        
	        if (currentTime.after(auction.getNextDecrement())) {
	            
	            auction.setCurrentPrice(auction.getCurrentPrice() - auction.getDecrementAmount());
	            
	            long nextDecrementTimeMillis = auction.getNextDecrement().getTime() + (auction.getDecrementTime() * 1000);
	            
	            auction.setNextDecrement(new Timestamp(nextDecrementTimeMillis));
	            
	            if (auction.getCurrentPrice() <= auction.getSecretMinimumPrice()) {
	                
	                auction.setStatus(AuctionStatus.FAILED);
	            }
	           
	            auctionRepository.save(auction);
	        }
	    }
	}
	
	@Scheduled(fixedRate = 60000) //executed every minute
	@Transactional
	public void updateReverseAuctionsStatus() {
		
		List<ReverseAuction> expiringAuctions = auctionRepository.findReverseByStatusAndExpirationDateBefore(AuctionStatus.IN_PROGRESS, new Timestamp(System.currentTimeMillis()));
	
		for (ReverseAuction auction: expiringAuctions) {
			
			List<ReverseBid> receivedBids = auction.getReceivedBids();
			
			if (receivedBids.isEmpty()) {
				
				auction.setStatus(AuctionStatus.FAILED);
				
			} else {
				
				ReverseBid minBid = Collections.min(receivedBids, Comparator.comparing(ReverseBid::getMoneyAmount));
				
	            minBid.setStatus(BidStatus.ACCEPTED);
	            
	            auction.setStatus(AuctionStatus.SUCCESSFUL);
	            
	            bidRepository.save(minBid);			          
			}
			
			auctionRepository.save(auction);
		}
	}


	
	
//    receivedBids.sort(Comparator.comparing(ReverseBid::getMoneyAmount));
//    
//    receivedBids.get(0).setStatus(BidStatus.ACCEPTED);
    
//    for (int i = 1; i < receivedBids.size(); i++) {
//    	
//    	receivedBids.get(i).setStatus(BidStatus.DECLINED);
//    }
//    
    //salva la lista di bid
    
//    for (ReverseBid bid: receivedBids) {
//    	
//    	bidRepository.save(bid);
//    }
//    
}
