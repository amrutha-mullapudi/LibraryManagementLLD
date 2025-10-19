package catalog;

import model.LibraryItem;
import repository.InventoryManagement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic catalog for any LibraryItem
 */
public class Catalog<T extends LibraryItem> {

    private final InventoryManagement<T, String> inventory;

    public Catalog(InventoryManagement<T, String> inventory) {
        this.inventory = inventory;
    }

    public List<T> searchItems(String query, SearchStrategy<T> strategy) {
        return strategy.search(inventory.findAll(), query);
    }

    public T findById(String id) {
        return inventory.findById(id).orElse(null);
    }

    public List<T> filterByStatus(BookStatus status) {
        return inventory.findAll().stream()
                .filter(item -> item instanceof BookCopy && ((BookCopy)item).getStatus() == status)
                .collect(Collectors.toList());
    }
}
