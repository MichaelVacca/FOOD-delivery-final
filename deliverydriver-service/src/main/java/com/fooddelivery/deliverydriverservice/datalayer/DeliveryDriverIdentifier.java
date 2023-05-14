package com.fooddelivery.deliverydriverservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class DeliveryDriverIdentifier {
    private String deliveryDriverId;

    public DeliveryDriverIdentifier() {
        this.deliveryDriverId = UUID.randomUUID().toString();
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }
}
