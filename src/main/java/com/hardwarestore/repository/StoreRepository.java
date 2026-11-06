
package com.hardwarestore.repository;

// Imports the Store model that this repository manages.
import com.hardwarestore.model.Store;

// Imports JpaRepository, which provides built-in database operations.
import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface for accessing Store records in MySQL.
// JpaRepository<Store, Long> means:
// Store = the entity this repository manages.
// Long = the data type of the Store primary key.
public interface StoreRepository extends JpaRepository<Store, Long> {

    // No additional methods are needed right now.
    // JpaRepository already provides methods to save, find,
    // update, and delete store records.
}
