
package com.hardwarestore.controller;

// Imports the repositories used to retrieve information from MySQL.
import com.hardwarestore.repository.*;

// Marks this class as a Spring MVC controller.
import org.springframework.stereotype.Controller;

// Allows Java to send information to the HTML pages.
import org.springframework.ui.Model;

// Handles HTTP GET requests.
import org.springframework.web.bind.annotation.GetMapping;

// Allows the application to receive values from URL parameters.
import org.springframework.web.bind.annotation.RequestParam;

// Controller responsible for displaying the customer portal
// and login page.
@Controller
public class HomeController {

    // Repositories used to retrieve store, inventory, and shipment information.
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;
    private final ShipmentRepository shipmentRepository;

    // Constructor that receives the repositories from Spring.
    // This is dependency injection.
    public HomeController(StoreRepository storeRepository,
                          InventoryRepository inventoryRepository,
                          ShipmentRepository shipmentRepository) {
        this.storeRepository = storeRepository;
        this.inventoryRepository = inventoryRepository;
        this.shipmentRepository = shipmentRepository;
    }

    // Handles requests to the homepage at http://localhost:8080/.
    @GetMapping("/")
    public String customerPortal(

            // Gets the selected store ID from the URL.
            // If no store ID is provided, it defaults to 1.
            @RequestParam(defaultValue = "1") Long storeId,

            // Gets the search term entered by the customer.
            // If no search term is provided, it defaults to an empty string.
            @RequestParam(defaultValue = "") String search,

            // Model is used to send information from Java
            // to the customer.html page.
            Model model) {

        // Retrieves all store locations and sends them to the webpage.
        model.addAttribute("stores", storeRepository.findAll());

        // Sends the currently selected store ID to the webpage.
        model.addAttribute("selectedStoreId", storeId);

        // Sends the customer's search term to the webpage.
        model.addAttribute("search", search);

        // If the search field is empty, retrieve all inventory
        // for the selected store.
        // Otherwise, search inventory by product name or SKU.
        var inventory = search.isBlank()
                ? inventoryRepository.findByStoreStoreId(storeId)
                : inventoryRepository.searchStoreInventory(storeId, search);

        // Sends the inventory results to the customer.html page.
        model.addAttribute("inventory", inventory);

        // Retrieves shipments for the selected store,
        // ordered by their expected arrival dates.
        // Sends the shipment information to the webpage.
        model.addAttribute("shipments",
                shipmentRepository.findByStoreStoreIdOrderByArrivalDateAsc(storeId));

        // Opens the customer.html template.
        return "customer";
    }

    // Handles requests to the login page at /login.
    @GetMapping("/login")
    public String login() {

        // Opens the login.html template.
        return "login";
    }
}
