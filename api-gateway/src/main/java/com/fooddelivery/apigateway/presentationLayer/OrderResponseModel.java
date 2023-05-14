package com.fooddelivery.apigateway.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderResponseModel {
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
    private String orderDate;
}
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
