package com.ingsw.backend.service;

import java.util.List;
import java.util.Optional;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.SilentAuction;

public interface AuctionService {
	public List<Auction> getAllAuctions();
	
	public List<SilentAuction> getAllSilentAuctions();
	public List<ReverseAuction> getAllReverseAuctions();
	public List<DownwardAuction> getAllDownwardAuctions();
	
	
	
	public List<Auction> getAuctionsByCategory(Category category);
	public List<Auction> getAuctionsByTitleContaining(String keyword);
	public List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category);
	
	public List<SilentAuction> getSilentAuctionsByCategory(Category category);
	public List<SilentAuction> getSilentAuctionsByTitleContaining(String keyword);
	public List<SilentAuction> getSilentAuctionsByTitleContainingAndCategory(String keyword, Category category);
	
	public List<DownwardAuction> getDownwardAuctionsByCategory(Category category);
	public List<DownwardAuction> getDownwardAuctionsByTitleContaining(String keyword);
	public List<DownwardAuction> getDownwardAuctionsByTitleContainingAndCategory(String keyword, Category category);
	
	public List<ReverseAuction> getReverseAuctionsByCategory(Category category);
	public List<ReverseAuction> getReverseAuctionsByTitleContaining(String keyword);
	public List<ReverseAuction> getReverseAuctionsByTitleContainingAndCategory(String keyword, Category category);
	

	public List<ReverseAuction> getReverseAuctionsByOwnerEmail(String email);
	public List<SilentAuction> getSilentAuctionsByOwnerEmail(String email);
	public List<DownwardAuction> getDownwardAuctionsByOwnerEmail(String email);


	public Long getRemainingSecondsForAuction(Integer auctionId);

	public Auction addAuction(Auction auction);

	public Boolean delete(int id);

	public Optional<Auction> findById(Integer auctionId);

	public Boolean buyDownwardAuctionNow(Integer auctionId);



}
