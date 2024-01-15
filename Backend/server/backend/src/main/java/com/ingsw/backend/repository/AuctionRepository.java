package com.ingsw.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;


public interface AuctionRepository extends JpaRepository<Auction, Integer>{

	
	public List<Auction> findAllByCategory(Category category);
	
	public List<Auction> findAllByTitleContainingIgnoreCase(String keyword);
	
	public List<Auction> findAllByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
	
	
}
