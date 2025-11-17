package library.repository;

import library.model.FineTransaction;
import library.model.enums.PaymentStatus;
import java.util.*;
import java.util.stream.Collectors;

public class FineTransactionRepository {
    private final Map<String, FineTransaction> transactionStorage = new HashMap<>();

    public FineTransaction save(FineTransaction transaction) {
        transactionStorage.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    public void delete(String transactionId) {
        transactionStorage.remove(transactionId);
    }

    public Optional<FineTransaction> findById(String transactionId) {
        return Optional.ofNullable(transactionStorage.get(transactionId));
    }

    public List<FineTransaction> findByPatronId(String patronId) {
        return transactionStorage.values().stream()
                .filter(t -> t.getPatronId().equals(patronId))
                .collect(Collectors.toList());
    }

    public List<FineTransaction> findByStatus(PaymentStatus status) {
        return transactionStorage.values().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<FineTransaction> findByFineId(String fineId) {
        return transactionStorage.values().stream()
                .filter(t -> t.getFineId().equals(fineId))
                .collect(Collectors.toList());
    }

    public List<FineTransaction> findAll() {
        return new ArrayList<>(transactionStorage.values());
    }
}