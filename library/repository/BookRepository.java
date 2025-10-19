package repository;

import model.Book;

import java.util.*;

public class BookRepository implements InventoryManagement<Book, String> {
    private final Map<String, Book> bookStorage = new HashMap<>();

    @Override
    public void add(Book book) { bookStorage.put(book.getBookId(), book); }

    @Override
    public void remove(String bookId) { bookStorage.remove(bookId); }

    @Override
    public void update(Book book) { bookStorage.put(book.getBookId(), book); }

    @Override
    public Optional<Book> findById(String bookId) { return Optional.ofNullable(bookStorage.get(bookId)); }

    @Override
    public List<Book> findAll() { return new ArrayList<>(bookStorage.values()); }

    // Additional search methods
    public List<Book> findByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookStorage.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) result.add(book);
        }
        return result;
    }

    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookStorage.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) result.add(book);
        }
        return result;
    }
}
