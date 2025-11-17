package catalog;

import java.util.List;

/**
 * Generic interface for searching any type of entity.
 * @param <T> The type of entity to search
 */
public interface SearchStrategy<T> {
    List<T> search(List<T> items, String query);
}
