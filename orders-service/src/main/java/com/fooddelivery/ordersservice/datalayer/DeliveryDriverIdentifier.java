package com.fooddelivery.ordersservice.datalayer;



import java.util.UUID;


public class DeliveryDriverIdentifier {
    private String deliveryDriverId;

    public DeliveryDriverIdentifier(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }
}
