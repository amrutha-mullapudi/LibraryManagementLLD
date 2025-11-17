package library.repository;

import library.model.Loan;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LoanRepository {
    private final Map<String, Loan> loanStorage = new HashMap<>();

    public Loan save(Loan loan) {
        loanStorage.put(loan.getId(), loan);
        return loan;
    }

    public void delete(String loanId) {
        loanStorage.remove(loanId);
    }

    public Loan update(Loan loan) {
        loanStorage.put(loan.getId(), loan);
        return loan;
    }

    public Loan findById(String loanId) {
        return loanStorage.get(loanId);
    }

    public List<Loan> findAll() {
        return new ArrayList<>(loanStorage.values());
    }

    public List<Loan> findByPatronId(String patronId) {
        return loanStorage.values().stream()
            .filter(loan -> loan.getPatron().getId().equals(patronId))
            .collect(Collectors.toList());
    }

    public List<Loan> findActiveLoans() {
        return loanStorage.values().stream()
            .filter(loan -> loan.getReturnDate() == null)
            .collect(Collectors.toList());
    }

    public List<Loan> findOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loanStorage.values().stream()
            .filter(loan -> loan.getReturnDate() == null && loan.getDueDate().isBefore(today))
            .collect(Collectors.toList());
    }

    public List<Loan> findByItemId(String itemId) {
        return loanStorage.values().stream()
            .filter(loan -> loan.getBookCopy().getId().equals(itemId))
            .collect(Collectors.toList());
    }
}
}
