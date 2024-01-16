package com.ingsw.backend.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.SilentAuction;


public interface AuctionRepository extends JpaRepository<Auction, Integer>{

	
	public List<Auction> findAllByCategory(Category category);
	
	public List<Auction> findAllByTitleContainingIgnoreCase(String keyword);
	
	public List<Auction> findAllByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);

	public List<SilentAuction> findByStatusAndExpirationDateBefore(AuctionStatus status, Timestamp currentTimestamp);

	public List<DownwardAuction> findAllByStatus(AuctionStatus status);
	
	
}
