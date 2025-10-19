package library.service;

import library.model.*;
import java.time.LocalDateTime;

// Enum to represent different types of library events that need notifications
public enum NotificationType {
    BOOK_AVAILABLE,
    RESERVATION_EXPIRED,
    RESERVATION_CANCELLED,
    LOAN_DUE_SOON,
    LOAN_OVERDUE,
    FINE_INCURRED,
    MEMBERSHIP_EXPIRING,
    BOOK_ADDED_TO_WISHLIST,
    BOOK_BACK_IN_STOCK
}