package catalog;

import model.Book;
import java.util.ArrayList;
import java.util.List;

public class SearchByGenre implements SearchStrategy<Book> {

    @Override
    public List<Book> search(List<Book> items, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : items) {
            if (book.getGenre().equalsIgnoreCase(query)) {
                result.add(book);
            }
        }
        return result;
    }
}
