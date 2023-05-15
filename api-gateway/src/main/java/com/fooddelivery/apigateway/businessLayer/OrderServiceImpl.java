package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.domainClientLayer.OrderServiceClient;
import com.fooddelivery.apigateway.presentationLayer.OrderRequestModel;
import com.fooddelivery.apigateway.presentationLayer.OrderResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderServiceClient orderServiceClient;

    public OrderServiceImpl(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    @Override
    public OrderResponseModel[] getAllOrdersAggregate() {
        return orderServiceClient.getAllOrdersAggregate();
    }

    @Override
    public OrderResponseModel getOrderById(String orderId) {
        return orderServiceClient.getOrderById(orderId);
    }

    @Override
    public OrderResponseModel getOrderByOrderIdAndByClientId(String clientId, String orderId) {
        return orderServiceClient.getOrderByOrderIdAndByClientId(clientId, orderId);
    }

    @Override
    public OrderResponseModel[] getAllOrdersAggregateByClientId(String clientId) {
        return orderServiceClient.getAllOrdersAggregateByClientId(clientId);
    }

    @Override
    public OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId) {
        return orderServiceClient.processClientOrders(orderRequestModel, clientId);
    }


    @Override
    public void updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel) {
            orderServiceClient.updateClientOrder(clientId, orderId, orderRequestModel);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderServiceClient.deleteOrder(orderId);
    }

    @Override
    public void deleteOrderByIdAndClientId(String clientId, String orderId) {
        orderServiceClient.deleteClientOrder(clientId, orderId);
    }
}
