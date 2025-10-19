package library.service;

import library.model.FineTransaction;
import library.model.enums.PaymentStatus;
import library.model.enums.PaymentType;
import library.repository.FineTransactionRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class FineTransactionService {
    private final FineTransactionRepository transactionRepository;
    private final FineService fineService;

    public FineTransactionService(
            FineTransactionRepository transactionRepository,
            FineService fineService) {
        this.transactionRepository = transactionRepository;
        this.fineService = fineService;
    }

    public FineTransaction createTransaction(
            String patronId, 
            String fineId, 
            BigDecimal amount,
            PaymentType paymentType,
            String paymentReference) {
        
        FineTransaction transaction = new FineTransaction.Builder(
                patronId, fineId, amount, paymentType)
                .paymentReference(paymentReference)
                .build();
        
        return transactionRepository.save(transaction);
    }

    public FineTransaction processPayment(String transactionId) {
        Optional<FineTransaction> transactionOpt = transactionRepository.findById(transactionId);
        
        if (transactionOpt.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found: " + transactionId);
        }

        FineTransaction transaction = transactionOpt.get();
        
        // Update transaction status to processing
        transaction.updateStatus(PaymentStatus.PROCESSING);
        transactionRepository.save(transaction);

        try {
            // Process payment (in a real system, this would integrate with a payment gateway)
            // For now, we'll simulate successful payment
            processPaymentWithGateway(transaction);

            // Update transaction status to completed
            transaction.updateStatus(PaymentStatus.COMPLETED);
            transaction.addNotes("Payment processed successfully");

            // Update the fine status
            fineService.markFineAsPaid(transaction.getFineId());

        } catch (Exception e) {
            transaction.updateStatus(PaymentStatus.FAILED);
            transaction.addNotes("Payment failed: " + e.getMessage());
            throw new RuntimeException("Payment processing failed", e);
        }

        return transactionRepository.save(transaction);
    }

    public FineTransaction refundTransaction(String transactionId, String reason) {
        Optional<FineTransaction> transactionOpt = transactionRepository.findById(transactionId);
        
        if (transactionOpt.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found: " + transactionId);
        }

        FineTransaction transaction = transactionOpt.get();
        
        if (transaction.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Can only refund completed transactions");
        }

        // Process refund
        try {
            processRefundWithGateway(transaction);
            transaction.updateStatus(PaymentStatus.REFUNDED);
            transaction.addNotes("Refunded: " + reason);
        } catch (Exception e) {
            transaction.addNotes("Refund failed: " + e.getMessage());
            throw new RuntimeException("Refund processing failed", e);
        }

        return transactionRepository.save(transaction);
    }

    public List<FineTransaction> getPatronTransactions(String patronId) {
        return transactionRepository.findByPatronId(patronId);
    }

    public List<FineTransaction> getTransactionsByStatus(PaymentStatus status) {
        return transactionRepository.findByStatus(status);
    }

    private void processPaymentWithGateway(FineTransaction transaction) {
        // In a real implementation, this would integrate with a payment gateway
        // For now, we'll just simulate the process
        try {
            Thread.sleep(1000); // Simulate processing time
            if (transaction.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
                throw new RuntimeException("Amount exceeds maximum limit");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Payment processing interrupted");
        }
    }

    private void processRefundWithGateway(FineTransaction transaction) {
        // Similar to processPaymentWithGateway, this would integrate with a payment gateway
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Refund processing interrupted");
        }
    }
}