HardwareHub — Hardware Store Inventory Management System

This is a Java 21 / Spring Boot web application based on the team's Project Elaboration.

What Is Included

Public customer portal: Search products, select a store, view stock availability, and see expected arrival dates.

Employee workspace: Add products, edit product details, search inventory, update quantities, remove a product from a store's inventory, record a sale, and manage incoming shipments.

Administrator dashboard: View store locations, add store locations, catalog/inventory totals, expected shipments, and recent sales.

Three store locations are inserted as sample data: Coon Rapids, Minneapolis, and Blaine.

MySQL database integration using Spring Data JPA.

Role separation for public customers, employees, and administrators.

Requirements

Java 21

Visual Studio Code (VS Code)

Extension Pack for Java (VS Code extension)

Spring Boot Extension Pack (VS Code extension)

Maven 3.9+ (or use the Maven wrapper if included)

MySQL Server 8+

MySQL Workbench (recommended for managing the database)

Internet connection on first build so Maven can download dependencies.

Configure MySQL

Install MySQL Server and MySQL Workbench.

Open MySQL Workbench and connect to your local MySQL Server.

Create the database using:

CREATE DATABASE hardware_store_db;
USE hardware_store_db;

Open src/main/resources/application.properties in VS Code.

Replace YOUR_MYSQL_PASSWORD with your actual MySQL root password.

The JDBC URL creates hardware_store_db if it does not exist.

The Java entity classes create/update the tables when the application starts (spring.jpa.hibernate.ddl-auto=update).

The database/schema.sql file documents the relational tables. Do not run the SQL file after the app has already created tables unless you know how to handle existing tables.

Run in Visual Studio Code

Extract the HardwareStore_Java_WebApp ZIP folder.

Open Visual Studio Code.

Select File → Open Folder.

Select the HardwareStore_Java_WebApp project folder containing pom.xml.

Install the following VS Code extensions if they are not already installed:

Extension Pack for Java.

Spring Boot Extension Pack.

Wait for VS Code and Maven to finish loading the project and downloading dependencies.

Open src/main/resources/application.properties.

Set your MySQL password.

Make sure your MySQL Server is running.

Open src/main/java/com/hardwarestore/HardwareStoreApplication.java.

Click Run above the main method.

Alternatively, open the VS Code terminal using Ctrl + backtick and run:

mvn spring-boot:run

Wait for Spring Boot to start successfully. The application runs on port 8080.

Open your browser and visit:

http://localhost:8080

Important: Keep the VS Code terminal running while using the website. Stopping the application will stop the website.

Demo Accounts

Employee: Username employee, password employee123

Administrator: Username admin, password admin123

These are demonstration-only in-memory accounts. Replace them with database-backed accounts and stronger password/security management before any real deployment.

Main URLs

Customer portal: http://localhost:8080/

Employee login: http://localhost:8080/login

Employee workspace: http://localhost:8080/employee

Administrator dashboard: http://localhost:8080/admin

How This Matches the Paper

Product and inventory management: Add products, search inventory, update stock, and remove a store's inventory entry.

Sales processing: Recording a sale reduces stock and stores a sales total.

Shipment and arrival tracking: Add expected shipments, show arrival dates, and receive a shipment to add its quantity to stock.

Customer portal: Public product search, store selection, availability, and expected arrival dates.

Admin controls: Separate admin route and role, store overview, and system-level reports.

Multi-store design: Inventory is associated with both a product and a store, so the same product can have different stock levels at different locations.

Prototype Limitations

The employee and administrator accounts are hardcoded for demonstration, not stored in MySQL.

The administrator page is a read-only overview. Full user/role configuration and database maintenance screens are not implemented.

Sales are recorded as a store, timestamp, and total. The paper's initial description does not define detailed sale line items, tax, payment, or customer records.

The customer portal displays stock information but does not place online orders; the paper says ordering is a possible future capability.

The app is a class prototype and has not been security-audited or load-tested for real multi-store production use.
