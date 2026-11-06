# 🏗️ HardwareHub
### Hardware Store Inventory Management System

A Java 21 and Spring Boot web application designed to help hardware stores manage products, inventory, sales, shipments, and multiple store locations.

HardwareHub was developed as part of our team's Software Design Project.

---

## 📌 Project Overview

HardwareHub provides a centralized inventory management system that allows customers, employees, and administrators to interact with hardware store information through a web-based application.

### ✨ Main Features

**🛒 Customer Portal**
- Search for products by name or SKU.
- Select different store locations.
- View product availability and stock quantities.
- Check expected shipment arrival dates.

**👷 Employee Workspace**
- Add new products and edit product details.
- Search store inventory.
- Update product quantities.
- Remove products from a store's inventory.
- Record customer sales and automatically reduce stock.
- Manage incoming shipments and update inventory when shipments arrive.

**🔐 Administrator Dashboard**
- View all store locations.
- Add new store locations.
- View product and inventory totals.
- Monitor expected shipments.
- Review the 10 most recent sales.

### 🏬 Sample Store Locations

The application automatically inserts three sample locations when the database is empty:

- Coon Rapids, MN
- Minneapolis, MN
- Blaine, MN

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Main programming language |
| Spring Boot | Application framework |
| Spring MVC | Web application controllers |
| Spring Data JPA | Database operations |
| Spring Security | Login and role-based access |
| Thymeleaf | HTML page rendering |
| MySQL 8+ | Database management |
| Maven 3.9+ | Dependency and project management |
| HTML / CSS | Website structure and styling |
| Visual Studio Code | Development environment |

---

## 📋 Requirements

Before running the application, make sure you have installed:

1. Java Development Kit (JDK) 21.
2. Visual Studio Code (VS Code).
3. Extension Pack for Java.
4. Spring Boot Extension Pack.
5. Maven 3.9+ (or the Maven wrapper, if included).
6. MySQL Server 8+.
7. MySQL Workbench (recommended).

An internet connection is required during the first build so Maven can download project dependencies.

---

## ⚙️ Installation and Setup

### 1. Download the Project

Clone or download this repository from GitHub.

If downloading as a ZIP file, extract the project folder.

### 2. Open the Project in VS Code

1. Open Visual Studio Code.
2. Select **File → Open Folder**.
3. Select the `HardwareStore_Java_WebApp` project folder.
4. Make sure the folder containing `pom.xml` is open.
5. Install the Java and Spring Boot extensions if needed.
6. Wait for Maven to finish loading the project and downloading dependencies.

### 3. Configure MySQL

1. Install and start MySQL Server.
2. Open MySQL Workbench.
3. Connect to your local MySQL Server.
4. Create the application database:

```sql
CREATE DATABASE hardware_store_db;
USE hardware_store_db;
```

### 4. Configure Database Connection

Open:

`src/main/resources/application.properties`

Configure your MySQL username and password.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hardware_store_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password.

**Security reminder:** Do not commit your real database password to GitHub. For shared or public repositories, use environment variables or another secure configuration method.

The application uses:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This allows Hibernate to create and update database tables based on the Java entity classes.

The `database/schema.sql` file documents the relational database structure.

---

## ▶️ Running the Application

### Option 1: Run Through VS Code

1. Open the project in VS Code.
2. Navigate to:

`src/main/java/com/hardwarestore/HardwareStoreApplication.java`

3. Open the file.
4. Click **Run** above the `main()` method.
5. Wait for Spring Boot to finish starting.

### Option 2: Run Through the Terminal

Open the VS Code terminal:

**Terminal → New Terminal**

Run:

```bash
mvn spring-boot:run
```

Wait until Spring Boot starts successfully.

The application runs on port `8080`.

Open your browser and visit:

**http://localhost:8080**

> **Important:** Keep the VS Code terminal running while using the website. Press `Ctrl + C` in the terminal to stop the application.

---

## 🔑 Demo Login Accounts

The application includes two demonstration accounts.

| User Role | Username | Password |
|---|---|---|
| Employee | `employee` | `employee123` |
| Administrator | `admin` | `admin123` |

**Note:** These accounts are stored in memory and are intended for demonstration purposes only. They are not production-ready credentials.

---

## 🌐 Application URLs

| Page | URL |
|---|---|
| Customer Portal | [http://localhost:8080/](http://localhost:8080/) |
| Login Page | [http://localhost:8080/login](http://localhost:8080/login) |
| Employee Workspace | [http://localhost:8080/employee](http://localhost:8080/employee) |
| Administrator Dashboard | [http://localhost:8080/admin](http://localhost:8080/admin) |

---

## 🗄️ Database Structure

The application uses MySQL and Spring Data JPA to manage its database records.

Main database entities include:

- **Product:** Stores product names, SKUs, and prices.
- **Store:** Stores hardware store locations.
- **Inventory:** Tracks product quantities at individual stores.
- **Sale:** Stores sales totals, store information, and transaction dates.
- **Shipment:** Tracks incoming products, quantities, arrival dates, and shipment statuses.

The inventory system connects products with individual store locations. This allows the same product to have different stock quantities at different stores.

---

## 📖 How HardwareHub Supports the Project Requirements

| Project Requirement | HardwareHub Implementation |
|---|---|
| Product Management | Add and update product information. |
| Inventory Management | Search, update, and remove inventory records. |
| Sales Processing | Record sales and automatically reduce stock. |
| Shipment Tracking | Add shipments and record their arrival. |
| Customer Portal | Search products and check store availability. |
| Administrator Controls | Manage store locations and view system information. |
| Multi-Store Support | Track inventory separately for each store. |

---

## ⚠️ Prototype Limitations

HardwareHub is a class project prototype. Some features would need additional development before real-world deployment.

- Employee and administrator accounts are currently hardcoded in memory rather than stored in MySQL.
- The administrator dashboard provides an overview but does not include full user management, role configuration, or database maintenance screens.
- Sales records currently store the store, timestamp, and total. Detailed sale line items, taxes, payment information, and customer records are not implemented.
- The customer portal displays product availability but does not support online ordering.
- The application has not been security-audited or load-tested for production use.

---

## 🚀 Future Improvements

Potential improvements for future versions include:

- Database-backed employee and administrator accounts.
- Expanded administrator permissions and user management.
- Online customer ordering.
- Detailed sales reports and transaction history.
- Improved reporting across multiple store locations.
- Additional security and validation features.

---

## 👥 Project Information

**Project Name:** HardwareHub – Hardware Store Inventory Management System

**Course:** Software Design

**Development Environment:** Visual Studio Code

**Language:** Java 21

**Framework:** Spring Boot

**Database:** MySQL

**Repository:** [HardwareHub on GitHub](https://github.com/ktvang/hardware-store-inventory-management)

---

*Developed as part of a collaborative Software Design class project.*
