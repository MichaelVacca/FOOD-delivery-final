package com.fooddelivery.ordersservice.datalayer;

import com.fooddelivery.ordersservice.datalayer.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    @Id
    private String id;

     public OrderIdentifier orderIdentifier;
    public RestaurantIdentifier restaurantIdentifier;
    public MenuIdentifier menuIdentifier;
    public ClientIdentifier clientIdentifier;
    public DeliveryDriverIdentifier deliveryDriverIdentifier;
    public String driverFirstName;
    public String driverLastName;
    public String clientUsername;
    public String clientEmail;
    public List<Items> items;
    public String restaurantName;
    public String typeOfMenu;
    public OrderStatus orderStatus;
    public Double finalPrice;
    public String estimatedDeliveryTime;
    public LocalDate orderDate;

    public Order(OrderIdentifier orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public Order(OrderIdentifier orderIdentifier, ClientIdentifier clientIdentifier) {
        this.orderIdentifier = orderIdentifier;
        this.clientIdentifier = clientIdentifier;
    }
    /*    public Order(OrderIdentifier orderIdentifier, RestaurantIdentifier restaurantIdentifier, MenuIdentifier menuIdentifier, ClientIdentifier clientIdentifier, DeliveryDriverIdentifier deliveryDriverIdentifier, String driverFirstName, String driverLastName, String clientUsername, String clientEmail, List<Items> items, String restaurantName, String typeOfMenu, OrderStatus orderStatus, Double finalPrice, String estimatedDeliveryTime) {
        this.orderIdentifier = orderIdentifier;
        this.restaurantIdentifier = restaurantIdentifier;
        this.menuIdentifier = menuIdentifier;
        this.clientIdentifier = clientIdentifier;
        this.deliveryDriverIdentifier = deliveryDriverIdentifier;
        this.driverFirstName = driverFirstName;
        this.driverLastName = driverLastName;
        this.clientUsername = clientUsername;
        this.clientEmail = clientEmail;
        this.items = items;
        this.restaurantName = restaurantName;
        this.typeOfMenu = typeOfMenu;
        this.orderStatus = orderStatus;
        this.finalPrice = finalPrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }*/


/*    public Order(OrderIdentifier orderIdentifier, RestaurantIdentifier restaurantIdentifier, MenuIdentifier menuIdentifier, ClientIdentifier clientIdentifier, DeliveryDriverIdentifier deliveryDriverIdentifier, String driverFirstName, String driverLastName, String clientUsername, String clientEmail) {
        this.orderIdentifier = orderIdentifier;
        this.restaurantIdentifier = restaurantIdentifier;
        this.menuIdentifier = menuIdentifier;
        this.clientIdentifier = clientIdentifier;
        this.deliveryDriverIdentifier = deliveryDriverIdentifier;
        this.driverFirstName = driverFirstName;
        this.driverLastName = driverLastName;
        this.clientUsername = clientUsername;
        this.clientEmail = clientEmail;
    }*/
}


/*    private String orderId;
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


/*    Order order = Order.builder()
            .orderIdentifier(new OrderIdentifier())
            .restaurantIdentifier(new RestaurantIdentifier(menuResponseModel.getRestaurantId()))
            .menuIdentifier(new MenuIdentifier(menuResponseModel.getMenuId()))
            .clientIdentifier(new ClientIdentifier(clientResponseModel.getClientId()))
            .deliveryDriverIdentifier(new DeliveryDriverIdentifier(deliveryDriverResponseModel.getDeliveryDriverId()))
            .driverFirstName(deliveryDriverResponseModel.getFirstName())
            .driverLastName(deliveryDriverResponseModel.getLastName())
            .clientUsername(clientResponseModel.getUserName())
            .clientEmail(clientResponseModel.getEmailAddress())
            .totalPrice(orderRequestModel.getTotalPrice())
            .orderStatus(orderRequestModel.getOrderStatus())
            .items(orderRequestModel.getItems())
            .orderDate(orderRequestModel.getOrderDate())
            .build();*/

