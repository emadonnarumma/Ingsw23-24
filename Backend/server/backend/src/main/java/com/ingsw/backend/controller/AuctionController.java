package com.ingsw.backend.controller;

import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.*;
import com.ingsw.backend.service.ImageService;
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

    @Autowired
    @Qualifier("mainImageService")
    private ImageService imageService;
	
	@GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }
	
	@GetMapping("/silent")
    public ResponseEntity<List<SilentAuction>> getAllSilentAuctions() {
        return ResponseEntity.ok(auctionService.getAllSilentAuctions());
    }

	@GetMapping("/reverse")
    public ResponseEntity<List<ReverseAuction>> getAllReverseAuctions() {
        return ResponseEntity.ok(auctionService.getAllReverseAuctions());
    }
	
	@GetMapping("/downward")
    public ResponseEntity<List<DownwardAuction>> getAllDownwardAuctions() {
        return ResponseEntity.ok(auctionService.getAllDownwardAuctions());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Auction>> getAuctionsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getAuctionsByCategory(category));
    }
    
    @GetMapping("/silent/category/{category}")
    public ResponseEntity<List<SilentAuction>> getSilentAuctionsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getSilentAuctionsByCategory(category));
    }
    
    @GetMapping("/reverse/category/{category}")
    public ResponseEntity<List<ReverseAuction>> getReverseAuctionsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getReverseAuctionsByCategory(category));
    }
    
    @GetMapping("/downward/category/{category}")
    public ResponseEntity<List<DownwardAuction>> getDownwardAuctionsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getDownwardAuctionsByCategory(category));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Auction>> getAuctionsByTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(auctionService.getAuctionsByTitleContaining(keyword));
    }
    
    @GetMapping("/silent/search/{keyword}")
    public ResponseEntity<List<SilentAuction>> getSilentAuctionsByTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(auctionService.getSilentAuctionsByTitleContaining(keyword));
    }
    
    @GetMapping("/reverse/search/{keyword}")
    public ResponseEntity<List<ReverseAuction>> getReverseAuctionsByTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(auctionService.getReverseAuctionsByTitleContaining(keyword));
    }
    
    @GetMapping("/downward/search/{keyword}")
    public ResponseEntity<List<DownwardAuction>> getDownwardAuctionsByTitleContaining(@PathVariable String keyword) {
        return ResponseEntity.ok(auctionService.getDownwardAuctionsByTitleContaining(keyword));
    }

    @GetMapping("/search/{keyword}/category/{category}")
    public ResponseEntity<List<Auction>> getAuctionsByTitleContainingAndCategory(@PathVariable String keyword, @PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getAuctionsByTitleContainingAndCategory(keyword, category));
    }
    
    @GetMapping("/silent/search/{keyword}/category/{category}")
    public ResponseEntity<List<SilentAuction>> getSilentAuctionsByTitleContainingAndCategory(@PathVariable String keyword, @PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getSilentAuctionsByTitleContainingAndCategory(keyword, category));
    }
    
    @GetMapping("/reverse/search/{keyword}/category/{category}")
    public ResponseEntity<List<ReverseAuction>> getReverseAuctionsByTitleContainingAndCategory(@PathVariable String keyword, @PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getReverseAuctionsByTitleContainingAndCategory(keyword, category));
    }
    
    @GetMapping("/downward/search/{keyword}/category/{category}")
    public ResponseEntity<List<DownwardAuction>> getDownwardAuctionsByTitleContainingAndCategory(@PathVariable String keyword, @PathVariable Category category) {
        return ResponseEntity.ok(auctionService.getDownwardAuctionsByTitleContainingAndCategory(keyword, category));
    }
    
    @PostMapping
    public ResponseEntity<Auction> addAuction(@Valid @RequestBody Auction auction) {
        if(auction.getOwner() instanceof Buyer)
            auction.getOwner().setRole(Role.BUYER);
        else
            auction.getOwner().setRole(Role.SELLER);

        Optional<User> owner = userService.getUser(auction.getOwner().getEmail(), auction.getOwner().getRole());
        
		if (owner.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        auction.setOwner(owner.get());
        Auction savedAuction = auctionService.addAuction(auction);

        for(Image image : auction.getImages()) {
            image.setAuction(auction);
            imageService.addImage(image);
        }

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

    @PostMapping("/relaunch")
    public ResponseEntity<Auction> relaunchAuction(@Valid @RequestBody Auction auction) {
        Boolean isDeleted = auctionService.delete(auction.getIdAuction());
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return addAuction(auction);
        }
    }
    
    @PostMapping("/buyNow/{auctionId}")
    public ResponseEntity<Boolean> buyNow(@PathVariable Integer auctionId) {
    	Boolean result = auctionService.buyDownwardAuctionNow(auctionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{auctionId}/remainingSeconds")
    public ResponseEntity<Long> getRemainingSeconds(@PathVariable Integer auctionId) {
    	Long remainingSeconds = auctionService.getRemainingSecondsForAuction(auctionId);
        return new ResponseEntity<>(remainingSeconds, HttpStatus.OK);
    }

    @GetMapping("/{email}/getSilentAuctions")
    public ResponseEntity<List<SilentAuction>> getSilentAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getSilentAuctionsByOwnerEmail(email));
    }

    @GetMapping("/{email}/getDownwardAuctions")
    public ResponseEntity<List<DownwardAuction>> getDownwardAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getDownwardAuctionsByOwnerEmail(email));
    }

    @GetMapping("/{email}/getReverseAuctions")
    public ResponseEntity<List<ReverseAuction>> getReverseAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getReverseAuctionsByOwnerEmail(email));
    }

    @GetMapping("/{email}/getInProgressSilentAuctions")
    public ResponseEntity<List<SilentAuction>> getInProgressAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getInProgressSilentAuctionsByOwnerEmail(email));
    }

    @GetMapping("/{email}/getInProgressDownwardAuctions")
    public ResponseEntity<List<DownwardAuction>> getInProgressDownwardAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getInProgressDownwardAuctionsByOwnerEmail(email));
    }

    @GetMapping("/{email}/getInProgressReverseAuctions")
    public ResponseEntity<List<ReverseAuction>> getInProgressReverseAuctionsByOwnerEmail(@PathVariable String email) {
        return ResponseEntity.ok(auctionService.getInProgressReverseAuctionsByOwnerEmail(email));
    }
}


