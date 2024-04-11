package com.ingsw.backend.controller;

import com.ingsw.backend.enumeration.*;
import com.ingsw.backend.model.SilentAuction;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuctionControllerTest {
    List<SilentAuction> testAuctions = new ArrayList<>();

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuctionController auctionController;

    @BeforeEach
    @Transactional
    void setUpAuctions() {
        for (int i = 0; i < 10; i++) {
            SilentAuction testAuction = new SilentAuction();
            testAuction.setCategory(Category.SERVICES);
            testAuction.setTitle("Test_Auction_" + i);
            testAuction.setDescription("Test_Description_" + i);
            testAuction.setWear(Wear.NOT_SPECIFIED);
            testAuction.setStatus(AuctionStatus.IN_PROGRESS);
            testAuction.setWithdrawalTime(100000L + i);
            testAuction.setExpirationDate(Timestamp.valueOf("3000-01-01 12:00:00"));
            testAuctions.add(testAuction);
            entityManager.persist(testAuction);
            entityManager.flush();
        }
    }

    @AfterEach
    @Transactional
    void tearDownAuctions() {
        for (SilentAuction testAuction : testAuctions) {
            entityManager.remove(testAuction);
            entityManager.flush();
        }
    }

    /**
     * These test methods use various methods from the `AuctionController` class to test the functionality of the auction system.
     * <p>
     * The methods identify the following equivalence classes for the input parameters:
     * <p>
     * 1. Valid keyword and category that correspond to existing auctions in the system (valid equivalence class).
     * <br>
     * 2. Valid keyword but invalid or non-existent category (invalid equivalence class).
     * <br>
     * 3. Invalid or non-existent keyword but valid category (invalid equivalence class).
     * <br>
     * 4. Invalid or non-existent keyword and category (invalid equivalence class).
     */

    @Test
    @Transactional
    void testGetSilentAuctionsByTitleContainingAndCategory_NotEmptyResult() {
        List<SilentAuction> result = auctionController.getSilentAuctionsByTitleContainingAndCategory("Test", Category.SERVICES).getBody();
        assertEquals(result, testAuctions);
    }

    @Test
    @Transactional
    void testGetSilentAuctionsByTitleContainingAndCategory_EmptyResult() {
        List<SilentAuction> result = auctionController.getSilentAuctionsByTitleContainingAndCategory("Nothing", Category.ART).getBody();
        assertEquals(result, new ArrayList<>());
    }

    @Test
    @Transactional
    void testGetSilentAuctionsByTitleContainingAndCategory_NullCategory() {
        List<SilentAuction> result = auctionController.getSilentAuctionsByTitleContainingAndCategory("Test", null).getBody();
        assertEquals(result, new ArrayList<>());
    }

    @Test
    @Transactional
    void testGetSilentAuctionsByTitleContainingAndCategory_NullKeyword() {
        List<SilentAuction> result = auctionController.getSilentAuctionsByTitleContainingAndCategory(null, Category.SERVICES).getBody();
        assertEquals(result, new ArrayList<>());
    }

    @Test
    @Transactional
    void testGetSilentAuctionsByTitleContainingAndCategory_EmptyKeyword() {
        List<SilentAuction> result = auctionController.getSilentAuctionsByTitleContainingAndCategory("", Category.SERVICES).getBody();
        assertEquals(result, testAuctions);
    }
}
