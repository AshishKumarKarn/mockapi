package karn.mockapi.mockapi.service;

import karn.mockapi.mockapi.jpa.GroceryItem;
import karn.mockapi.mockapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroceryServiceImpl implements GroceryService {


    @Autowired
    private ItemRepository employeeRepository;

    @Override
    public List<GroceryItem> getAllItems() {
        List<GroceryItem> items = employeeRepository.findAll();
        return items;
    }

    @Override
    public List<GroceryItem> findByName(String name) {
        return employeeRepository.findAllByName(name);
    }

    @Override
    public Long addItem(GroceryItem item) {
        GroceryItem groceryItem = employeeRepository.save(item);
        return groceryItem.getId();
    }

    @Override
    public Set<Long> deleteItem(Set<Long> id) {
        employeeRepository.deleteAllById(id);
        return id;
    }

    @Override
    public String deleteAllItem() {
        employeeRepository.deleteAll();
        return "success";
    }
}
