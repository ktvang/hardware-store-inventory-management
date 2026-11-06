
package com.hardwarestore.controller;

// Imports the repositories used to retrieve database information.
import com.hardwarestore.repository.*;

// Imports the service that handles inventory operations and business logic.
import com.hardwarestore.service.InventoryService;

// Imports Spring MVC controller functionality.
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Allows the controller to send success and error messages
// after redirecting to another page.
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

// Marks this class as a controller.
// All routes in this class start with /employee.
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    // Repositories and service used by the employee controller.
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ShipmentRepository shipmentRepository;
    private final InventoryService inventoryService;

    // Constructor that receives the required repositories and service.
    // Spring automatically provides these objects through dependency injection.
    public EmployeeController(StoreRepository storeRepository,
                              ProductRepository productRepository,
                              InventoryRepository inventoryRepository,
                              ShipmentRepository shipmentRepository,
                              InventoryService inventoryService) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.shipmentRepository = shipmentRepository;
        this.inventoryService = inventoryService;
    }

    // Displays the employee dashboard.
    @GetMapping
    public String dashboard(@RequestParam(defaultValue = "1") Long storeId,
                           @RequestParam(defaultValue = "") String search,
                           Model model) {

        // Sends all store locations and products to the employee webpage.
        model.addAttribute("stores", storeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        // Sends the selected store and search term to the webpage.
        model.addAttribute("selectedStoreId", storeId);
        model.addAttribute("search", search);

        // If the search field is empty, retrieve all inventory
        // for the selected store.
        // Otherwise, search by product name or SKU.
        var items = search.isBlank()
                ? inventoryRepository.findByStoreStoreId(storeId)
                : inventoryRepository.searchStoreInventory(storeId, search);

        // Sends the inventory results to the employee webpage.
        model.addAttribute("inventory", items);

        // Retrieves shipments for the selected store,
        // ordered by their expected arrival dates.
        model.addAttribute("shipments",
                shipmentRepository.findByStoreStoreIdOrderByArrivalDateAsc(storeId));

        // Counts inventory records that have a quantity greater than zero.
        model.addAttribute("inStock", items.stream().filter(i -> i.getQuantity() > 0).count());

        // Counts inventory records with a quantity of five or less.
        model.addAttribute("lowStock", items.stream().filter(i -> i.getQuantity() <= 5).count());

        // Opens the employee.html webpage.
        return "employee";
    }

    // Handles requests to add a new product.
    @PostMapping("/products")
    public String addProduct(@RequestParam String name,
                             @RequestParam String sku,
                             @RequestParam BigDecimal price,
                             @RequestParam int quantity,
                             @RequestParam Long storeId,
                             RedirectAttributes redirect) {

        try {
            // Calls the service to validate and add the product
            // and its inventory record.
            inventoryService.addProduct(name, sku, price, quantity, storeId);

            // Sends a success message back to the employee webpage.
            redirect.addFlashAttribute("success", "Product added successfully.");

        } catch (RuntimeException ex) {
            // If something goes wrong, send the error message to the webpage.
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        // Returns the employee to the dashboard for the selected store.
        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to update an existing product.
    @PostMapping("/products/{productId}/update")
    public String updateProduct(@PathVariable Long productId,
                                @RequestParam String name,
                                @RequestParam String sku,
                                @RequestParam BigDecimal price,
                                @RequestParam Long storeId,
                                RedirectAttributes redirect) {
        try {
            // Calls the service to update the product's name, SKU, and price.
            inventoryService.updateProduct(productId, name, sku, price);

            // Sends a success message after the update.
            redirect.addFlashAttribute("success", "Product details updated.");

        } catch (RuntimeException ex) {
            // Sends an error message if the update fails.
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to change the quantity of an inventory item.
    @PostMapping("/inventory/{inventoryId}/quantity")
    public String updateQuantity(@PathVariable Long inventoryId,
                                 @RequestParam int quantity,
                                 @RequestParam Long storeId,
                                 RedirectAttributes redirect) {
        try {
            // Calls the service to update the inventory quantity.
            inventoryService.updateQuantity(inventoryId, quantity);

            redirect.addFlashAttribute("success", "Inventory quantity updated.");

        } catch (RuntimeException ex) {
            // Displays an error if the quantity cannot be updated.
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to remove a product from a store's inventory.
    @PostMapping("/inventory/{inventoryId}/remove")
    public String removeProduct(@PathVariable Long inventoryId,
                                @RequestParam Long storeId,
                                RedirectAttributes redirect) {
        try {
            // Calls the service to delete the inventory record.
            inventoryService.removeProduct(inventoryId);

            redirect.addFlashAttribute("success", "Product removed from this store's inventory.");

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to record a customer sale.
    @PostMapping("/sales")
    public String recordSale(@RequestParam Long inventoryId,
                             @RequestParam int quantitySold,
                             @RequestParam Long storeId,
                             RedirectAttributes redirect) {
        try {
            // Calls the service to record the sale
            // and subtract the sold quantity from inventory.
            inventoryService.recordSale(inventoryId, quantitySold);

            redirect.addFlashAttribute("success", "Sale recorded and inventory reduced.");

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to add an incoming shipment.
    @PostMapping("/shipments")
    public String addShipment(@RequestParam Long storeId,
                              @RequestParam Long productId,
                              @RequestParam int quantity,
                              @RequestParam LocalDate arrivalDate,
                              RedirectAttributes redirect) {
        try {
            // Calls the service to create a shipment
            // with the selected store, product, quantity, and arrival date.
            inventoryService.addShipment(storeId, productId, quantity, arrivalDate);

            redirect.addFlashAttribute("success", "Incoming shipment added.");

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }

    // Handles requests to mark an incoming shipment as received.
    @PostMapping("/shipments/{shipmentId}/receive")
    public String receiveShipment(@PathVariable Long shipmentId,
                                  @RequestParam Long storeId,
                                  RedirectAttributes redirect) {
        try {
            // Calls the service to mark the shipment as received
            // and add its quantity to the store's inventory.
            inventoryService.receiveShipment(shipmentId);

            redirect.addFlashAttribute("success", "Shipment received and stock updated.");

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/employee?storeId=" + storeId;
    }
}
