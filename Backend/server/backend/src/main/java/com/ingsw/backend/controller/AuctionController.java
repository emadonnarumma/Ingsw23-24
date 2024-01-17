package com.ingsw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.backend.service.AuctionService;
import com.ingsw.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.User;
import com.ingsw.backend.enumeration.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
	
	@Autowired
	@Qualifier("mainAuctionService")
	private AuctionService auctionService;
	
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;
	
	
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

        Optional<User> owner = userService.getUser(auction.getOwner().getEmail());
        
		if (owner.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        auction.setOwner(owner.get());

        Auction savedAuction = auctionService.addAuction(auction);

        return new ResponseEntity<>(savedAuction, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
    	
    	Boolean isDeleted = auctionService.delete(id);
        
    	if (!isDeleted) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/buyNow/{auctionId}")
    public ResponseEntity<Boolean> buyNow(@PathVariable Integer auctionId) {
        
    	Boolean result = auctionService.buyDownwardAuctionNow(auctionId);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}


