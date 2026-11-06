
package com.hardwarestore.model;

// Imports JPA annotations used to connect this class to the database.
import jakarta.persistence.*;

// Marks this class as a database entity.
@Entity

// Connects this Java class to the "stores" table in MySQL.
@Table(name = "stores")
public class Store {

    // Primary key used to uniquely identify each store.
    @Id

    // Automatically generates a new ID when a store is added.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    // Stores the city where the hardware store is located.
    // This field cannot be empty in the database.
    @Column(nullable = false)
    private String city;

    // Stores the two-letter state abbreviation.
    // This field cannot be empty and has a maximum length of 2 characters.
    @Column(nullable = false, length = 2)
    private String state;

    // Default constructor required by JPA when retrieving stores from MySQL.
    public Store() {
    }

    // Constructor used to create a store with its city and state.
    public Store(String city, String state) {
        this.city = city;
        this.state = state;
    }

    // Returns the store's ID.
    public Long getStoreId() { return storeId; }

    // Updates the store's ID.
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    // Returns the store's city.
    public String getCity() { return city; }

    // Updates the store's city.
    public void setCity(String city) { this.city = city; }

    // Returns the store's state abbreviation.
    public String getState() { return state; }

    // Updates the store's state abbreviation.
    public void setState(String state) { this.state = state; }

    // Combines the city and state into one readable name.
    // Example: "Coon Rapids, MN"
    public String getDisplayName() {
        return city + ", " + state;
    }
}
