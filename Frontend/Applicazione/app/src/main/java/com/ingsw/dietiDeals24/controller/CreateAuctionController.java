package com.ingsw.dietiDeals24.controller;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.createAuction.CreateAuctionDao;
import com.ingsw.dietiDeals24.network.createAuction.InsertImagesDao;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageAuctionBinder;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class CreateAuctionController implements RetroFitHolder {

    private CreateAuctionController() {
    }

    private static String uploadImageAndGetUrl(Uri fileUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imagesRef = storageRef.child("images/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imagesRef.putFile(fileUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return imagesRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
            } else {

            }
        });

        return urlTask.getResult().toString();
    }

    public static CompletableFuture<Boolean> createAuction(ReverseAuction newAuction, List<Uri> imageUris) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Auction auction = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();

                for (Uri imageUri : imageUris) {
                    String imageUrl = uploadImageAndGetUrl(imageUri);
                }

                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static CompletableFuture<Boolean> createAuction(DownwardAuction newAuction, List<Uri> imageUris) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Auction auction = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();

                for (Uri imageUri : imageUris) {
                    String imageUrl = uploadImageAndGetUrl(imageUri);
                }

                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static CompletableFuture<Boolean> createAuction(SilentAuction newAuction, List<Uri> imageUris) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Auction auction = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();

                for (Uri imageUri : imageUris) {
                    String imageUrl = uploadImageAndGetUrl(imageUri);
                }

                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static boolean isValidDecrementAmount(double initialPrice, double decrementAmount) {
        return decrementAmount <= initialPrice * 0.15;
    }
}