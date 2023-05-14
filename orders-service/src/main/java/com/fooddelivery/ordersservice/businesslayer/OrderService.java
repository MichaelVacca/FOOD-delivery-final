package com.fooddelivery.ordersservice.businesslayer;

import com.fooddelivery.ordersservice.presentationlayer.OrderRequestModel;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import java.util.List;

public interface OrderService {
    List<OrderResponseModel> getAllOrders();

    OrderResponseModel getOrderById(String orderId);

    OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId);

    OrderResponseModel updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel);
    void deleteOrder(String orderId);

}
