package com.ingsw.backend.auction;

import com.ingsw.backend.enumeration.Category;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.DownwardAuction;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.SilentAuction;

import java.util.List;
import java.util.Optional;

public interface AuctionService {
    List<Auction> getAllAuctions();
    List<SilentAuction> getAllSilentAuctions();
    List<ReverseAuction> getAllReverseAuctions();
    List<DownwardAuction> getAllDownwardAuctions();

    List<Auction> getAuctionsByCategory(Category category);
    List<Auction> getAuctionsByTitleContaining(String keyword);
    List<Auction> getAuctionsByTitleContainingAndCategory(String keyword, Category category);

    List<SilentAuction> getSilentAuctionsByCategory(Category category);
    List<SilentAuction> getSilentAuctionsByTitleContaining(String keyword);
    List<SilentAuction> getSilentAuctionsByTitleContainingAndCategory(String keyword, Category category);

    List<DownwardAuction> getDownwardAuctionsByCategory(Category category);
    List<DownwardAuction> getDownwardAuctionsByTitleContaining(String keyword);
    List<DownwardAuction> getDownwardAuctionsByTitleContainingAndCategory(String keyword, Category category);

    List<ReverseAuction> getReverseAuctionsByCategory(Category category);
    List<ReverseAuction> getReverseAuctionsByTitleContaining(String keyword);
    List<ReverseAuction> getReverseAuctionsByTitleContainingAndCategory(String keyword, Category category);
    List<ReverseAuction> getReverseAuctionsByOwnerEmail(String email);

    List<SilentAuction> getSilentAuctionsByOwnerEmail(String email);

    List<DownwardAuction> getDownwardAuctionsByOwnerEmail(String email);

    List<ReverseAuction> getInProgressReverseAuctionsByOwnerEmail(String email);
    List<SilentAuction> getInProgressSilentAuctionsByOwnerEmail(String email);
    List<DownwardAuction> getInProgressDownwardAuctionsByOwnerEmail(String email);

    Long getRemainingSecondsForAuction(Integer auctionId);

    Auction addAuction(Auction auction);

    Boolean delete(int id);

    Optional<Auction> findById(Integer auctionId);

    Boolean buyDownwardAuctionNow(Integer auctionId);
}
