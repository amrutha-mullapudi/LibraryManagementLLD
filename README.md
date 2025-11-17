# Library Management System

A robust and scalable library management system designed to handle multiple branches, book management, patron services, and inter-branch book transfers.

- **Multi-Branch Support**
  - Manage multiple library branches
  - Track branch status (Active, Temporary Closed, etc.)
  - Inter-branch book transfers
  - Branch-specific inventory management

- **Book Management**
  - Book and book copy tracking
  - ISBN-based cataloging
  - Status tracking (Available, Borrowed, Reserved, etc.)
  - Search by title, author, ISBN, and genre

- **Patron Services**
  - Membership management
  - Book borrowing and returns
  - Reservations
  - Fine calculation and payment

- **Inventory Management**
  - Real-time inventory tracking
  - Book transfer management
  - Copy status monitoring
  - Branch-wise stock management

1. **Models**
   - Book and BookCopy for book management
   - Branch for library branch management
   - Patron and PatronAccount for user management
   - Loan and Reservation for lending services
   - FineRecord and FineTransaction for fine management

2. **Services**
   - LoanService for borrowing operations
   - ReservationService for book reservations
   - TransferService for inter-branch transfers
   - NotificationService for system notifications

3. **Repositories**
   - Implements data access layer for all entities
   - In-memory storage with Map-based implementation
   - Extensible for future database integration

### Design Patterns

- Repository Pattern for data access
- Strategy Pattern for search functionality
- Observer Pattern for notifications

## Class Structure

The system is organized into several packages:

```
library/
├── catalog/          # Search and cataloging
├── model/            # Domain entities
├── repository/       # Data access layer
├── service/         # Business logic
└── notification/     # Notification system
```

Refer to `class-diagram.md` for the complete class diagram.

## Future Enhancements

- Database integration
- RESTful API implementation
- Web-based user interface
- Online catalog access
- Digital content management
- Analytics and reporting
- Mobile application
