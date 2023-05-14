package com.fooddelivery.ordersservice.datalayer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order,String> {
    Order  findByOrderIdentifier_OrderId(String orderId);
}
