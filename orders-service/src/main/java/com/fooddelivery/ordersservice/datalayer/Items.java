package com.fooddelivery.ordersservice.datalayer;


import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
public class Items {
private String itemName;
private String itemDesc;
private Double itemCost;

public Items(){

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
