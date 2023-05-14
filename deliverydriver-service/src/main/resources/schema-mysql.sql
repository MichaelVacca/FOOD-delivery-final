USE `deliverydriver-db`;


create table if not exists drivers (
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       delivery_driver_id VARCHAR(36),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth VARCHAR(20),
    description VARCHAR(100),
    employee_since VARCHAR (30),
    country_name VARCHAR (50),
    street_name VARCHAR(50),
    city_name VARCHAR(50),
    province_name VARCHAR (50),
    postal_code VARCHAR (9)
    );