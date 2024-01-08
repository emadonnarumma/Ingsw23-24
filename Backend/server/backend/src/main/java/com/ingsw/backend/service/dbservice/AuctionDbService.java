package com.ingsw.backend.service.dbservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.repository.AuctionRepository;
import com.ingsw.backend.service.AuctionService;

@Service("mainAuctionService")
public class AuctionDbService implements AuctionService {
	
	private final AuctionRepository auctionRepository;

	@Autowired
	public AuctionDbService(AuctionRepository auctionRepository) {
		this.auctionRepository = auctionRepository;
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
		
		return auctionRepository.findAllByTitleContaining(keyword);
	}

	@Override
	public List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category) {
		
		return auctionRepository.findAllByTitleContainingAndCategory(keyword, category);
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


}
