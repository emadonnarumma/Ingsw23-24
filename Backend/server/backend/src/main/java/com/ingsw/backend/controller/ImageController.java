package com.ingsw.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Image;
import com.ingsw.backend.service.AuctionService;
import com.ingsw.backend.service.ImageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
	
	@Autowired
	@Qualifier("mainAuctionService")
	private AuctionService auctionService;
	
	@Autowired
	@Qualifier("mainImageService")
	private ImageService imageService;

	@GetMapping("/auction/{auctionId}")
	public ResponseEntity<List<Image>> getAllImagesByAuction(@PathVariable Integer auctionId) {
	    
	    Optional<Auction> auction = auctionService.findById(auctionId);
	    
	    if (!auction.isPresent()) {
	        
	    	return ResponseEntity.notFound().build();
	    }
	    
	    
	    List<Image> images = imageService.getAllImagesByAuction(auction.get());
	    
	    return ResponseEntity.ok(images);
	}



    @PostMapping
    public ResponseEntity<List<Image>> addImages(@Valid @RequestBody List<Image> images) {
        
    	List<Image> savedImages = imageService.addImages(images);
        
    	return ResponseEntity.ok(savedImages);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable Integer id) {
        
    	if (imageService.delete(id)) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

}
