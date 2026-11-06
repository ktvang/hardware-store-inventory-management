package com.hardwarestore;

// Imports Spring Boot classes needed to start the application
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marks this as the main Spring Boot application class.
// Spring Boot uses this annotation to configure the application
// and scan for controllers, services, repositories, and other components.
@SpringBootApplication
public class HardwareStoreApplication {

    // Main method: This is where the application starts running.
    public static void main(String[] args) {

        // Starts the Spring Boot application.
        // This also starts the embedded Tomcat web server.
        SpringApplication.run(HardwareStoreApplication.class, args);
    }
}