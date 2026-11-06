
package com.hardwarestore.model;

// Imports JPA annotations used to connect this class to the database.
import jakarta.persistence.*;

// Marks this class as a database entity.
@Entity

// Connects this Java class to the "inventory" table.
// The unique constraint prevents the same product from being
// entered more than once for the same store.
@Table(name = "inventory",
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "store_id"}))
public class Inventory {

    // Primary key used to uniquely identify each inventory record.
    @Id

    // Automatically generates a new ID when an inventory record is created.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    // Connects an inventory record to a product.
    // Many inventory records can reference the same product.
    // A product must be assigned to an inventory record.
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    // Connects an inventory record to a store location.
    // Many inventory records can belong to the same store.
    // A store must be assigned to an inventory record.
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    // Stores how many units of the product are available at this store.
    // This value cannot be empty in the database.
    @Column(nullable = false)
    private int quantity;

    // Default constructor required by JPA when retrieving records from MySQL.
    public Inventory() {
    }

    // Constructor used to create an inventory record.
    // It receives the product, store, and available quantity.
    public Inventory(Product product, Store store, int quantity) {
        this.product = product;
        this.store = store;
        this.quantity = quantity;
    }

    // Returns the inventory record's ID.
    public Long getInventoryId() { return inventoryId; }

    // Updates the inventory record's ID.
    public void setInventoryId(Long inventoryId) { this.inventoryId = inventoryId; }

    // Returns the product connected to this inventory record.
    public Product getProduct() { return product; }

    // Changes the product connected to this inventory record.
    public void setProduct(Product product) { this.product = product; }

    // Returns the store connected to this inventory record.
    public Store getStore() { return store; }

    // Changes the store connected to this inventory record.
    public void setStore(Store store) { this.store = store; }

    // Returns the current quantity of the product.
    public int getQuantity() { return quantity; }

    // Updates the available quantity.
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Checks the quantity and returns a description of the stock level.
    public String getStockStatus() {

        // Returns "Out of stock" when there are no units available.
        if (quantity == 0) return "Out of stock";

        // Returns "Low stock" when there are 5 or fewer units available.
        if (quantity <= 5) return "Low stock";

        // Returns "In stock" when more than 5 units are available.
        return "In stock";
    }
}

