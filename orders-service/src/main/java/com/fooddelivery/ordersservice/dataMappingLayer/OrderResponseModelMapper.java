package com.fooddelivery.ordersservice.dataMappingLayer;

import com.fooddelivery.ordersservice.datalayer.Order;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResponseModelMapper {

    @Mapping(expression = "java(order.getOrderIdentifier().getOrderId())", target = "orderId")
    @Mapping(expression = "java(order.getClientIdentifier().getClientId())", target = "clientId")
    @Mapping(expression = "java(order.getRestaurantIdentifier().getRestaurantId())", target = "restaurantId")
    @Mapping(expression = "java(order.getMenuIdentifier().getMenuId())", target = "menuId")
    @Mapping(expression = "java(order.getDeliveryDriverIdentifier().getDeliveryDriverId())", target = "deliveryDriverId")
    OrderResponseModel entityToResponseModel(Order order);

    List<OrderResponseModel> entityToResponseModelList(List<Order> orders);
}
