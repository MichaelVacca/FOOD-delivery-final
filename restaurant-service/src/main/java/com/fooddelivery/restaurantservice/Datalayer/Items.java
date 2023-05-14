package com.fooddelivery.restaurantservice.Datalayer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public class Items {
private String itemName;
private String itemDesc;
private Double itemCost;

Items(){

}


    public Items(@NotNull String itemName, @NotNull String itemDesc, @NotNull Double itemCost) {
        Objects.requireNonNull(this.itemName = itemName);
        Objects.requireNonNull(this.itemDesc = itemDesc);
        Objects.requireNonNull(this.itemCost = itemCost);
    }

    public @NotNull String getItemName() {
        return itemName;
    }

    public @NotNull String getItemDesc() {
        return itemDesc;
    }

    public @NotNull Double getItemCost() {
        return itemCost;
    }
}
