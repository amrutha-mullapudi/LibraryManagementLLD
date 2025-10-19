package repository;

import model.Patron;
import java.util.*;

public class PatronRepository {
    private final Map<String, Patron> patronStorage = new HashMap<>();

    public void add(Patron patron) { patronStorage.put(patron.getPatronId(), patron); }

    public void remove(String patronId) { patronStorage.remove(patronId); }

    public void update(Patron patron) { patronStorage.put(patron.getPatronId(), patron); }

    public Optional<Patron> findById(String patronId) { 
        return Optional.ofNullable(patronStorage.get(patronId)); 
    }

    public List<Patron> findAll() { 
        return new ArrayList<>(patronStorage.values()); 
    }
}
