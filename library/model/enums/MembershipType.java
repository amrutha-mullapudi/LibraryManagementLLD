package library.model.enums;

public enum MembershipType {
    BASIC(5),    // Can borrow up to 5 books
    PREMIUM(10), // Can borrow up to 10 books
    STUDENT(3);  // Can borrow up to 3 books

    private final int maxBooks;

    MembershipType(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    public int getMaxBooks() {
        return maxBooks;
    }
}