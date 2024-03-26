package com.ingsw.backend.service;

import java.util.List;

import com.ingsw.backend.model.Notification;
import com.ingsw.backend.model.User;

public interface NotificationService {
    List<Notification> getAllNotificationsByUser(User user);

    Boolean delete(Integer id);
}
