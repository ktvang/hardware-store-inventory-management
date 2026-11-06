
package com.hardwarestore.repository;

// Imports the Inventory model that this repository manages.
import com.hardwarestore.model.Inventory;

// Provides built-in database operations through Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// Allows us to write custom database queries using JPQL.
import org.springframework.data.jpa.repository.Query;

// Allows us to connect method parameters to named query parameters.
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Repository interface for managing inventory records in MySQL.
// Inventory = the model being managed.
// Long = the data type of the inventory primary key.
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Finds all inventory records belonging to a specific store.
    // For example, retrieves all inventory for the Coon Rapids store.
    List<Inventory> findByStoreStoreId(Long storeId);

    // Custom JPQL query that searches inventory for a specific store.
    // It checks both the product name and SKU.
    // LOWER makes the search case-insensitive.
    // CONCAT adds % before and after the search term,
    // allowing partial matches.
    @Query("SELECT i FROM Inventory i WHERE i.store.storeId = :storeId " +
           "AND (LOWER(i.product.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
           "OR LOWER(i.product.sku) LIKE LOWER(CONCAT('%', :term, '%')))")
    List<Inventory> searchStoreInventory(@Param("storeId") Long storeId,
                                         @Param("term") String term);

    // Finds one inventory record using both the product ID and store ID.
    // Optional means the record might not exist.
    // This helps prevent creating duplicate inventory records
    // for the same product at the same store.
    Optional<Inventory> findByProductProductIdAndStoreStoreId(Long productId, Long storeId);

    // Counts inventory records where the quantity is greater than
    // the number provided.
    // Can be used to count items that have stock available.
    long countByQuantityGreaterThan(int quantity);

    // Counts inventory records where the quantity is less than
    // or equal to the number provided.
    // Can be used to count items that are out of stock or have
    // a low quantity, depending on the number passed in.
    long countByQuantityLessThanEqual(int quantity);
}
