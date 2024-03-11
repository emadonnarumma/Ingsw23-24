package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.*;

public interface BidService {

	List<SilentBid> getAllSilentBidsByBuyer(Buyer buyer);
	List<DownwardBid> getAllDownwardBidsByBuyer(Buyer buyer);
	List<ReverseBid> getAllReverseBidsBySeller(Seller seller);

	List<SilentBid> getAllSilentBidsBySilentAuction(SilentAuction auction);
	List<SilentBid> getInProgressSilentBidsByAuctionId(Integer auctionId);
	List<ReverseBid> getInProgressReverseBidsByAuctionId(Integer auctionId);
	ReverseBid getMinReverseBidByReverseAuctionId(Integer id);

	ReverseBid getWinningReverseBidByAuctionId(Integer auctionId);
	SilentBid getWinningSilentBidByAuctionId(Integer auctionId);
	DownwardBid getWinningDownwardBidByAuctionId(Integer auctionId);

	Boolean acceptSilentBid(Integer id);
	Boolean declineSilentBid(Integer id);

	SilentBid addSilentBid(SilentBid silentBid);
	ReverseBid addReverseBid(ReverseBid reverseBid);

	Boolean delete(Integer id);

	DownwardBid makeDownwardAuctionPayment(DownwardBid downwardBid);

	Boolean isSilentBidWithdrawable(Integer id);
}
