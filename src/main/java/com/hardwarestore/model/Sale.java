
package com.hardwarestore.model;

// Imports JPA annotations used to connect this class to the database.
import jakarta.persistence.*;

// BigDecimal is used to store money accurately.
import java.math.BigDecimal;

// LocalDateTime stores both the date and time of a sale.
import java.time.LocalDateTime;

// Marks this class as a database entity.
@Entity

// Connects this Java class to the "sales" table in MySQL.
@Table(name = "sales")
public class Sale {

    // Primary key used to uniquely identify each sale.
    @Id

    // Automatically generates a new ID when a sale is recorded.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    // Connects the sale to the store where it took place.
    // Many sales can be associated with the same store.
    // A store must be assigned to each sale.
    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    // Stores the date and time when the sale occurred.
    // This field cannot be empty in the database.
    @Column(nullable = false)
    private LocalDateTime dateOfSale;

    // Stores the total amount of the sale.
    // Precision 12 allows 12 total digits, with 2 digits after the decimal.
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    // Default constructor required by JPA when retrieving sales from MySQL.
    public Sale() {
    }

    // Constructor used to create a sale with its store, date, and total.
    public Sale(Store store, LocalDateTime dateOfSale, BigDecimal total) {
        this.store = store;
        this.dateOfSale = dateOfSale;
        this.total = total;
    }

    // Returns the sale's ID.
    public Long getSaleId() { return saleId; }

    // Returns the store where the sale occurred.
    public Store getStore() { return store; }

    // Returns the date and time of the sale.
    public LocalDateTime getDateOfSale() { return dateOfSale; }

    // Returns the total amount of the sale.
    public BigDecimal getTotal() { return total; }
}
