package com.ingsw.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.Notification;
import com.ingsw.backend.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	List<Notification> findAllByUser(User user);
}
