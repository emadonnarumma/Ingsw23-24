package com.ingsw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ingsw.backend.service.AuctionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.enumeration.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
	
	@Autowired
	@Qualifier("mainAuctionService")
	private AuctionService auctionService;
	
	
	@GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Auction>> getAuctionsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getAuctionsByCategory(category));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Auction>> getAuctionsByTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(auctionService.getAuctionsByTitleContaining(keyword));
    }

    @GetMapping("/search/{keyword}/category/{category}")
    public ResponseEntity<List<Auction>> getAuctionsByTitleContainingAndCategory(@PathVariable String keyword, @PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getAuctionsByTitleContainingAndCategory(keyword, category));
    }

    @PostMapping
    public ResponseEntity<Auction> addAuction(@Valid @RequestBody Auction auction) {
        
    	Auction savedAuction = auctionService.addAuction(auction);
        
        return new ResponseEntity<>(savedAuction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        
    	Boolean isDeleted = auctionService.delete(id);
        
    	if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found");
        }
        
        return ResponseEntity.noContent().build();
    }

}


