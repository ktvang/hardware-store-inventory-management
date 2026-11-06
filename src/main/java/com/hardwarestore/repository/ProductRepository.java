
package com.hardwarestore.repository;

// Imports the Product model that this repository works with.
import com.hardwarestore.model.Product;

// Imports JpaRepository, which provides built-in database operations.
import org.springframework.data.jpa.repository.JpaRepository;

// Imports List to return multiple matching products.
import java.util.List;

// Imports Optional to handle cases where a product might not be found.
import java.util.Optional;

// Repository interface for accessing Product records in MySQL.
// JpaRepository<Product, Long> means:
// Product = the entity this repository manages.
// Long = the data type of the Product primary key.
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Searches for products where the name or SKU contains the search text.
    // IgnoreCase makes the search case-insensitive.
    // Returns a list because multiple products can match the search.
    List<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(
            String name, String sku);

    // Searches for a product using its exact SKU.
    // IgnoreCase means uppercase and lowercase letters are treated equally.
    // Optional handles the possibility that no matching product exists.
    Optional<Product> findBySkuIgnoreCase(String sku);
}
