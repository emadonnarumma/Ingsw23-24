package com.ingsw.dietiDeals24.controller;



import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyAuctionsDao;
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


    public SearchAuctionsController() {
    }

    public static CompletableFuture<List<SilentAuction>> getAllSilentAuctions() {
        return CompletableFuture.supplyAsync(() -> {

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

                    silentAuctions = auctions;
                    return silentAuctions;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }

    public static CompletableFuture<List<ReverseAuction>> getAllReverseAuctions() {
        return CompletableFuture.supplyAsync(() -> {

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

                    reverseAuctions = auctions;
                    return reverseAuctions;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }

    public static CompletableFuture<List<DownwardAuction>> getAllDownwardAuctions() {
        return CompletableFuture.supplyAsync(() -> {

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

                    downwardAuctions = auctions;
                    return downwardAuctions;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }
}
