package com.ingsw.backend.repository;

import java.sql.Timestamp;
import java.util.List;

import com.ingsw.backend.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.enumeration.AuctionStatus;
import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.SilentAuction;
import org.springframework.data.jpa.repository.Query;


public interface AuctionRepository extends JpaRepository<Auction, Integer>{
	List<ReverseAuction> findReverseByOwnerEmailAndOwnerRole(String email, Role role);
	List<SilentAuction> findSilentByOwnerEmailAndOwnerRole(String email, Role role);
	List<DownwardAuction> findDownwardByOwnerEmailAndOwnerRole(String email, Role role);

	@Query("SELECT ra FROM ReverseAuction ra WHERE ra.owner.email = :email AND ra.owner.role = :role AND ra.status = 'IN_PROGRESS'")
	List<ReverseAuction> findInProgressReverseByOwnerEmailAndOwnerRole(String email, Role role);
	@Query("SELECT sa FROM SilentAuction sa WHERE sa.owner.email = :email AND sa.owner.role = :role AND sa.status = 'IN_PROGRESS'")
	List<SilentAuction> findInProgressSilentByOwnerEmailAndOwnerRole(String email, Role role);
	@Query("SELECT da FROM DownwardAuction da WHERE da.owner.email = :email AND da.owner.role = :role AND da.status = 'IN_PROGRESS'")
	List<DownwardAuction> findInProgressDownwardByOwnerEmailAndOwnerRole(String email, Role role);

	List<SilentAuction> findAllSilentByStatus(AuctionStatus status);
	List<ReverseAuction> findAllReverseByStatus(AuctionStatus status);
	List<DownwardAuction> findAllDownwardByStatus(AuctionStatus status);

	List<Auction> findAllByCategory(Category category);
	List<Auction> findAllByTitleContainingIgnoreCase(String keyword);
	List<Auction> findAllByTitleContainingIgnoreCaseAndCategory(String keyword, Category category);
	
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
	
	List<SilentAuction> findAllSilentByStatusAndCategory(AuctionStatus status, Category category);
	List<SilentAuction> findAllSilentByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
	List<SilentAuction> findAllSilentByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);
    
	List<ReverseAuction> findAllReverseByStatusAndCategory(AuctionStatus status, Category category);
	List<ReverseAuction> findAllReverseByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
	List<ReverseAuction> findAllReverseByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);
    
	List<DownwardAuction> findAllDownwardByStatusAndCategory(AuctionStatus status, Category category);
	List<DownwardAuction> findAllDownwardByStatusAndTitleContainingIgnoreCase(AuctionStatus status, String keyword);
	List<DownwardAuction> findAllDownwardByStatusAndTitleContainingIgnoreCaseAndCategory(AuctionStatus status, String keyword, Category category);

	List<SilentAuction> findSilentByStatusAndExpirationDateBefore(AuctionStatus status, Timestamp currentTimestamp);
	List<ReverseAuction> findReverseByStatusAndExpirationDateBefore(AuctionStatus status, Timestamp currentTimestamp);
	List<DownwardAuction> findAllByStatus(AuctionStatus status);
}
