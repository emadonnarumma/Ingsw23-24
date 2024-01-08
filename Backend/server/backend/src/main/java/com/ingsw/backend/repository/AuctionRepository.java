package com.ingsw.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer>{

	
	public List<Auction> findAllByCategory(Category category);
	
	public List<Auction> findAllByTitleContaining(String keyword);
	
	public List<Auction> findAllByTitleContainingAndCategory(String keyword, Category category);
	
}
