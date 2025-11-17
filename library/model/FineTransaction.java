package library.model;

import library.model.enums.PaymentStatus;
import library.model.enums.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class FineTransaction {
    private final String transactionId;
    private final String patronId;
    private final String fineId;
    private final BigDecimal amount;
    private final PaymentType paymentType;
    private PaymentStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentReference;
    private String notes;

    private FineTransaction(Builder builder) {
        this.transactionId = UUID.randomUUID().toString();
        this.patronId = builder.patronId;
        this.fineId = builder.fineId;
        this.amount = builder.amount;
        this.paymentType = builder.paymentType;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.paymentReference = builder.paymentReference;
        this.notes = builder.notes;
    }

    // Builder pattern for flexible transaction creation
    public static class Builder {
        private final String patronId;
        private final String fineId;
        private final BigDecimal amount;
        private final PaymentType paymentType;
        private String paymentReference;
        private String notes;

        public Builder(String patronId, String fineId, BigDecimal amount, PaymentType paymentType) {
            this.patronId = patronId;
            this.fineId = fineId;
            this.amount = amount;
            this.paymentType = paymentType;
        }

        public Builder paymentReference(String paymentReference) {
            this.paymentReference = paymentReference;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public FineTransaction build() {
            return new FineTransaction(this);
        }
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getPatronId() { return patronId; }
    public String getFineId() { return fineId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentType getPaymentType() { return paymentType; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getPaymentReference() { return paymentReference; }
    public String getNotes() { return notes; }

    // Status management methods
    public void updateStatus(PaymentStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePaymentReference(String reference) {
        this.paymentReference = reference;
        this.updatedAt = LocalDateTime.now();
    }

    public void addNotes(String additionalNotes) {
        this.notes = this.notes == null ? additionalNotes : 
                    this.notes + " | " + additionalNotes;
        this.updatedAt = LocalDateTime.now();
    }
}