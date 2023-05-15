package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.presentationLayer.OrderRequestModel;
import com.fooddelivery.apigateway.presentationLayer.OrderResponseModel;

import java.util.List;

public interface OrderService {
    OrderResponseModel[] getAllOrdersAggregate();
    OrderResponseModel getOrderById(String orderId);

    OrderResponseModel getOrderByOrderIdAndByClientId(String clientId, String orderId);

    OrderResponseModel[] getAllOrdersAggregateByClientId(String clientId);
    OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId);
    void updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel);
    void deleteOrder(String orderId);

    void deleteOrderByIdAndClientId(String clientId, String orderId);
}
