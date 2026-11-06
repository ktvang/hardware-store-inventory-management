
package com.hardwarestore.repository;

// Imports the Shipment model that this repository manages.
import com.hardwarestore.model.Shipment;

// Provides built-in database operations through Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for managing shipment records in MySQL.
// Shipment = the model being managed.
// Long = the data type of the shipment primary key.
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    // Retrieves all shipments for a specific store.
    // Orders the results by arrival date, from earliest to latest.
    List<Shipment> findByStoreStoreIdOrderByArrivalDateAsc(Long storeId);

    // Retrieves shipments based on their status.
    // IgnoreCase means the search does not depend on uppercase
    // or lowercase letters.
    // Orders the shipments by arrival date, earliest first.
    List<Shipment> findByStatusIgnoreCaseOrderByArrivalDateAsc(String status);
}
