package library.model;

import java.util.Objects;

public class Patron {
    private final String patronId;
    private String name;
    private String email;
    private String phoneNumber;
    private PatronAccount account;
    private BorrowingProfile borrowingProfile;

    public Patron(String patronId, String name, String email, String phoneNumber) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Basic info getters and setters
    public String getPatronId() { return patronId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // Account and profile getters and setters
    public PatronAccount getAccount() { return account; }
    public void setAccount(PatronAccount account) { this.account = account; }

    public BorrowingProfile getBorrowingProfile() { return borrowingProfile; }
    public void setBorrowingProfile(BorrowingProfile borrowingProfile) { 
        this.borrowingProfile = borrowingProfile; 
    }

    // Helper methods
    public boolean canBorrow() {
        return account != null && 
               account.isActive() && 
               borrowingProfile != null && 
               !borrowingProfile.hasOverdueBooks() &&
               borrowingProfile.getCurrentBorrowedCount() < account.getMembershipType().getMaxBooks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron)) return false;
        Patron patron = (Patron) o;
        return patronId.equals(patron.patronId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patronId='" + patronId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountStatus='" + (account != null ? account.getStatus() : "NO_ACCOUNT") + '\'' +
                '}';
    }
}
