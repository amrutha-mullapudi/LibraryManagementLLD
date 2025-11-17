package catalog;

import model.Book;
import java.util.ArrayList;
import java.util.List;

public class SearchByAuthor implements SearchStrategy<Book> {

    @Override
    public List<Book> search(List<Book> items, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : items) {
            if (book.getAuthor().equalsIgnoreCase(query)) {
                result.add(book);
            }
        }
        return result;
    }
}
