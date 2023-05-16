package com.fooddelivery.apigateway.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
public class OrderResponseModel extends RepresentationModel<OrderResponseModel> {
     String orderId;
     String clientId;
     String restaurantId;
     String menuId;
     String deliveryDriverId;
     String driverFirstName;
     String driverLastName;
     String clientUsername;
     String clientEmail;
     List<Items> items;
     String restaurantName;
     String typeOfMenu;
     OrderStatus orderStatus;
     Double finalPrice;
     String estimatedDeliveryTime;
     String orderDate;
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
