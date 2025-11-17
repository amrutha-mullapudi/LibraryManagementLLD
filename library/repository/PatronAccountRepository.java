package library.repository;

import library.model.PatronAccount;
import java.util.*;

public class PatronAccountRepository {
    private final Map<String, PatronAccount> accountStorage = new HashMap<>();

    public PatronAccount save(PatronAccount account) {
        accountStorage.put(account.getId(), account);
        return account;
    }

    public void delete(String id) {
        accountStorage.remove(id);
    }

    public PatronAccount findById(String id) {
        return accountStorage.get(id);
    }

    public PatronAccount findByPatronId(String patronId) {
        return accountStorage.values().stream()
            .filter(account -> account.getPatronId().equals(patronId))
            .findFirst()
            .orElse(null);
    }

    public List<PatronAccount> findAll() {
        return new ArrayList<>(accountStorage.values());
    }
}