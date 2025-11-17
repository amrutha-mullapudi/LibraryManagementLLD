package library.service.notification;

import library.model.Notification;

public interface NotificationChannel {
    void send(Notification notification);
    boolean supports(Notification notification);
}