package com.fooddelivery.restaurantservice.Presentationlayer;

import com.fooddelivery.restaurantservice.Datalayer.Items;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MenuRequestModel {

    private String menuId;
    private String restaurantId;
    private String typeOfMenu;
    private List<Items> items;

}
