package com.ingsw.backend.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.SilentAuction;


public interface AuctionRepository extends JpaRepository<Auction, Integer>{
	public List<ReverseAuction> findReverseByOwnerEmail(String email);
	public List<SilentAuction> findSilentByOwnerEmail(String email);
	public List<DownwardAuction> findDownwardByOwnerEmail(String email);
	
	public List<SilentAuction> findAllSilentByStatus(AuctionStatus status);
	
	public List<ReverseAuction> findAllReverseByStatus(AuctionStatus status);
	
	public List<DownwardAuction> findAllDownwardByStatus(AuctionStatus status);

	public List<Auction> findAllByCategory(Category category);
	public List<Auction> findAllByTitleContainingIgnoreCase(String keyword);
	public List<Auction> findAllByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
	
//	public List<SilentAuction> findAllSilentByCategory(Category category);
//	public List<SilentAuction> findAllSilentByTitleContainingIgnoreCase(String keyword);
//	public List<SilentAuction> findAllSilentByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
//	
//	public List<ReverseAuction> findAllReverseByCategory(Category category);
//	public List<ReverseAuction> findAllReverseByTitleContainingIgnoreCase(String keyword);
//	public List<ReverseAuction> findAllReverseByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
//	
//	public List<DownwardAuction> findAllDownwardByCategory(Category category);
//	public List<DownwardAuction> findAllDownwardByTitleContainingIgnoreCase(String keyword);
//	public List<DownwardAuction> findAllDownwardByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
	
	public List<SilentAuction> findAllSilentByStatusAndCategory(AuctionStatus status, Category category);
    public List<SilentAuction> findAllSilentByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
    public List<SilentAuction> findAllSilentByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);
    
    public List<ReverseAuction> findAllReverseByStatusAndCategory(AuctionStatus status, Category category);
    public List<ReverseAuction> findAllReverseByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
    public List<ReverseAuction> findAllReverseByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);
    
    public List<DownwardAuction> findAllDownwardByStatusAndCategory(AuctionStatus status, Category category);
    public List<DownwardAuction> findAllDownwardByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
    public List<DownwardAuction> findAllDownwardByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);

	public List<SilentAuction> findSilentByStatusAndExpirationDateBefore(AuctionStatus status, Timestamp currentTimestamp);
    public List<ReverseAuction> findReverseByStatusAndExpirationDateBefore(AuctionStatus status, Timestamp currentTimestamp);
	public List<DownwardAuction> findAllByStatus(AuctionStatus status);
}
