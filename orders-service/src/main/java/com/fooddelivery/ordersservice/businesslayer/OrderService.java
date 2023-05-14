package com.fooddelivery.ordersservice.businesslayer;

import com.fooddelivery.ordersservice.presentationlayer.OrderRequestModel;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import java.util.List;

public interface OrderService {
    List<OrderResponseModel> getAllOrders();

    OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId);

}
