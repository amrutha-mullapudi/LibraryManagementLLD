package catalog;

import model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Search books by ISBN
 */
public class SearchByISBN implements SearchStrategy<Book> {

    @Override
    public List<Book> search(List<Book> items, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : items) {
            if (book.getISBN().equalsIgnoreCase(query)) {
                result.add(book);
            }
        }
        return result;
    }
}
