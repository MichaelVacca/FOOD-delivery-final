package com.fooddelivery.ordersservice.datalayer;

import com.fooddelivery.ordersservice.datalayer.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

