package library.service;

import library.model.BookCopy;
import library.model.Loan;
import library.model.Patron;
import library.model.enums.BookStatus;
import library.repository.BookCopyRepository;
import library.repository.LoanRepository;
import library.repository.PatronRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanService {
    private final LoanRepository loanRepository;
    private final BookCopyRepository bookCopyRepository;
    private final PatronRepository patronRepository;
    private final FineService fineService;
    private static final int DEFAULT_LOAN_DAYS = 14;
    private static final int MAX_EXTENSION_DAYS = 14;

    public LoanService(LoanRepository loanRepository, 
                      BookCopyRepository bookCopyRepository,
                      PatronRepository patronRepository,
                      FineService fineService) {
        this.loanRepository = loanRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.patronRepository = patronRepository;
        this.fineService = fineService;
    }

    public Loan loanBook(String patronId, String copyId) {
        Patron patron = patronRepository.findById(patronId);
        BookCopy copy = bookCopyRepository.findById(copyId);

        if (copy.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book copy is not available for loan");
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(DEFAULT_LOAN_DAYS);

        Loan loan = new Loan();
        loan.setPatron(patron);
        loan.setBookCopy(copy);
        loan.setIssueDate(issueDate);
        loan.setDueDate(dueDate);

        copy.setStatus(BookStatus.LOANED);
        bookCopyRepository.update(copy);

        return loanRepository.save(loan);
    }

    public Loan reissueLoan(String loanId, int extraDays) {
        if (extraDays > MAX_EXTENSION_DAYS) {
            throw new IllegalArgumentException("Cannot extend loan beyond maximum limit");
        }

        Loan loan = loanRepository.findById(loanId);
        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Cannot reissue a returned loan");
        }

        loan.setDueDate(loan.getDueDate().plusDays(extraDays));
        return loanRepository.update(loan);
    }

    public Loan returnBook(String loanId) {
        Loan loan = loanRepository.findById(loanId);
        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned");
        }

        loan.setReturnDate(LocalDate.now());
        loan.getBookCopy().setStatus(BookStatus.AVAILABLE);
        
        // Calculate fine if book is overdue
        double fine = fineService.calculateFine(loan);
        loan.setFine(fine);

        bookCopyRepository.update(loan.getBookCopy());
        return loanRepository.update(loan);
    }

    // List all loans of a patron
    public List<Loan> getLoansByPatron(String patronId) {
        return loanRepository.findByPatronId(patronId);
    }

    // List all loans of an item
    public List<Loan> getLoansByItem(String itemId) {
        return loanRepository.findByItemId(itemId);
    }
}
