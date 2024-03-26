package com.ingsw.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

	@GetMapping("/{auctionId}/getAllAuctionImages")
	public ResponseEntity<List<Image>> getAllImagesByAuction(@PathVariable Integer auctionId) {
	    
	    Optional<Auction> auction = auctionService.findById(auctionId);
	    
	    if (!auction.isPresent()) {
	        
	    	return ResponseEntity.notFound().build();
	    }
	    
	    
	    List<Image> images = imageService.getAllImagesByAuction(auction.get());
	    
	    return ResponseEntity.ok(images);
	}

    @PostMapping("/{auctionId}")
    public ResponseEntity<Image> addImage(@Valid @RequestBody Image image, @PathVariable Integer auctionId) {

		Optional<Auction> auction = auctionService.findById(auctionId);

		if (auction.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		image.setAuction(auction.get());

    	Image savedImage = imageService.addImage(image);
        
    	return ResponseEntity.ok(savedImage);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable Integer id) {
        
    	if (Boolean.TRUE.equals(imageService.delete(id))) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

}
