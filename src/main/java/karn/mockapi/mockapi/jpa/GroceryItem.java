package karn.mockapi.mockapi.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class GroceryItem {
    @Id
    @Column(name="grocery_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private String unit;
    private Date purchaseDate;
    private double price;
}
