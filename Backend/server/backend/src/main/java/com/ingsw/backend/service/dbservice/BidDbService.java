package com.ingsw.backend.service.dbservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.model.SilentBid;
import com.ingsw.backend.repository.BidRepository;
import com.ingsw.backend.service.BidService;

@Service("mainBidService")
public class BidDbService implements BidService {
	
	private final BidRepository bidRepository;


	public BidDbService(BidRepository bidRepository) {
		this.bidRepository = bidRepository;
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
	public List<ReverseBid> getAllReverseBidsByReverseAuction(ReverseAuction auction) {
	    return bidRepository.findAllReverseBidsByReverseAuction(auction);
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
		
		return bidRepository.save(reverseBid);
	}

}
