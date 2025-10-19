package library.service;

import java.util.List;
import java.util.UUID;

import library.model.BookCopy;
import library.model.BookTransfer;
import library.model.Branch;
import library.repository.BookCopyRepository;
import library.repository.BookTransferRepository;
import library.repository.BranchRepository;

public class TransferService {
    private BookTransferRepository transferRepository;
    private BranchRepository branchRepository;
    private BookCopyRepository bookCopyRepository;
    private NotificationService notificationService;

    public TransferService(BookTransferRepository transferRepository,
                         BranchRepository branchRepository,
                         BookCopyRepository bookCopyRepository,
                         NotificationService notificationService) {
        this.transferRepository = transferRepository;
        this.branchRepository = branchRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.notificationService = notificationService;
    }

    public BookTransfer initiateTransfer(BookCopy bookCopy, Branch sourceBranch, Branch destinationBranch) {
        // Validate branches exist
        if (!branchRepository.exists(sourceBranch.getBranchId()) || 
            !branchRepository.exists(destinationBranch.getBranchId())) {
            throw new IllegalArgumentException("Invalid branch specified");
        }

        // Validate book copy exists in source branch
        if (!sourceBranch.hasBookCopy(bookCopy)) {
            throw new IllegalArgumentException("Book copy not available in source branch");
        }

        // Create transfer record
        String transferId = UUID.randomUUID().toString();
        BookTransfer transfer = new BookTransfer(transferId, bookCopy, sourceBranch, destinationBranch);
        
        // Update book copy status and location
        sourceBranch.removeBookCopy(bookCopy);
        bookCopy.setStatus(BookStatus.IN_TRANSIT);
        
        // Save transfer record
        transferRepository.addTransfer(transfer);
        
        // Notify relevant parties
        notificationService.notifyBranchStaff(sourceBranch, 
            "Book transfer initiated for " + bookCopy.getBook().getTitle());
        notificationService.notifyBranchStaff(destinationBranch, 
            "Incoming book transfer for " + bookCopy.getBook().getTitle());

        return transfer;
    }

    public void completeTransfer(String transferId) {
        BookTransfer transfer = transferRepository.getTransferById(transferId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transfer ID"));

        if (transfer.getStatus() != BookTransfer.TransferStatus.IN_TRANSIT) {
            throw new IllegalStateException("Transfer is not in transit");
        }

        BookCopy bookCopy = transfer.getBookCopy();
        Branch destinationBranch = transfer.getDestinationBranch();

        // Update book copy status and location
        bookCopy.setStatus(BookStatus.AVAILABLE);
        destinationBranch.addBookCopy(bookCopy);

        // Update transfer status
        transfer.setStatus(BookTransfer.TransferStatus.COMPLETED);
        transferRepository.updateTransfer(transfer);

        // Notify relevant parties
        notificationService.notifyBranchStaff(transfer.getSourceBranch(), 
            "Book transfer completed for " + bookCopy.getBook().getTitle());
        notificationService.notifyBranchStaff(destinationBranch, 
            "Book transfer received: " + bookCopy.getBook().getTitle());
    }

    public void cancelTransfer(String transferId) {
        BookTransfer transfer = transferRepository.getTransferById(transferId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transfer ID"));

        if (transfer.getStatus() == BookTransfer.TransferStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed transfer");
        }

        BookCopy bookCopy = transfer.getBookCopy();
        Branch sourceBranch = transfer.getSourceBranch();

        // Return book copy to source branch
        bookCopy.setStatus(BookStatus.AVAILABLE);
        sourceBranch.addBookCopy(bookCopy);

        // Update transfer status
        transfer.setStatus(BookTransfer.TransferStatus.CANCELLED);
        transferRepository.updateTransfer(transfer);

        // Notify relevant parties
        notificationService.notifyBranchStaff(sourceBranch, 
            "Book transfer cancelled for " + bookCopy.getBook().getTitle());
        notificationService.notifyBranchStaff(transfer.getDestinationBranch(), 
            "Book transfer cancelled for " + bookCopy.getBook().getTitle());
    }

    public List<BookTransfer> getTransfersBySourceBranch(Branch branch) {
        return transferRepository.getTransfersBySourceBranch(branch);
    }

    public List<BookTransfer> getTransfersByDestinationBranch(Branch branch) {
        return transferRepository.getTransfersByDestinationBranch(branch);
    }
}