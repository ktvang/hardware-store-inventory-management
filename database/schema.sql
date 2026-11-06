CREATE DATABASE IF NOT EXISTS hardware_store_db;
USE hardware_store_db;

-- Spring Data JPA creates/updates the tables from the Java entity classes.
-- This file documents the planned relational structure from the project paper.

CREATE TABLE IF NOT EXISTS stores (
    store_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(12) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id)
        REFERENCES products(product_id),
    CONSTRAINT fk_inventory_store FOREIGN KEY (store_id)
        REFERENCES stores(store_id),
    CONSTRAINT uq_product_store UNIQUE (product_id, store_id)
);

CREATE TABLE IF NOT EXISTS sales (
    sale_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    date_of_sale DATETIME NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_sales_store FOREIGN KEY (store_id)
        REFERENCES stores(store_id)
);

CREATE TABLE IF NOT EXISTS shipments (
    shipment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    arrival_date DATE NOT NULL,
    status VARCHAR(40) NOT NULL,
    CONSTRAINT fk_shipments_store FOREIGN KEY (store_id)
        REFERENCES stores(store_id),
    CONSTRAINT fk_shipments_product FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);
