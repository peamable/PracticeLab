CREATE TABLE IF NOT EXISTS records (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       customer_number INT NOT NULL,
                                       customer_name VARCHAR(255),
    customer_deposit DECIMAL(10, 2),
    number_of_years INT,
    savings_type VARCHAR(255)
    );
INSERT INTO records (customer_number, customer_name, customer_deposit, number_of_years, savings_type)
VALUES (115, 'Jasper Diaz', 15000.00, 5, 'Savings-Deluxe');

INSERT INTO records (customer_number, customer_name, customer_deposit, number_of_years, savings_type)
VALUES (112, 'Zanip Mendez', 5000.00, 2, 'Savings-Deluxe');

INSERT INTO records (customer_number, customer_name, customer_deposit, number_of_years, savings_type)
VALUES (113, 'Geronima Esper', 6000.00, 5, 'Savings-Regular');