package com.ingsw.backend.service;

import java.util.List;
import java.util.Optional;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;

public interface AuctionService {
	
	public List<Auction> getAllAuctions();
	
	public List<Auction> getAuctionsByCategory(Category category);
	
	public List<Auction> getAuctionsByTitleContaining(String keyword);
	
	public List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category);
	
	public Auction addAuction(Auction auction);
	
	public Boolean delete(int id);

	public Optional<Auction> findById(Integer auctionId);

	public Boolean buyDownwardAuctionNow(Integer auctionId);
	
	public Long getRemainingSecondsForAuction(Integer auctionId);

	public List<Image> getImagesByAuction(Auction auction);

}
