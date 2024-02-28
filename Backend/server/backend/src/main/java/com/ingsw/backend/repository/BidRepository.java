package com.ingsw.backend.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingsw.backend.enumeration.BidStatus;
import com.ingsw.backend.model.Bid;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.model.SilentBid;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends JpaRepository<Bid, Integer> {

	List<SilentBid> findAllSilentBidsByBuyer(Buyer buyer);

	List<ReverseBid> findAllReverseBidsBySeller(Seller seller);

	List<SilentBid> findAllSilentBidsBySilentAuction(SilentAuction auction);

	List<ReverseBid> findAllReverseBidsByReverseAuction(ReverseAuction auction);

	List<SilentBid> findByStatus(BidStatus status);

	@Query("SELECT rb FROM ReverseBid rb WHERE rb.reverseAuction.idAuction = :auctionId AND rb.status = 'PENDING'")
	ReverseBid findPendingReverseBidByAuctionId(@Param("auctionId") Integer auctionId);

	@Query("SELECT sb FROM SilentBid sb WHERE sb.status = :status AND sb.silentAuction.expirationDate < :currentTimestamp")
    List<SilentBid> findExpiredSilentBidsByStatus(BidStatus status, Timestamp currentTimestamp);

	@Query("SELECT sb FROM SilentBid sb WHERE sb.silentAuction.idAuction = :auctionId AND sb.status = 'ACCEPTED'")
	SilentBid getWinningSilentBidByAuctionId(@Param("auctionId") Integer auctionId);

	@Query("SELECT rb FROM ReverseBid rb WHERE rb.reverseAuction.idAuction = :auctionId AND rb.status = 'ACCEPTED'")
	ReverseBid getWinningReverseBidByAuctionId(@Param("auctionId") Integer auctionId);


}


