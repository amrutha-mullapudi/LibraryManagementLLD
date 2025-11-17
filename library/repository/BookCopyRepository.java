package library.repository;

import library.model.BookCopy;
import library.model.enums.BookStatus;

import java.util.*;

public class BookCopyRepository {
    private final Map<String, BookCopy> copyStorage = new HashMap<>();

    public BookCopy save(BookCopy copy) {
        copyStorage.put(copy.getId(), copy);
        return copy;
    }

    public void delete(String copyId) {
        copyStorage.remove(copyId);
    }

    public BookCopy update(BookCopy copy) {
        copyStorage.put(copy.getId(), copy);
        return copy;
    }

    public BookCopy findById(String copyId) {
        return copyStorage.get(copyId);
    }

    public List<BookCopy> findAll() {
        return new ArrayList<>(copyStorage.values());
    }

    public List<BookCopy> findCopiesByBookId(String bookId) {
        return copyStorage.values().stream()
            .filter(copy -> copy.getBook().getId().equals(bookId))
            .toList();
    }

    public List<BookCopy> findAvailableCopies(String bookId) {
        return copyStorage.values().stream()
            .filter(copy -> copy.getBook().getId().equals(bookId) 
                && copy.getStatus() == BookStatus.AVAILABLE)
            .toList();
    }

    public List<BookCopy> findByStatus(BookStatus status) {
        return copyStorage.values().stream()
            .filter(copy -> copy.getStatus() == status)
            .toList();
    }
}
