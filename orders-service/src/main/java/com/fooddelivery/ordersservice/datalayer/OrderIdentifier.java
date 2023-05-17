package com.fooddelivery.ordersservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

public class OrderIdentifier {
    @Indexed(unique = true)
    private String orderId;

    public OrderIdentifier() {
        this.orderId = UUID.randomUUID().toString();
    }
    public OrderIdentifier(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }

}
