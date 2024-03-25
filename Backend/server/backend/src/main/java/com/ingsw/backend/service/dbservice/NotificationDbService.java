package com.ingsw.backend.service.dbservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.Notification;
import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.NotificationRepository;
import com.ingsw.backend.service.NotificationService;

@Service("mainNotificationService")
public class NotificationDbService implements NotificationService {
	
	private final NotificationRepository notificationRepository;
	
	public NotificationDbService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<Notification> getAllNotificationsByUser(User user) {
		
		return notificationRepository.findAllByUser(user);
	}

}
