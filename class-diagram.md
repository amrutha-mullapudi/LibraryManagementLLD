classDiagram
    LibraryItem <|-- Book
    Book "1" -- "*" BookCopy
    BookCopy "*" -- "1" Branch
    Branch "1" -- "*" BookTransfer
    BookTransfer "*" -- "1" BookCopy
    Patron "1" -- "*" Loan
    Patron "1" -- "1" PatronAccount
    Patron "1" -- "*" Reservation
    Patron "1" -- "*" FineRecord
    FineRecord "1" -- "*" FineTransaction
    BookCopy "1" -- "*" Loan
    Book "1" -- "*" Reservation
    Branch "1" -- "*" Reservation
    BookRepository -- Book
    BookCopyRepository -- BookCopy
    BranchRepository -- Branch
    BookTransferRepository -- BookTransfer
    PatronRepository -- Patron
    LoanService -- LoanRepository
    ReservationService -- ReservationRepository
    TransferService -- BookTransferRepository
    NotificationService -- NotificationChannel

    class LibraryItem{
        -String id
        -String title
        -String description
    }

    class Book{
        -String isbn
        -String title
        -String author
        -String publisher
        -String genre
        +getISBN()
        +getTitle()
        +getAuthor()
    }

    class BookCopy {
        -String copyId
        -Book book
        -Branch branch
        -BookStatus status
        +getCopyId()
        +getBook()
        +getStatus()
        +setStatus()
    }

    class Branch {
        -String branchId
        -String name
        -String location
        -BranchStatus status
        -List~BookCopy~ inventory
        +getBranchId()
        +getName()
        +getStatus()
        +addBookCopy()
        +removeBookCopy()
        +hasBookCopy()
    }

    class BookTransfer {
        -String transferId
        -BookCopy bookCopy
        -Branch sourceBranch
        -Branch destinationBranch
        -LocalDateTime transferDate
        -TransferStatus status
        +getTransferId()
        +getStatus()
        +setStatus()
    }

    class Patron {
        -String patronId
        -String name
        -String email
        -String phone
        -MembershipType membershipType
        +getPatronId()
        +getName()
        +getMembershipType()
    }

    class PatronAccount {
        -String accountId
        -Patron patron
        -AccountStatus status
        -double fineAmount
        +getAccountId()
        +getStatus()
        +getFineAmount()
    }

    class Loan {
        -String loanId
        -BookCopy bookCopy
        -Patron patron
        -LocalDateTime issueDate
        -LocalDateTime dueDate
        -LocalDateTime returnDate
        +getLoanId()
        +getDueDate()
        +isOverdue()
    }

    class Reservation {
        -String reservationId
        -Book book
        -Patron patron
        -Branch branch
        -LocalDateTime reservationDate
        -ReservationStatus status
        +getReservationId()
        +getStatus()
    }

    class FineRecord {
        -String fineId
        -Patron patron
        -double amount
        -LocalDateTime dueDate
        -FineStatus status
        +getFineId()
        +getAmount()
        +getStatus()
    }

    class FineTransaction {
        -String transactionId
        -FineRecord fineRecord
        -double amount
        -PaymentType paymentType
        -PaymentStatus status
        -LocalDateTime transactionDate
        +getTransactionId()
        +getAmount()
    }

    %% Repositories
    class BookRepository {
        -Map~String, Book~ books
        +addBook()
        +getBookById()
        +getAllBooks()
    }

    class BookCopyRepository {
        -Map~String, BookCopy~ copies
        +addCopy()
        +getCopyById()
        +getCopiesByBook()
    }

    class BranchRepository {
        -Map~String, Branch~ branches
        +addBranch()
        +getBranchById()
        +getAllBranches()
    }

    class BookTransferRepository {
        -Map~String, BookTransfer~ transfers
        +addTransfer()
        +getTransferById()
        +getTransfersByBranch()
    }

    class PatronRepository {
        -Map~String, Patron~ patrons
        +addPatron()
        +getPatronById()
        +getAllPatrons()
    }

    %% Services
    class LoanService {
        -LoanRepository loanRepo
        -BookCopyRepository copyRepo
        +issueLoan()
        +returnBook()
        +checkOverdue()
    }

    class ReservationService {
        -ReservationRepository reserveRepo
        -BookRepository bookRepo
        +createReservation()
        +cancelReservation()
        +processReservation()
    }

    class TransferService {
        -BookTransferRepository transferRepo
        -BranchRepository branchRepo
        +initiateTransfer()
        +completeTransfer()
        +cancelTransfer()
    }

    class NotificationService {
        -List~NotificationChannel~ channels
        +sendNotification()
        +notifyBranchStaff()
    }

    %% Enums
    class BookStatus {
        <<enumeration>>
        AVAILABLE
        BORROWED
        RESERVED
        IN_TRANSIT
        LOST
        DAMAGED
    }

    class BranchStatus {
        <<enumeration>>
        ACTIVE
        TEMPORARY_CLOSED
        PERMANENTLY_CLOSED
        UNDER_MAINTENANCE
    }

    class TransferStatus {
        <<enumeration>>
        INITIATED
        IN_TRANSIT
        COMPLETED
        CANCELLED
        FAILED
    }

    class AccountStatus {
        <<enumeration>>
        ACTIVE
        SUSPENDED
        CLOSED
    }

    class MembershipType {
        <<enumeration>>
        STANDARD
        PREMIUM
        STUDENT
    }
