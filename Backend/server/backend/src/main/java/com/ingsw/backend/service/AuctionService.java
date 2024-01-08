package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;

public interface AuctionService {
	
	public List<Auction> getAllAuctions();
	
	public List<Auction> getAuctionsByCategory(Category category);
	
	public List<Auction> getAuctionsByTitleContaining(String keyword);
	
	public List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category);
	
	public Auction addAuction(Auction auction);
	
	public Boolean delete(int id);

}
