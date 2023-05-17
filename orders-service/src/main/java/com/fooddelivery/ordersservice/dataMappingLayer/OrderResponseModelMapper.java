package com.fooddelivery.ordersservice.dataMappingLayer;

import com.fooddelivery.ordersservice.datalayer.Order;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseModel orderResponseModel, Order order) {

        URI baseUri = URI.create("http://localhost:8080");

        Link selfLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "clients", orderResponseModel.getClientId(),"orders",orderResponseModel.getOrderId())
                        .toUriString(),
                "self");

        Link clientLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "clients", orderResponseModel.getClientId(),"orders")
                        .toUriString(),
                "allOrders");

        orderResponseModel.add(selfLink);
        orderResponseModel.add(clientLink);
    }
}
