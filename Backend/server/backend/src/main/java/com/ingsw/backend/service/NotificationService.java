package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.Notification;
import com.ingsw.backend.model.User;

public interface NotificationService {

	public List<Notification> getAllNotificationsByUser(User user);
	
	public Boolean delete(Integer id);
}
