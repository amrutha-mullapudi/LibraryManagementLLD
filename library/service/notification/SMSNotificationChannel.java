package library.service.notification;

import library.model.Notification;

public class SMSNotificationChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) {
        // Implement SMS sending logic
        System.out.println("Sending SMS: " + notification.getMessage());
    }

    @Override
    public boolean supports(Notification notification) {
        // Check if we have phone number in metadata
        return notification.getMetadata().containsKey("phone");
    }
}