package com.fooddelivery.apigateway.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Builder

public class OrderRequestModel extends RepresentationModel<OrderRequestModel> {
     String restaurantId;
     String menuId;
     Double totalPrice;
     String deliveryDriverId;
     OrderStatus orderStatus;
      List<Items> items;
      String estimatedDeliveryTime;
     String  orderDate;

     public OrderRequestModel() {

     }

     public OrderRequestModel(String restaurantId, String menuId, Double totalPrice, String deliveryDriverId, OrderStatus orderStatus, List<Items> items, String estimatedDeliveryTime, String orderDate) {
         this.restaurantId = restaurantId;
         this.menuId = menuId;
         this.totalPrice = totalPrice;
         this.deliveryDriverId = deliveryDriverId;
         this.orderStatus = orderStatus;
         this.items = items;
         this.estimatedDeliveryTime = estimatedDeliveryTime;
         this.orderDate = orderDate;
     }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public void setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
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
