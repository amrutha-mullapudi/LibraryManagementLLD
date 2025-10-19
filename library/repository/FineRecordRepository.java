package library.repository;

import library.model.FineRecord;
import library.model.enums.FineStatus;
import java.util.*;

public class FineRecordRepository {
    private final Map<String, FineRecord> fineStorage = new HashMap<>();

    public FineRecord save(FineRecord fine) {
        fineStorage.put(fine.getId(), fine);
        return fine;
    }

    public void delete(String id) {
        fineStorage.remove(id);
    }

    public FineRecord findById(String id) {
        return fineStorage.get(id);
    }

    public List<FineRecord> findByPatronId(String patronId) {
        return fineStorage.values().stream()
            .filter(fine -> fine.getPatronId().equals(patronId))
            .toList();
    }

    public List<FineRecord> findByPatronIdAndStatus(String patronId, FineStatus status) {
        return fineStorage.values().stream()
            .filter(fine -> fine.getPatronId().equals(patronId) && 
                          fine.getStatus() == status)
            .toList();
    }

    public List<FineRecord> findAll() {
        return new ArrayList<>(fineStorage.values());
    }
}