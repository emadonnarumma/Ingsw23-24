package com.ingsw.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.Bid;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.model.SilentBid;

public interface BidRepository extends JpaRepository<Bid, Integer> {

	List<SilentBid> findAllSilentBidsByBuyer(Buyer buyer);

	List<ReverseBid> findAllReverseBidsBySeller(Seller seller);

	List<SilentBid> findAllSilentBidsBySilentAuction(SilentAuction auction);

	List<ReverseBid> findAllReverseBidsByReverseAuction(ReverseAuction auction);

}
