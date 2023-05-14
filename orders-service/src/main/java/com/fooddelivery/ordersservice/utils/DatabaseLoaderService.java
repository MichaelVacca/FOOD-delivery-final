package com.fooddelivery.ordersservice.utils;

import com.fooddelivery.ordersservice.datalayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoaderService implements CommandLineRunner {

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public void run(String... args) throws Exception {

        var orderIdentifier1 = new OrderIdentifier();
        var restaurantIdentifier1 = new RestaurantIdentifier("ef0558f4-f10f-11ed-a05b-0242ac120003");
        var menuIdentifier1 = new MenuIdentifier("d02a4f52-f10f-11ed-a05b-0242ac120003");
        var clientIdentifier1 = new ClientIdentifier("fdeb7574-f10f-11ed-a05b-0242ac120003");
        var deliveryDriverIdentifier1 = new DeliveryDriverIdentifier("0b8f49da-f110-11ed-a05b-0242ac120003");

        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        var order1 = Order.builder()
                .orderIdentifier(orderIdentifier1)
                .restaurantIdentifier(restaurantIdentifier1)
                .menuIdentifier(menuIdentifier1)
                .clientIdentifier(clientIdentifier1)
                .deliveryDriverIdentifier(deliveryDriverIdentifier1)
                .driverFirstName("John")
                .driverLastName("Doe")
                .clientUsername("johndoeUser")
                .clientEmail("johndoe@email")
                .finalPrice(10.0)
                .orderStatus(OrderStatus.PROCCESSING_ORDER)
                .items(items)
                .restaurantName("Burger King")
                .typeOfMenu("Hamburger")
                .estimatedDeliveryTime("5 min")
                .orderDate(LocalDate.of(2020,12,15))
                .build();

        var orderIdentifier2 = new OrderIdentifier();
        var restaurantIdentifier2 = new RestaurantIdentifier("a30558f4-f10f-11ed-a05b-0242ac120003");
        var menuIdentifier2 = new MenuIdentifier("t02a4f52-f10f-11ed-a05b-0242ac120003");
        var clientIdentifier2 = new ClientIdentifier("p6eb7574-f10f-11ed-a05b-0242ac120003");
        var deliveryDriverIdentifier2 = new DeliveryDriverIdentifier("4m8f49da-f110-11ed-a05b-0242ac120003");

        Items three = new Items("Salad","Grilled Salad",9.99);
        Items four = new Items(" Crepes","Baked",5.99);
        List<Items> items2 = new ArrayList<>(Arrays.asList(three,four));

        var order2 = Order.builder()
                .orderIdentifier(orderIdentifier2)
                .restaurantIdentifier(restaurantIdentifier2)
                .menuIdentifier(menuIdentifier2)
                .clientIdentifier(clientIdentifier2)
                .deliveryDriverIdentifier(deliveryDriverIdentifier2)
                .driverFirstName("Jane")
                .driverLastName("Doe")
                .clientUsername("janedoeUser")
                .clientEmail("janedoe@email")
                .finalPrice(10.0)
                .orderStatus(OrderStatus.MAKING_ORDER)
                .items(items2)
                .restaurantName("KFC")
                .typeOfMenu("Breakfast")
                .estimatedDeliveryTime("20 minutes")
                .orderDate(LocalDate.of(2017,10,10))
                .build();
        ordersRepository.insert(order1);
        ordersRepository.insert(order2);


    }
}
