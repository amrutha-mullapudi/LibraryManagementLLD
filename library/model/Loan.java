package model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private final String loanId;
    private final String itemId;      // LibraryItem ID
    private final String patronId;    // Patron ID
    private final LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(String loanId, String itemId, String patronId, LocalDate issueDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.itemId = itemId;
        this.patronId = patronId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getLoanId() { return loanId; }
    public String getItemId() { return itemId; }
    public String getPatronId() { return patronId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return loanId.equals(loan.loanId);
    }

    @Override
    public int hashCode() { return Objects.hash(loanId); }
}
