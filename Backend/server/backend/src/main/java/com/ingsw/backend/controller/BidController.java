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

import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Auction;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.ReverseAuction;
import com.ingsw.backend.model.ReverseBid;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.SilentAuction;
import com.ingsw.backend.model.SilentBid;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.AuctionService;
import com.ingsw.backend.service.BidService;
import com.ingsw.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
	
	@Autowired
	@Qualifier("mainBidService")
	private BidService bidService;
	
	@Autowired
	@Qualifier("mainAuctionService")
	private AuctionService auctionService;
	
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;
	
	@GetMapping("/silent/buyer/{buyerEmail}")
    public ResponseEntity<List<SilentBid>> getAllSilentBidsByBuyer(@PathVariable String buyerEmail) {
        
		Optional<User> buyer = userService.getUser(buyerEmail); 
		
		if (buyer.isEmpty() || buyer.get().getRole() != Role.BUYER) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
		List<SilentBid> bids = bidService.getAllSilentBidsByBuyer((Buyer) buyer.get());
        
		return ResponseEntity.ok(bids);
    }


    @GetMapping("/reverse/seller/{sellerEmail}")
    public ResponseEntity<List<ReverseBid>> getAllReverseBidsBySeller(@PathVariable String sellerEmail) {
		
    	Optional<User> seller = userService.getUser(sellerEmail);
		
		if (seller.isEmpty() || seller.get().getRole() != Role.SELLER) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
		List<ReverseBid> bids = bidService.getAllReverseBidsBySeller((Seller) seller.get());
        
		return ResponseEntity.ok(bids);
    }
    
    @GetMapping("/silent/{auctionId}")
    public ResponseEntity<List<SilentBid>> getAllSilentBidsBySilentAuction(@PathVariable Integer auctionId) {

		Optional<Auction> auction = auctionService.findById(auctionId); 
		
		if (auction.isEmpty() || !(auction.get() instanceof SilentAuction)) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
		List<SilentBid> bids = bidService.getAllSilentBidsBySilentAuction((SilentAuction) auction.get());
        
		return ResponseEntity.ok(bids);
    }

   
    @GetMapping("/reverse/{auctionId}")
    public ResponseEntity<List<ReverseBid>> getAllReverseBidsByReverseAuction(@PathVariable Integer auctionId) {
		
    	Optional<Auction> auction = auctionService.findById(auctionId); 
		
		if (auction.isEmpty() || !(auction.get() instanceof ReverseAuction)) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
		List<ReverseBid> bids = bidService.getAllReverseBidsByReverseAuction((ReverseAuction) auction.get());
        
		return ResponseEntity.ok(bids);
    }


    @PostMapping("/silent")
    public ResponseEntity<SilentBid> addSilentBid(@Valid @RequestBody SilentBid silentBid) {
        
        Optional<User> owner = userService.getUser(silentBid.getBuyer().getEmail());
        
		if (owner.isEmpty() || owner.get().getRole() == Role.SELLER) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        silentBid.setBuyer((Buyer) owner.get());
        
        Optional<Auction> auction = auctionService.findById(silentBid.getSilentAuction().getIdAuction());
        
		if (auction.isEmpty() || !(auction.get() instanceof SilentAuction)) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        silentBid.setSilentAuction((SilentAuction) auction.get());

        SilentBid savedBid = bidService.addSilentBid(silentBid);

        return new ResponseEntity<>(savedBid, HttpStatus.CREATED);
    }
    
    @PostMapping("/reverse")
    public ResponseEntity<ReverseBid> addReverseBid(@Valid @RequestBody ReverseBid reverseBid) {
        
        Optional<User> owner = userService.getUser(reverseBid.getSeller().getEmail());
        
		if (owner.isEmpty() || owner.get().getRole() == Role.BUYER) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		reverseBid.setSeller((Seller) owner.get());
        
        Optional<Auction> auction = auctionService.findById(reverseBid.getReverseAuction().getIdAuction());
        
		if (auction.isEmpty() || !(auction.get() instanceof ReverseAuction)) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		reverseBid.setReverseAuction((ReverseAuction) auction.get());

        ReverseBid savedBid = bidService.addReverseBid(reverseBid);

        return new ResponseEntity<>(savedBid, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBid(@PathVariable Integer id) {
    	
    	Boolean isDeleted = bidService.delete(id);
        
    	if (!isDeleted) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/acceptBid/{id}")
    public ResponseEntity<Boolean> acceptBid(@PathVariable Integer id) {
    	
        Boolean result = bidService.acceptSilentBid(id);
        	
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/declineBid/{id}")
    public ResponseEntity<Boolean> declineBid(@PathVariable Integer id) {
    	
        Boolean result = bidService.declineSilentBid(id);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/silent/{id}/isWithdrawable")
    public ResponseEntity<Boolean> isSilentBidWithdrawable(@PathVariable Integer id) {
        
    	Boolean isWithdrawable = bidService.isSilentBidWithdrawable(id);
        
    	return new ResponseEntity<>(isWithdrawable, HttpStatus.OK);
    }
}

