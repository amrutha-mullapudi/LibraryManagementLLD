package repository;

import java.util.List;
import java.util.Optional;

public interface InventoryManagement<T, ID> {
    void add(T entity);
    void remove(ID id);
    void update(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}
