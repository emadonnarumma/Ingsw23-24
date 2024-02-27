package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.model.SilentBid;

public interface BidService {

	public List<SilentBid> getAllSilentBidsByBuyer(Buyer buyer);
	
	public List<ReverseBid> getAllReverseBidsBySeller(Seller seller);
	
	public List<SilentBid> getAllSilentBidsBySilentAuction(SilentAuction auction);

	public ReverseBid getMinReverseBidByReverseAuctionId(Integer id);

	public SilentBid addSilentBid(SilentBid silentBid);
	
	public ReverseBid addReverseBid(ReverseBid reverseBid);
	
	public Boolean delete(Integer id);
	
	public Boolean acceptSilentBid(Integer id);
	
	public Boolean declineSilentBid(Integer id);
	
	
	public Boolean isSilentBidWithdrawable(Integer id);
}
