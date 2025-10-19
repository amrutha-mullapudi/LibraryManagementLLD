package library.model;

import library.model.enums.FineStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FineRecord {
    private String id;
    private String patronId;
    private String loanId;
    private BigDecimal amount;
    private LocalDate issuedDate;
    private LocalDate paidDate;
    private FineStatus status;
    private String reason;

    public FineRecord() {
        this.issuedDate = LocalDate.now();
        this.status = FineStatus.PENDING;
        this.amount = BigDecimal.ZERO;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatronId() { return patronId; }
    public void setPatronId(String patronId) { this.patronId = patronId; }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public FineStatus getStatus() { return status; }
    public void setStatus(FineStatus status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public void markAsPaid() {
        this.status = FineStatus.PAID;
        this.paidDate = LocalDate.now();
    }

    public void waive() {
        this.status = FineStatus.WAIVED;
        this.paidDate = LocalDate.now();
    }
}