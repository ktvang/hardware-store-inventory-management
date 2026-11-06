
package com.hardwarestore.model;

// Imports JPA annotations used to connect this class to the database.
import jakarta.persistence.*;

// LocalDate is used to store the expected arrival date of a shipment.
import java.time.LocalDate;

// Marks this class as a database entity.
@Entity

// Connects this Java class to the "shipments" table in MySQL.
@Table(name = "shipments")
public class Shipment {

    // Primary key used to uniquely identify each shipment.
    @Id

    // Automatically generates a new ID when a shipment is created.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    // Connects the shipment to the store receiving the delivery.
    // Multiple shipments can be delivered to the same store.
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    // Connects the shipment to the product being delivered.
    // Multiple shipments can contain the same product.
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    // Stores the number of units included in the shipment.
    // This field cannot be empty in the database.
    @Column(nullable = false)
    private int quantity;

    // Stores the expected date when the shipment will arrive.
    @Column(nullable = false)
    private LocalDate arrivalDate;

    // Stores the current shipment status.
    // Example: Expected or Received.
    @Column(nullable = false)
    private String status;

    // Default constructor required by JPA when retrieving shipments from MySQL.
    public Shipment() {
    }

    // Constructor used to create a shipment with its store, product,
    // quantity, expected arrival date, and current status.
    public Shipment(Store store, Product product, int quantity,
                    LocalDate arrivalDate, String status) {
        this.store = store;
        this.product = product;
        this.quantity = quantity;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    // Returns the shipment's ID.
    public Long getShipmentId() { return shipmentId; }

    // Returns the store receiving the shipment.
    public Store getStore() { return store; }

    // Returns the product included in the shipment.
    public Product getProduct() { return product; }

    // Returns the number of units being delivered.
    public int getQuantity() { return quantity; }

    // Returns the expected arrival date.
    public LocalDate getArrivalDate() { return arrivalDate; }

    // Returns the current shipment status.
    public String getStatus() { return status; }

    // Updates the shipment status.
    // This can be used when a shipment changes from Expected to Received.
    public void setStatus(String status) { this.status = status; }
}

