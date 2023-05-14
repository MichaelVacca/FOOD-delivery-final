package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.presentationLayer.OrderRequestModel;
import com.fooddelivery.apigateway.presentationLayer.OrderResponseModel;

public interface OrderService {
    OrderResponseModel[] getAllOrdersAggregate();
    OrderResponseModel getOrderById(String orderId);
    OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId);
    void updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel);
    void deleteOrder(String orderId);
}
