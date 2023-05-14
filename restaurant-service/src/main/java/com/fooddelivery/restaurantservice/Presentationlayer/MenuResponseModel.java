package com.fooddelivery.restaurantservice.Presentationlayer;


import com.fooddelivery.restaurantservice.Datalayer.Items;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuResponseModel {
    private final String restaurantId;
    private final String menuId;
    private final String typeOfMenu;
    private final List<Items> items;


}
