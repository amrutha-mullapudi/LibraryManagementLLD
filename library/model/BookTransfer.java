package library.model;

import java.time.LocalDateTime;

public class BookTransfer {
    private String transferId;
    private BookCopy bookCopy;
    private Branch sourceBranch;
    private Branch destinationBranch;
    private LocalDateTime transferDate;
    private TransferStatus status;

    public BookTransfer(String transferId, BookCopy bookCopy, Branch sourceBranch, 
                       Branch destinationBranch) {
        this.transferId = transferId;
        this.bookCopy = bookCopy;
        this.sourceBranch = sourceBranch;
        this.destinationBranch = destinationBranch;
        this.transferDate = LocalDateTime.now();
        this.status = TransferStatus.INITIATED;
    }

    // Getters
    public String getTransferId() {
        return transferId;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public Branch getSourceBranch() {
        return sourceBranch;
    }

    public Branch getDestinationBranch() {
        return destinationBranch;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public TransferStatus getStatus() {
        return status;
    }

    // Setters
    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    // Transfer status enum
    public enum TransferStatus {
        INITIATED,
        IN_TRANSIT,
        COMPLETED,
        CANCELLED,
        FAILED
    }
}