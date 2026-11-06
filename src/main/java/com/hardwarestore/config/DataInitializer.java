
package com.hardwarestore.config;

// Imports the model classes used to create sample database records.
import com.hardwarestore.model.*;

// Imports the repositories used to save information into the database.
import com.hardwarestore.repository.*;

// Imports Spring Boot classes needed to run code when the application starts.
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Imports BigDecimal for storing product prices accurately.
import java.math.BigDecimal;

// Imports LocalDate for setting expected shipment arrival dates.
import java.time.LocalDate;

// Marks this class as a configuration class for Spring Boot.
@Configuration
public class DataInitializer {

    // Runs this method when the application starts.
    // The repositories are passed in so sample information can be saved to MySQL.
    @Bean
    CommandLineRunner loadSampleData(StoreRepository stores,
                                     ProductRepository products,
                                     InventoryRepository inventory,
                                     ShipmentRepository shipments) {

        // CommandLineRunner executes the code inside this block during startup.
        return args -> {

            // Checks whether store records already exist in the database.
            // If stores are already present, sample data will not be inserted again.
            if (stores.count() > 0) {
                return;
            }

            // Creates three sample store locations and saves them to MySQL.
            Store coonRapids = stores.save(new Store("Coon Rapids", "MN"));
            Store minneapolis = stores.save(new Store("Minneapolis", "MN"));
            Store blaine = stores.save(new Store("Blaine", "MN"));

            // Creates sample products with their names, SKUs, and prices.
            // BigDecimal is used to represent prices accurately.
            Product drill = products.save(new Product(
                    "Cordless Drill Kit", "TOOL-104", new BigDecimal("89.99")));

            Product hammer = products.save(new Product(
                    "Claw Hammer", "TOOL-205", new BigDecimal("18.50")));

            Product saw = products.save(new Product(
                    "Circular Saw", "TOOL-310", new BigDecimal("119.00")));

            Product gloves = products.save(new Product(
                    "Work Gloves", "SAFE-021", new BigDecimal("12.99")));

            Product tape = products.save(new Product(
                    "Measuring Tape", "TOOL-118", new BigDecimal("9.49")));

            // Adds the sample products to specific store locations.
            // Each inventory record connects a product, a store, and its quantity.
            inventory.save(new Inventory(drill, coonRapids, 14));
            inventory.save(new Inventory(hammer, coonRapids, 4));
            inventory.save(new Inventory(saw, coonRapids, 0));
            inventory.save(new Inventory(gloves, coonRapids, 32));
            inventory.save(new Inventory(tape, minneapolis, 18));
            inventory.save(new Inventory(hammer, blaine, 9));

            // Creates sample incoming shipments.
            // LocalDate.now() gets today's date, and plusDays() adds the expected
            // number of days until each shipment arrives.
            shipments.save(new Shipment(
                    coonRapids, saw, 8, LocalDate.now().plusDays(4), "Expected"));

            shipments.save(new Shipment(
                    minneapolis, drill, 10, LocalDate.now().plusDays(2), "Expected"));
        };
    }
}

