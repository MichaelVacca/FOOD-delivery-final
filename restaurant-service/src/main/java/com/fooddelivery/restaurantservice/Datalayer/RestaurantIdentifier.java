package com.fooddelivery.restaurantservice.Datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class RestaurantIdentifier {
    private String restaurantId;

    public RestaurantIdentifier(){
        this.restaurantId = UUID.randomUUID().toString();
    }

    public String getRestaurantId() {
        return restaurantId;
    }


}
