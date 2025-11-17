package library.service;

import library.model.Notification;
import library.model.enums.NotificationType;
import library.service.notification.NotificationChannel;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class NotificationService {
    private final List<NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> channels) {
        this.channels = channels;
    }

    public void sendNotification(Notification notification) {
        // Set notification ID if not already set
        if (notification.getId() == null) {
            notification.setId(UUID.randomUUID().toString());
        }

        // Try each channel until one succeeds
        for (NotificationChannel channel : channels) {
            if (channel.supports(notification)) {
                try {
                    channel.send(notification);
                    notification.setSent(true);
                    break;
                } catch (Exception e) {
                    // Log error and continue trying other channels
                    System.err.println("Failed to send notification through channel: " + channel.getClass().getSimpleName());
                }
            }
        }
    }

    // Helper methods to create common notifications
    public Notification createBookAvailableNotification(String patronId, String bookTitle, String bookId) {
        Notification notification = new Notification();
        notification.setType(NotificationType.BOOK_AVAILABLE);
        notification.setRecipientId(patronId);
        notification.setSubject("Book Available: " + bookTitle);
        notification.setMessage("The book '" + bookTitle + "' is now available for pickup.");
        notification.addMetadata("bookId", bookId);
        return notification;
    }

    public Notification createLoanDueSoonNotification(String patronId, String bookTitle, String dueDate) {
        Notification notification = new Notification();
        notification.setType(NotificationType.LOAN_DUE_SOON);
        notification.setRecipientId(patronId);
        notification.setSubject("Book Due Soon: " + bookTitle);
        notification.setMessage("The book '" + bookTitle + "' is due on " + dueDate);
        return notification;
    }

    public Notification createOverdueNotification(String patronId, String bookTitle, double fine) {
        Notification notification = new Notification();
        notification.setType(NotificationType.LOAN_OVERDUE);
        notification.setRecipientId(patronId);
        notification.setSubject("Book Overdue: " + bookTitle);
        notification.setMessage("The book '" + bookTitle + "' is overdue. Current fine: $" + fine);
        return notification;
    }

    public Notification createReservationExpiryNotification(String patronId, String bookTitle) {
        Notification notification = new Notification();
        notification.setType(NotificationType.RESERVATION_EXPIRED);
        notification.setRecipientId(patronId);
        notification.setSubject("Reservation Expired: " + bookTitle);
        notification.setMessage("Your reservation for '" + bookTitle + "' has expired.");
        return notification;
    }
}