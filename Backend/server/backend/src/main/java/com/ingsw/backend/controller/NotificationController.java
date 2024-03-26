package com.ingsw.backend.controller;

import java.util.List;
import java.util.Optional;

import com.ingsw.backend.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.backend.model.Notification;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.NotificationService;
import com.ingsw.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	
	
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;
	
	@Autowired
	@Qualifier("mainNotificationService")
	private NotificationService notificationService;
	
	@GetMapping("/{userEmail}/{userRole}/getAllNotifications")
	public ResponseEntity<List<Notification>> getAllNotificationsByUser(@PathVariable String userEmail, @PathVariable Role userRole) {
	    
	    Optional<User> user = userService.getUser(userEmail, userRole);
	    
	    if (!user.isPresent()) {
	        
	    	return ResponseEntity.notFound().build();
	    }
	    
	    
	    List<Notification> notifications = notificationService.getAllNotificationsByUser(user.get());
	    
	    return ResponseEntity.ok(notifications);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNotification(@PathVariable Integer id) {
        
    	if (Boolean.TRUE.equals(notificationService.delete(id))) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }
}
