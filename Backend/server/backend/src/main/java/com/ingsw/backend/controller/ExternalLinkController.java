package com.ingsw.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.backend.model.ExternalLink;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.ExternalLinkService;
import com.ingsw.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externallink")
public class ExternalLinkController {
	
    @Autowired
    @Qualifier("mainExternalLinkService")
    private ExternalLinkService externalLinkService;
    
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;

    @PostMapping
    public ResponseEntity<ExternalLink> addExternalLink(@Valid @RequestBody ExternalLink externalLink) {
        
    	Optional<User> user = userService.getUser(externalLink.getUser().getEmail());
        
		if (user.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        externalLink.setUser(user.get());

        ExternalLink savedExternalLink= externalLinkService.addExternalLink(externalLink);

        return new ResponseEntity<>(savedExternalLink, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExternalLink> updateExternalLink(@PathVariable Integer id, @Valid @RequestBody ExternalLink externalLink) {
       
    	Optional<ExternalLink> updatedExternalLink = externalLinkService.update(id, externalLink);
        
    	if (updatedExternalLink.isPresent()) {
        	
            return ResponseEntity.ok(updatedExternalLink.get());
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExternalLink(@PathVariable Integer id) {
        
    	if (externalLinkService.delete(id)) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

}