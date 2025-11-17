package library;

import library.model.*;
import library.model.enums.*;
import library.repository.*;
import library.service.*;
import library.service.notification.*;
import library.catalog.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories
        BookRepository bookRepo = new BookRepository();
        BookCopyRepository copyRepo = new BookCopyRepository();
        PatronRepository patronRepo = new PatronRepository();
        LoanRepository loanRepo = new LoanRepository();
        ReservationRepository reservationRepo = new ReservationRepository();
        FineRecordRepository fineRecordRepo = new FineRecordRepository();
        FineTransactionRepository fineTransactionRepo = new FineTransactionRepository();

        // Initialize notification channels
        List<NotificationChannel> notificationChannels = Arrays.asList(
            new EmailNotificationChannel(),
            new SMSNotificationChannel()
        );

        // Initialize services
        NotificationService notificationService = new NotificationService(notificationChannels);
        FineService fineService = new FineService(fineRecordRepo);
        FineTransactionService fineTransactionService = new FineTransactionService(fineTransactionRepo, fineService);
        LoanService loanService = new LoanService(loanRepo, copyRepo, patronRepo, fineService);
        ReservationService reservationService = new ReservationService(
            reservationRepo, copyRepo, patronRepo, notificationService);

        // Add sample books
        Book book1 = new Book();
        book1.setId("B1");
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert Martin");
        book1.setIsbn("123456789");
        book1.setPublicationYear(2008);
        book1.setGenre("Programming");

        Book book2 = new Book();
        book2.setId("B2");
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("987654321");
        book2.setPublicationYear(2018);
        book2.setGenre("Programming");

        bookRepo.save(book1);
        bookRepo.save(book2);

        // Add book copies
        BookCopy copy1 = new BookCopy();
        copy1.setId("C1");
        copy1.setBook(book1);
        copy1.setStatus(BookStatus.AVAILABLE);
        copy1.setLocation("Shelf A1");
        copy1.setCondition("Good");

        BookCopy copy2 = new BookCopy();
        copy2.setId("C2");
        copy2.setBook(book1);
        copy2.setStatus(BookStatus.AVAILABLE);
        copy2.setLocation("Shelf A1");
        copy2.setCondition("Good");

        copyRepo.save(copy1);
        copyRepo.save(copy2);

        // Add sample patrons with complete profile
        PatronService patronService = new PatronService(
            patronRepo, 
            new PatronAccountRepository(), 
            new BorrowingProfileRepository(),
            fineRecordRepo
        );

        Patron patron1 = patronService.createPatron("John Doe", "john@example.com", "1234567890");
        Patron patron2 = patronService.createPatron("Jane Smith", "jane@example.com", "0987654321");

        // Create catalog for searching
        Catalog catalog = new Catalog();
        
        // Demonstrate library operations
        try {
            // 1. Search for books
            System.out.println("\n=== Searching for Programming Books ===");
            List<Book> programmingBooks = catalog.searchItems("Programming", new SearchByGenre());
            programmingBooks.forEach(book -> System.out.println(book.getTitle()));

            // 2. Loan a book
            System.out.println("\n=== Loaning a Book ===");
            Loan loan1 = loanService.loanBook(patron1.getId(), copy1.getId());
            System.out.println("Book loaned to: " + loan1.getPatron().getName());

            // 3. Try to reserve an available book (should fail)
            System.out.println("\n=== Attempting to Reserve Available Book ===");
            try {
                reservationService.createReservation(patron2.getId(), copy2.getId());
            } catch (IllegalStateException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            // 4. Return an overdue book and handle fine
            System.out.println("\n=== Returning Overdue Book and Processing Fine ===");
            loan1.setDueDate(LocalDate.now().minusDays(5)); // Simulate overdue book
            Loan returnedLoan = loanService.returnBook(loan1.getId());
            
            // Calculate and create fine
            FineRecord fine = fineService.calculateAndCreateFine(returnedLoan);
            System.out.println("Fine charged: $" + fine.getAmount());

            // Process fine payment
            System.out.println("\n=== Processing Fine Payment ===");
            FineTransaction transaction = fineTransactionService.createTransaction(
                patron1.getId(),
                fine.getId(),
                fine.getAmount(),
                PaymentType.CREDIT_CARD,
                "CARD-123456"
            );
            
            try {
                transaction = fineTransactionService.processPayment(transaction.getTransactionId());
                System.out.println("Payment status: " + transaction.getStatus());
            } catch (RuntimeException e) {
                System.out.println("Payment failed: " + e.getMessage());
            }

            // 5. Reserve a book
            System.out.println("\n=== Reserving a Book ===");
            copy1.setStatus(BookStatus.LOANED);
            copyRepo.update(copy1);
            Reservation reservation = reservationService.createReservation(patron2.getId(), copy1.getId());
            System.out.println("Book reserved for: " + reservation.getPatron().getName());

            // 6. Process book return and notify next patron
            System.out.println("\n=== Processing Return and Notification ===");
            reservationService.processBookReturn(copy1);

            // 7. Demonstrate fine refund
            System.out.println("\n=== Processing Fine Refund ===");
            try {
                FineTransaction refundTransaction = fineTransactionService.refundTransaction(
                    transaction.getTransactionId(),
                    "Customer service gesture"
                );
                System.out.println("Refund status: " + refundTransaction.getStatus());
            } catch (RuntimeException e) {
                System.out.println("Refund failed: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error during demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
}
}
