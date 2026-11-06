
package com.hardwarestore.controller;

// Imports the repositories used to retrieve and save database information.
import com.hardwarestore.repository.*;

// Imports Spring MVC controller functionality.
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Allows the controller to display success and error messages after redirects.
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Imports the Store model used when creating a new store location.
import com.hardwarestore.model.Store;

// Marks this class as a Spring MVC controller.
@Controller

// Sets the main URL for all routes in this controller to /admin.
@RequestMapping("/admin")
public class AdminController {

    // Repositories used to access different parts of the database.
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;
    private final ShipmentRepository shipmentRepository;

    // Constructor that receives the repositories through dependency injection.
    public AdminController(StoreRepository storeRepository,
                           ProductRepository productRepository,
                           InventoryRepository inventoryRepository,
                           SaleRepository saleRepository,
                           ShipmentRepository shipmentRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.saleRepository = saleRepository;
        this.shipmentRepository = shipmentRepository;
    }

    // Handles requests to add a new store location.
    @PostMapping("/stores")
    public String addStore(@RequestParam String city,
                           @RequestParam String state,
                           RedirectAttributes redirect) {

        // Checks that the city and state are provided.
        // Also makes sure the state contains exactly two letters.
        if (city == null || city.isBlank() || state == null || !state.matches("[A-Za-z]{2}")) {

            // Displays an error message if the information is invalid.
            redirect.addFlashAttribute("error", "Enter a city and a valid two-letter state code.");

            // Returns to the admin dashboard without saving the store.
            return "redirect:/admin";
        }

        // Creates a new Store object and saves it in the database.
        // The city is trimmed to remove extra spaces.
        // The state code is converted to uppercase.
        storeRepository.save(new Store(city.trim(), state.trim().toUpperCase()));

        // Displays a success message after adding the store.
        redirect.addFlashAttribute("success", "Store location added.");

        // Returns to the admin dashboard.
        return "redirect:/admin";
    }

    // Handles requests to display the administrator dashboard.
    @GetMapping
    public String dashboard(Model model) {

        // Retrieves all store locations and sends them to the admin webpage.
        model.addAttribute("stores", storeRepository.findAll());

        // Counts the total number of products in the database.
        model.addAttribute("productCount", productRepository.count());

        // Counts the total number of inventory records.
        model.addAttribute("inventoryCount", inventoryRepository.count());

        // Retrieves the 10 most recent sales records.
        model.addAttribute("sales", saleRepository.findTop10ByOrderByDateOfSaleDesc());

        // Retrieves shipments that are still marked as Expected.
        // Orders them by arrival date, with the earliest first.
        model.addAttribute("shipments",
                shipmentRepository.findByStatusIgnoreCaseOrderByArrivalDateAsc("Expected"));

        // Opens the admin.html webpage.
        return "admin";
    }
}
