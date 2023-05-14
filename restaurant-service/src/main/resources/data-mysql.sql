insert into restaurants (restaurant_id,menu_id,restaurant_name,  country_name, street_name, city_name,province_name, postal_code) values ('055b4a20-d29d-46ce-bb46-2c15b8ed6526', '0','Savory Street', 'Canada', 'Brown St', 'Château-Richer', 'Québec', 'B3H-7U5');
insert into restaurants (restaurant_id, menu_id,restaurant_name,  country_name, street_name, city_name,province_name, postal_code) values ('63b0795b-b7b7-46a8-b5bf-3be75c6289ba','0','Umami Bites', 'Canada', 'Darwin', 'Chambly', 'Québec', 'A9R-7L1');
insert into restaurants (restaurant_id, menu_id,restaurant_name,  country_name, street_name, city_name,province_name, postal_code) values ('c63967c3-b7f0-4376-8157-bb731ffee3c3','0','Spice Haven', 'Canada', 'Spohn', 'Sainte-Agathe-des-Monts', 'Québec', 'P3A-8U3');
insert into restaurants (restaurant_id, menu_id,restaurant_name,  country_name, street_name, city_name,province_name, postal_code) values ('a04ec2db-ab1f-4b23-b1ad-7826a60b6c32','0','Rustic Plate', 'Canada', 'Village Green', 'Les Cèdres', 'Québec', 'T5M-4S1');
insert into restaurants (restaurant_id, menu_id, restaurant_name,  country_name, street_name, city_name,province_name, postal_code) values ('c85eab4f-909e-4d0c-8ef3-4a0a0627b120','0','Coastal Catch', 'Canada', 'Shoshone', 'Saint-Tite', 'Québec', 'I6F-DN8');
insert into menus (restaurant_Id,menu_id,type_of_menu)values ('055b4a20-d29d-46ce-bb46-2c15b8ed6526', '8fb3d5f0-2ceb-4921-a978-736bb4d278b7', 'Appetizers');
insert into menus (restaurant_Id,menu_id,type_of_menu) values ('055b4a20-d29d-46ce-bb46-2c15b8ed6526','c4341840-87b8-4c2c-865f-c58b933e1994', 'Entrees');
insert into menus (restaurant_Id,menu_id,type_of_menu) values ('c85eab4f-909e-4d0c-8ef3-4a0a0627b120','13f2808d-1140-4ee1-b56a-b96c76d79226', 'Salads');
insert into menus (restaurant_Id,menu_id,type_of_menu) values ('a04ec2db-ab1f-4b23-b1ad-7826a60b6c32','2894adc8-f8ab-4436-a52e-942b65c99db1', 'Pastas');
insert into menus (restaurant_Id,menu_id,type_of_menu)values ('c63967c3-b7f0-4376-8157-bb731ffee3c3', 'ba213745-df23-425a-b9cd-3c50b311c019', 'Pizzas');

INSERT INTO items (menu_id, item_name, item_desc, item_cost) VALUES (1, 'Cheeseburger', 'A delicious burger with cheese', 9.99);
INSERT INTO items (menu_id, item_name, item_desc, item_cost) VALUES (2, 'Fries', 'Crispy golden fries', 3.99);
INSERT INTO items (menu_id, item_name, item_desc, item_cost) VALUES (3, 'Soft Drink', 'Refreshing soda', 1.99);





