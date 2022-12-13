package karn.mockapi.mockapi.controller;

import karn.mockapi.mockapi.jpa.GroceryItem;
import karn.mockapi.mockapi.model.CustomResponse;
import karn.mockapi.mockapi.model.DeleteBody;
import karn.mockapi.mockapi.model.ResponseTypes;
import karn.mockapi.mockapi.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/grocery")
public class GroceryController {

    @Autowired
    private GroceryService groceryService;

    /**
     * Access : http://localhost:8080/grocery/getAll
     * */
    @RolesAllowed("ROLE_ADMIN")
    @RequestMapping("/getAll")
    public ResponseEntity<CustomResponse<List<GroceryItem>>> getAllEmployee(){
        List<GroceryItem> allGroceryItems = groceryService.getAllItems();
        return ResponseEntity.ok().body(new CustomResponse<>(ResponseTypes.ALL_ITEMS, allGroceryItems));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/findByName/{name}")
    public ResponseEntity<CustomResponse<List<GroceryItem>>> findByName(@PathVariable String name){
        List<GroceryItem> allGroceryItems = groceryService.findByName(name.toLowerCase());
        return ResponseEntity.ok().body(new CustomResponse<>(ResponseTypes.BY_NAME, allGroceryItems));
    }


    @RequestMapping(value = "/addItem",method = RequestMethod.POST)
    public ResponseEntity<CustomResponse<Long>> addItem(@RequestBody GroceryItem item){
        Long itemId = groceryService.addItem(item);
        return ResponseEntity.ok().body(new CustomResponse<>(ResponseTypes.ITEM_ADDED, itemId));
    }

    @RequestMapping(value = "/deleteItem",method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<Set<Long>>> deleteItem(@RequestBody DeleteBody ids){
        System.out.println(ids);
        Set<Long> itemId = groceryService.deleteItem(ids.ids());
        return ResponseEntity.ok().body(new CustomResponse<>(ResponseTypes.ITEMS_DELETED, itemId));
    }

    @RequestMapping(value = "/deleteAll",method = RequestMethod.DELETE)
    public ResponseEntity<CustomResponse<String>> deleteAll(){
         groceryService.deleteAllItem();
        return ResponseEntity.ok().body(new CustomResponse<>(ResponseTypes.ITEMS_DELETED, ""));
    }


}
