package karn.mockapi.mockapi.repository;

import karn.mockapi.mockapi.jpa.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<GroceryItem, Long> {
    @Query("Select c from GroceryItem c where LOWER(c.name) LIKE %:name%")
    List<GroceryItem> findAllByName(String name);
}
