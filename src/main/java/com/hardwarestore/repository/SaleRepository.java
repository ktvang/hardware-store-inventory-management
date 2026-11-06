
package com.hardwarestore.repository;

// Imports the Sale model that this repository manages.
import com.hardwarestore.model.Sale;

// Provides built-in database operations through Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for managing sales records in MySQL.
// Sale = the model being managed.
// Long = the data type of the sale primary key.
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Retrieves the 10 most recent sales records.
    // OrderByDateOfSaleDesc sorts them by sale date and time,
    // with the newest sales appearing first.
    List<Sale> findTop10ByOrderByDateOfSaleDesc();
}
