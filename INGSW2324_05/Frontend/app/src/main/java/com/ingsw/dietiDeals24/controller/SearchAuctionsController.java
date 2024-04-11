package com.ingsw.dietiDeals24.controller;


import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyAuctionDetailsDao;
import com.ingsw.dietiDeals24.network.dao.SearchAuctionsDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class SearchAuctionsController implements RetroFitHolder {
    private static List<SilentAuction> silentAuctions = new ArrayList<>();
    private static List<ReverseAuction> reverseAuctions = new ArrayList<>();
    private static List<DownwardAuction> downwardAuctions = new ArrayList<>();

    private static List<SilentAuction> silentFilteredAuctions = new ArrayList<>();
    private static List<ReverseAuction> reverseFilteredAuctions = new ArrayList<>();
    private static List<DownwardAuction> downwardFilteredAuctions = new ArrayList<>();

    private static boolean updatedSilent = false;
    private static boolean updatedDownward = false;
    private static boolean updatedReverse = false;

    public static void setUpdatedSilent(boolean updatedSilent) {
        SearchAuctionsController.updatedSilent = updatedSilent;
    }

    public static void setUpdatedDownward(boolean updatedDownward) {
        SearchAuctionsController.updatedDownward = updatedDownward;
    }

    public static void setUpdatedReverse(boolean updatedReverse) {
        SearchAuctionsController.updatedReverse = updatedReverse;
    }

    public static void setUpdatedAll(boolean b) {
        updatedSilent = b;
        updatedDownward = b;
        updatedReverse = b;
    }

    public SearchAuctionsController() {
    }

    public static CompletableFuture<List<SilentAuction>> getAllSilentAuctions() {
        return CompletableFuture.supplyAsync(() -> {
            if (!updatedSilent) {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<SilentAuction>> response = searchAuctionsDao.getAllSilentAuctions(TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();

                    for (SilentAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedSilent = true;
                        silentAuctions = auctions;
                        return silentAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return silentAuctions;
            }
        });
    }

    public static CompletableFuture<List<ReverseAuction>> getAllReverseAuctions() {
        return CompletableFuture.supplyAsync(() -> {
            if (!updatedReverse) {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<ReverseAuction>> response = searchAuctionsDao.getAllReverseAuctions(TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();
                    for (ReverseAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedReverse = true;
                        reverseAuctions = auctions;
                        return reverseAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return reverseAuctions;
            }
        });
    }

    public static CompletableFuture<List<DownwardAuction>> getAllDownwardAuctions() {
        return CompletableFuture.supplyAsync(() -> {
            if (!updatedDownward) {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<DownwardAuction>> response = searchAuctionsDao.getAllDownwardAuctions(TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    for (DownwardAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedDownward = true;
                        downwardAuctions = auctions;
                        return downwardAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return downwardAuctions;
            }
        });
    }

    public static CompletableFuture<List<SilentAuction>> getAllSilentAuctionsByCategory
            (Category category) {
        return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<SilentAuction>> response = searchAuctionsDao.getAllSilentAuctionsByCategory(category, TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();

                    for (SilentAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        silentFilteredAuctions = auctions;
                        return silentFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }
                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<ReverseAuction>> getAllReverseAuctionsByCategory
        (Category category){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<ReverseAuction>> response = searchAuctionsDao.getAllReverseAuctionsByCategory(category, TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();

                    for (ReverseAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        reverseFilteredAuctions = auctions;
                        return reverseFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<DownwardAuction>> getAllDownwardAuctionsByCategory
        (Category category){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<DownwardAuction>> response = searchAuctionsDao.getAllDownwardAuctionsByCategory(category, TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    for (DownwardAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        downwardFilteredAuctions = auctions;
                        return downwardFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<SilentAuction>> getAllSilentAuctionsByKeyword (String
        keyword){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<SilentAuction>> response = searchAuctionsDao.getAllSilentAuctionsByKeyword(keyword, TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();

                    for (SilentAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        silentFilteredAuctions = auctions;
                        return silentFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<ReverseAuction>> getAllReverseAuctionsByKeyword (String
        keyword){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<ReverseAuction>> response = searchAuctionsDao.getAllReverseAuctionsByKeyword(keyword, TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();

                    for (ReverseAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        reverseFilteredAuctions = auctions;
                        return reverseFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<DownwardAuction>> getAllDownwardAuctionsByKeyword
        (String keyword){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<DownwardAuction>> response = searchAuctionsDao.getAllDownwardAuctionsByKeyword(keyword, TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    for (DownwardAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        downwardFilteredAuctions = auctions;
                        return downwardFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<SilentAuction>> getAllSilentAuctionsByKeywordAndCategory
        (String keyword, Category category){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<SilentAuction>> response = searchAuctionsDao.getAllSilentAuctionsByKeywordAndCategory(keyword, category, TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();

                    for (SilentAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        silentFilteredAuctions = auctions;
                        return silentFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();

            });
        }

        public static CompletableFuture<List<ReverseAuction>> getAllReverseAuctionsByKeywordAndCategory
        (String keyword, Category category){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<ReverseAuction>> response = searchAuctionsDao.getAllReverseAuctionsByKeywordAndCategory(keyword, category, TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();

                    for (ReverseAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        reverseFilteredAuctions = auctions;
                        return reverseFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();

            });
        }

        public static CompletableFuture<List<DownwardAuction>> getAllDownwardAuctionsByKeywordAndCategory
        (String keyword, Category category){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<DownwardAuction>> response = searchAuctionsDao.getAllDownwardAuctionsByKeywordAndCategory(keyword, category, TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    for (DownwardAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = searchAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        downwardFilteredAuctions = auctions;
                        return downwardFilteredAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();

            });
        }

        public static CompletableFuture<List<ReverseAuction>> getInProgressOtherUserReverseAuctions
        (String email){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<ReverseAuction>> response = searchAuctionsDao.getInProgressOtherUserReverseAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();

                    if (response.isSuccessful() && auctions != null) {
                        return auctions;
                    } else if (response.code() == 403 || auctions == null) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<SilentAuction>> getInProgressOtherUserSilentAuctions
        (String email){
            return CompletableFuture.supplyAsync(() -> {

                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<SilentAuction>> response = searchAuctionsDao.getInProgressOtherUserSilentAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();

                    if (response.isSuccessful() && auctions != null) {
                        return auctions;
                    } else if (response.code() == 403 || auctions == null) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<List<DownwardAuction>> getInProgressOtherUserDownwardAuctions
        (String email){
            return CompletableFuture.supplyAsync(() -> {

                try {
                    SearchAuctionsDao searchAuctionsDao = retrofit.create(SearchAuctionsDao.class);
                    Response<List<DownwardAuction>> response = searchAuctionsDao.getInProgressOtherUserDownwardAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    if (response.isSuccessful() && auctions != null) {
                        return auctions;
                    } else if (response.code() == 403 || auctions == null) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            });
        }

        public static CompletableFuture<ReverseBid> getCurrentReverseBid (ReverseAuction
        reverseAuction){
            return CompletableFuture.supplyAsync(() -> {
                try {
                    MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                    Response<ReverseBid> response = myAuctionDetailsDao.getMinPricedReverseBidsByAuctionId(reverseAuction.getIdAuction(), TokenHolder.getAuthToken()).execute();

                    if (response.isSuccessful()) {
                        return response.body();
                    } else if (response.code() == 404) {
                        return null;
                    } else if (response.code() == 403) {
                        throw new AuthenticationException("Token scaduto");
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return null;
            });
        }
    }
