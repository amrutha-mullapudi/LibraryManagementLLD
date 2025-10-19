package library.service.notification;

import library.model.Notification;

public class EmailNotificationChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) {
        // Implement email sending logic
        System.out.println("Sending email: " + notification.getSubject());
    }

    @Override
    public boolean supports(Notification notification) {
        // Check if we have email contact info in metadata
        return notification.getMetadata().containsKey("email");
    }
}