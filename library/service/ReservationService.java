package library.service;

import library.model.BookCopy;
import library.model.Patron;
import library.model.Reservation;
import library.model.enums.BookStatus;
import library.model.enums.ReservationStatus;
import library.repository.BookCopyRepository;
import library.repository.ReservationRepository;
import library.repository.PatronRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookCopyRepository bookCopyRepository;
    private final PatronRepository patronRepository;
    private static final int RESERVATION_EXPIRY_DAYS = 2;

    public ReservationService(ReservationRepository reservationRepository,
                            BookCopyRepository bookCopyRepository,
                            PatronRepository patronRepository) {
        this.reservationRepository = reservationRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.patronRepository = patronRepository;
    }

    public Reservation createReservation(String patronId, String copyId) {
        Patron patron = patronRepository.findById(patronId);
        BookCopy copy = bookCopyRepository.findById(copyId);

        if (copy.getStatus() == BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is available, no need for reservation");
        }

        Reservation reservation = new Reservation();
        reservation.setPatron(patron);
        reservation.setBookCopy(copy);
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus(ReservationStatus.WAITING);

        return reservationRepository.save(reservation);
    }

    public void processBookReturn(BookCopy copy) {
        List<Reservation> waitingReservations = reservationRepository.findByBookCopyAndStatus(
            copy.getId(), ReservationStatus.WAITING);

        if (!waitingReservations.isEmpty()) {
            Reservation nextReservation = waitingReservations.get(0);
            nextReservation.setStatus(ReservationStatus.READY);
            nextReservation.setNotificationDate(LocalDate.now());
            reservationRepository.update(nextReservation);

            // Send notification using NotificationService
            Notification notification = notificationService.createBookAvailableNotification(
                nextReservation.getPatron().getId(),
                nextReservation.getBookCopy().getBook().getTitle(),
                nextReservation.getBookCopy().getId()
            );
            notificationService.sendNotification(notification);
        }
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.update(reservation);
        
        // Send cancellation notification
        Notification notification = notificationService.createBookAvailableNotification(
            reservation.getPatron().getId(),
            reservation.getBookCopy().getBook().getTitle(),
            reservation.getBookCopy().getId()
        );
        notificationService.sendNotification(notification);
    }

    public void expireReservations() {
        LocalDate expiryDate = LocalDate.now().minusDays(RESERVATION_EXPIRY_DAYS);
        List<Reservation> readyReservations = reservationRepository.findByStatusAndNotificationDateBefore(
            ReservationStatus.READY, expiryDate);

        for (Reservation reservation : readyReservations) {
            reservation.setStatus(ReservationStatus.EXPIRED);
            reservationRepository.update(reservation);
            
            // Send expiry notification
            Notification notification = notificationService.createReservationExpiryNotification(
                reservation.getPatron().getId(),
                reservation.getBookCopy().getBook().getTitle()
            );
            notificationService.sendNotification(notification);
        }
    }

    // List reservations by patron
    public List<Reservation> getReservationsByPatron(String patronId) {
        return reservationRepository.findByPatronId(patronId);
    }

    // List reservations by item
    public List<Reservation> getReservationsByItem(String itemId) {
        return reservationRepository.findByItemId(itemId);
    }
}
