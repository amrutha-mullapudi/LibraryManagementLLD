package library.repository;

import library.model.BorrowingProfile;
import java.util.*;

public class BorrowingProfileRepository {
    private final Map<String, BorrowingProfile> profileStorage = new HashMap<>();

    public BorrowingProfile save(BorrowingProfile profile) {
        profileStorage.put(profile.getId(), profile);
        return profile;
    }

    public void delete(String id) {
        profileStorage.remove(id);
    }

    public BorrowingProfile findById(String id) {
        return profileStorage.get(id);
    }

    public BorrowingProfile findByPatronId(String patronId) {
        return profileStorage.values().stream()
            .filter(profile -> profile.getPatronId().equals(patronId))
            .findFirst()
            .orElse(null);
    }

    public List<BorrowingProfile> findAll() {
        return new ArrayList<>(profileStorage.values());
    }

    public List<BorrowingProfile> findAllWithOverdueBooks() {
        return profileStorage.values().stream()
            .filter(BorrowingProfile::hasOverdueBooks)
            .toList();
    }
}