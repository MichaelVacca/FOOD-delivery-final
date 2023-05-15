package com.fooddelivery.ordersservice.datalayer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersRepository extends MongoRepository<Order,String> {
    List<Order> findAllOrdersByClientIdentifier_ClientId(String clientId);
    Order  findByOrderIdentifier_OrderId(String orderId);
    Order findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(String clientId, String orderId);


}
