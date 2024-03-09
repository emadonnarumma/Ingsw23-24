package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.Bid;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class MyBidsController {
    private static LinkedList<Bid> myBids = null;

    public static CompletableFuture<Void> deleteTerminatedBid(Integer idBid) {
        //TODO
        return null;
    }

    public static CompletableFuture<LinkedList<Bid>> getMyBids() {
        //TODO
        return null;
    }
}
