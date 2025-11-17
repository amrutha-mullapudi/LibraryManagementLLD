package com.library.model;

import com.library.model.enums.BookStatus;
import java.util.Objects;
import model.LibraryItem;

public class BookCopy implements LibraryItem {
    private final String copyId;     // Unique ID per physical copy
    private final Book book;         // Reference to the base Book
    private BookStatus status;
    private String shelfLocation;
    private String condition;

    public BookCopy(String copyId, Book book, BookStatus status, String shelfLocation, String condition) {
        this.copyId = copyId;
        this.book = book;
        this.status = status;
        this.shelfLocation = shelfLocation;
        this.condition = condition;
    }

    public String getCopyId() { return copyId; }
    public Book getBook() { return book; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    public String getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(String shelfLocation) { this.shelfLocation = shelfLocation; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy)) return false;
        BookCopy copy = (BookCopy) o;
        return copyId.equals(copy.copyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(copyId);
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "copyId='" + copyId + '\'' +
                ", bookTitle='" + book.getTitle() + '\'' +
                ", status=" + status +
                ", shelfLocation='" + shelfLocation + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
