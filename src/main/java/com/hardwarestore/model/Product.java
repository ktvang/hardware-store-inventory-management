
package com.hardwarestore.model;

// Imports JPA annotations used to connect this Java class to a database table.
import jakarta.persistence.*;

// BigDecimal is used to store product prices accurately.
import java.math.BigDecimal;

// Tells JPA that this class represents a database entity.
@Entity

// Specifies that this class will be connected to the "products" table in MySQL.
@Table(name = "products")
public class Product {

    // Marks productId as the primary key of the products table.
    @Id

    // Automatically generates a new ID whenever a product is inserted.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // Stores the product name.
    // The name column cannot be left empty in the database.
    @Column(nullable = false)
    private String name;

    // Stores the product's Stock Keeping Unit (SKU).
    // Each product must have a unique SKU, and it can contain up to 12 characters.
    @Column(nullable = false, unique = true, length = 12)
    private String sku;

    // Stores the product's price.
    // Precision 10 allows up to 10 total digits, with 2 digits after the decimal.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Default constructor required by JPA when creating Product objects
    // from database records.
    public Product() {
    }

    // Constructor used to create a new product with its name, SKU, and price.
    public Product(String name, String sku, BigDecimal price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    // Returns the product's ID.
    public Long getProductId() { return productId; }

    // Changes the product's ID.
    public void setProductId(Long productId) { this.productId = productId; }

    // Returns the product's name.
    public String getName() { return name; }

    // Updates the product's name.
    public void setName(String name) { this.name = name; }

    // Returns the product's SKU.
    public String getSku() { return sku; }

    // Updates the product's SKU.
    public void setSku(String sku) { this.sku = sku; }

    // Returns the product's price.
    public BigDecimal getPrice() { return price; }

    // Updates the product's price.
    public void setPrice(BigDecimal price) { this.price = price; }
}
