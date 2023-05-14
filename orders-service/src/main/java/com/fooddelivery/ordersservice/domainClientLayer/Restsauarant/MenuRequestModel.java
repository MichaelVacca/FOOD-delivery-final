package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;


import com.fooddelivery.ordersservice.datalayer.Items;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRequestModel {

    private String menuId;
    private String restaurantId;
    private String typeOfMenu;
    private List<Items> items;
    private Double totalPrice;

}
