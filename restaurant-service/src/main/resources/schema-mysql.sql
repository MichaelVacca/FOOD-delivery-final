USE `restaurant-db`;

create table if not exists restaurants(
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    restaurant_id VARCHAR (36),
    menu_id VARCHAR (36),
    restaurant_name VARCHAR(36),
    country_name VARCHAR (50),
    street_name VARCHAR(50),
    city_name VARCHAR(50),
    province_name VARCHAR (50),
    postal_code VARCHAR (9)

);

create table if not exists items(
menu_id INTEGER UNIQUE,
item_name VARCHAR(30),
    item_desc VARCHAR(50),
    item_cost DECIMAL(19,2)

);

create table if not exists menus(
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
menu_id VARCHAR (36)UNIQUE,
restaurant_id VARCHAR (36),
    type_of_menu VARCHAR (30)

);
