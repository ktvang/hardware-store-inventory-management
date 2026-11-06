
package com.hardwarestore.service;

// Imports the model classes used by the service.
import com.hardwarestore.model.*;

// Imports the repositories used to communicate with the database.
import com.hardwarestore.repository.*;

// Marks this class as a Spring service containing business logic.
import org.springframework.stereotype.Service;

// Makes sure database operations in a method are treated as one transaction.
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Service class that handles the main inventory operations
// for the HardwareHub application.
@Service
public class InventoryService {

    // Repositories used to access and manage database records.
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;
    private final ShipmentRepository shipmentRepository;

    // Constructor that receives the repositories from Spring.
    // This is called dependency injection.
    public InventoryService(ProductRepository productRepository,
                            StoreRepository storeRepository,
                            InventoryRepository inventoryRepository,
                            SaleRepository saleRepository,
                            ShipmentRepository shipmentRepository) {

        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.inventoryRepository = inventoryRepository;
        this.saleRepository = saleRepository;
        this.shipmentRepository = shipmentRepository;
    }

    // Adds a new product and creates its inventory record
    // for the selected store.
    @Transactional
    public void addProduct(String name, String sku, BigDecimal price,
                           int quantity, Long storeId) {

        // Makes sure the product name and SKU are not empty.
        if (name == null || name.isBlank() || sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Product name and SKU are required.");
        }

        // Makes sure the price is greater than zero
        // and the starting quantity is not negative.
        if (price == null || price.signum() <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Enter a positive price and a quantity of zero or more.");
        }

        // Checks whether another product already uses this SKU.
        if (productRepository.findBySkuIgnoreCase(sku.trim()).isPresent()) {
            throw new IllegalArgumentException("That SKU is already in use.");
        }

        // Finds the store where the product will be added.
        Store store = getStore(storeId);

        // Creates and saves the new product in the database.
        Product product = productRepository.save(
                new Product(name.trim(), sku.trim().toUpperCase(), price));

        // Creates an inventory record connecting the product
        // to the selected store and its starting quantity.
        inventoryRepository.save(new Inventory(product, store, quantity));
    }

    // Updates the name, SKU, and price of an existing product.
    @Transactional
    public void updateProduct(Long productId, String name, String sku, BigDecimal price) {

        // Makes sure the product name and SKU are provided.
        if (name == null || name.isBlank() || sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Product name and SKU are required.");
        }

        // Makes sure the new price is greater than zero.
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Enter a price greater than zero.");
        }

        // Searches for the product using its ID.
        // If it does not exist, an error is thrown.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product was not found."));

        // Checks whether the new SKU is already being used.
        var existingSku = productRepository.findBySkuIgnoreCase(sku.trim());

        // Allows the product to keep its own SKU,
        // but prevents it from using another product's SKU.
        if (existingSku.isPresent()
                && !existingSku.get().getProductId().equals(productId)) {
            throw new IllegalArgumentException("That SKU is already in use.");
        }

        // Updates the product information.
        product.setName(name.trim());
        product.setSku(sku.trim().toUpperCase());
        product.setPrice(price);

        // Saves the updated product in the database.
        productRepository.save(product);
    }

    // Changes the quantity of an existing inventory record.
    @Transactional
    public void updateQuantity(Long inventoryId, int quantity) {

        // Prevents employees from entering a negative quantity.
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        // Finds the inventory record using its ID.
        Inventory item = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item was not found."));

        // Updates the quantity and saves the change.
        item.setQuantity(quantity);
        inventoryRepository.save(item);
    }

    // Removes a product from a store's inventory.
    @Transactional
    public void removeProduct(Long inventoryId) {

        // Finds the inventory record that needs to be removed.
        Inventory item = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item was not found."));

        // Deletes the inventory record from the database.
        inventoryRepository.delete(item);
    }

    // Records a sale and subtracts the sold quantity from inventory.
    @Transactional
    public void recordSale(Long inventoryId, int quantitySold) {

        // Makes sure the sale quantity is greater than zero.
        if (quantitySold <= 0) {
            throw new IllegalArgumentException("Sale quantity must be greater than zero.");
        }

        // Finds the inventory record for the product being sold.
        Inventory item = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item was not found."));

        // Checks whether the store has enough stock to complete the sale.
        if (item.getQuantity() < quantitySold) {
            throw new IllegalArgumentException("There is not enough stock to complete this sale.");
        }

        // Subtracts the sold quantity from the available inventory.
        item.setQuantity(item.getQuantity() - quantitySold);
        inventoryRepository.save(item);

        // Calculates the sale total by multiplying the product price
        // by the number of items sold.
        BigDecimal total = item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(quantitySold));

        // Creates and saves a new sale record with the store,
        // current date and time, and total amount.
        saleRepository.save(new Sale(item.getStore(), LocalDateTime.now(), total));
    }

    // Creates a new shipment for a store and product.
    @Transactional
    public void addShipment(Long storeId, Long productId, int quantity,
                            java.time.LocalDate arrivalDate) {

        // Makes sure the shipment contains at least one item.
        if (quantity <= 0) {
            throw new IllegalArgumentException("Shipment quantity must be greater than zero.");
        }

        // Finds the store receiving the shipment.
        Store store = getStore(storeId);

        // Finds the product included in the shipment.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product was not found."));

        // Creates and saves the shipment with an Expected status.
        shipmentRepository.save(new Shipment(
                store, product, quantity, arrivalDate, "Expected"));
    }

    // Marks a shipment as received and adds its quantity to inventory.
    @Transactional
    public void receiveShipment(Long shipmentId) {

        // Finds the shipment using its ID.
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Shipment was not found."));

        // Looks for an existing inventory record for this product
        // at the store receiving the shipment.
        Inventory item = inventoryRepository
                .findByProductProductIdAndStoreStoreId(
                        shipment.getProduct().getProductId(),
                        shipment.getStore().getStoreId())

                // If no inventory record exists, create one
                // with an initial quantity of zero.
                .orElseGet(() -> new Inventory(
                        shipment.getProduct(), shipment.getStore(), 0));

        // Adds the shipment quantity to the store's existing stock.
        item.setQuantity(item.getQuantity() + shipment.getQuantity());
        inventoryRepository.save(item);

        // Changes the shipment status to Received.
        shipment.setStatus("Received");
        shipmentRepository.save(shipment);
    }

    // Finds a store using its ID.
    // This method is private because it is only used inside this service.
    private Store getStore(Long storeId) {

        // Returns the store if it exists.
        // Otherwise, an error is thrown.
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store was not found."));
    }
}
