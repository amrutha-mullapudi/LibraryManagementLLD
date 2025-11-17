package model;

import model.enums.ReservationStatus;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private final String reservationId;
    private final String itemId;      // LibraryItem ID
    private final String patronId;    // Patron ID
    private ReservationStatus status;
    private final LocalDate reservationDate;

    public Reservation(String reservationId, String itemId, String patronId, ReservationStatus status, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.itemId = itemId;
        this.patronId = patronId;
        this.status = status;
        this.reservationDate = reservationDate;
    }

    public String getReservationId() { return reservationId; }
    public String getItemId() { return itemId; }
    public String getPatronId() { return patronId; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public LocalDate getReservationDate() { return reservationDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return reservationId.equals(that.reservationId);
    }

    @Override
    public int hashCode() { return Objects.hash(reservationId); }
}
