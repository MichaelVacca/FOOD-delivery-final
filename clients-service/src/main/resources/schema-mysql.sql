USE `clients-db`;


create table if not exists clients (
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       client_id VARCHAR(36),
    user_name VARCHAR(50)UNIQUE,
    password VARCHAR(50),
    age VARCHAR(3),
    email_address VARCHAR(50),
    phone_number VARCHAR (15),
    country_name VARCHAR (50),
    street_name VARCHAR(50),
    city_name VARCHAR(50),
    province_name VARCHAR (50),
    postal_code VARCHAR (9)
    );
