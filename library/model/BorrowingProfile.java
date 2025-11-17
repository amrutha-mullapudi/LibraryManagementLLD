package library.model;

import java.math.BigDecimal;

public class BorrowingProfile {
    private String id;
    private String patronId;
    private int currentBorrowedCount;
    private boolean hasOverdueBooks;
    private BigDecimal outstandingFines;

    public BorrowingProfile() {
        this.currentBorrowedCount = 0;
        this.hasOverdueBooks = false;
        this.outstandingFines = BigDecimal.ZERO;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatronId() { return patronId; }
    public void setPatronId(String patronId) { this.patronId = patronId; }

    public int getCurrentBorrowedCount() { return currentBorrowedCount; }
    public void setCurrentBorrowedCount(int count) { this.currentBorrowedCount = count; }

    public boolean hasOverdueBooks() { return hasOverdueBooks; }
    public void setHasOverdueBooks(boolean hasOverdueBooks) { this.hasOverdueBooks = hasOverdueBooks; }

    public BigDecimal getOutstandingFines() { return outstandingFines; }
    public void setOutstandingFines(BigDecimal fines) { this.outstandingFines = fines; }

    public void incrementBorrowedCount() { this.currentBorrowedCount++; }
    public void decrementBorrowedCount() { 
        if (this.currentBorrowedCount > 0) {
            this.currentBorrowedCount--;
        }
    }
}