package karn.mockapi.mockapi.service;

import karn.mockapi.mockapi.jpa.GroceryItem;

import java.util.List;
import java.util.Set;

public interface GroceryService {
    List<GroceryItem> getAllItems();
    List<GroceryItem> findByName(String name);
    Long addItem(GroceryItem item);
    Set<Long> deleteItem(Set<Long> id);
    String deleteAllItem();
}
