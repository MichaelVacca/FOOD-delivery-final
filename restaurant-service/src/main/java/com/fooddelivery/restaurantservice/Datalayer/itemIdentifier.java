package com.fooddelivery.restaurantservice.Datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class itemIdentifier {
    private String itemId;

    public itemIdentifier(){

        this.itemId = UUID.randomUUID().toString();
    }

    public String getItemId() {

        return itemId;
    }
}
