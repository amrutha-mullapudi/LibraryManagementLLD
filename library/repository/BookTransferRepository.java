package library.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import library.model.BookTransfer;
import library.model.Branch;

public class BookTransferRepository {
    private Map<String, BookTransfer> transfers;

    public BookTransferRepository() {
        this.transfers = new HashMap<>();
    }

    public void addTransfer(BookTransfer transfer) {
        transfers.put(transfer.getTransferId(), transfer);
    }

    public Optional<BookTransfer> getTransferById(String transferId) {
        return Optional.ofNullable(transfers.get(transferId));
    }

    public List<BookTransfer> getAllTransfers() {
        return new ArrayList<>(transfers.values());
    }

    public List<BookTransfer> getTransfersBySourceBranch(Branch sourceBranch) {
        return transfers.values().stream()
            .filter(transfer -> transfer.getSourceBranch().equals(sourceBranch))
            .collect(Collectors.toList());
    }

    public List<BookTransfer> getTransfersByDestinationBranch(Branch destinationBranch) {
        return transfers.values().stream()
            .filter(transfer -> transfer.getDestinationBranch().equals(destinationBranch))
            .collect(Collectors.toList());
    }

    public void updateTransfer(BookTransfer transfer) {
        if (transfers.containsKey(transfer.getTransferId())) {
            transfers.put(transfer.getTransferId(), transfer);
        }
    }

    public void removeTransfer(String transferId) {
        transfers.remove(transferId);
    }
}