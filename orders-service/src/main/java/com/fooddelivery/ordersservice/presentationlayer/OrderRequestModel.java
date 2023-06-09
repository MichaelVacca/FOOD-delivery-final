package com.fooddelivery.ordersservice.presentationlayer;

import com.fooddelivery.ordersservice.datalayer.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@Value
public class OrderRequestModel {
     String restaurantId;
     String menuId;
     Double totalPrice;
     String deliveryDriverId;
     OrderStatus orderStatus;
      List<Items> items;
      String estimatedDeliveryTime;
     LocalDate orderDate;
}
/*
     private OrderIdentifier orderIdentifier;
     private RestaurantIdentifier restaurantIdentifier;
     private MenuIdentifier menuIdentifier;
     private ClientIdentifier clientIdentifier;
     private DeliveryDriverIdentifier deliveryDriverIdentifier;
     private String driverFirstName;
     private String driverLastName;
     private String clientUsername;
     private String clientEmail;
     private List<Items> items;
     private String restaurantName;
     private String typeOfMenu;
     private OrderStatus orderStatus;
     private Double finalPrice;
     private String estimatedDeliveryTime;
     private LocalDate orderDate;

}

     private String orderId;
    private String clientId;
    private String restaurantId;
    private String menuId;
    private String deliveryDriverId;
    private String driverFirstName;
    private String driverLastName;
    private String clientUsername;
    private String clientEmail;
    private List<Items> items;
    private String restaurantName;
    private String typeOfMenu;
    private OrderStatus orderStatus;
    private Double finalPrice;
    private String estimatedDeliveryTime;
    private LocalDate orderDate;*/
