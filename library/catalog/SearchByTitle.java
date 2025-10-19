package catalog;

import model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic strategy for searching by title.
 */
public class SearchByTitle implements SearchStrategy<Book> {

    @Override
    public List<Book> search(List<Book> items, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : items) {
            if (book.getTitle().equalsIgnoreCase(query)) {
                result.add(book);
            }
        }
        return result;
    }
}
