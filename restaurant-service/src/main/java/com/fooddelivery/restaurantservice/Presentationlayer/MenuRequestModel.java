package com.fooddelivery.restaurantservice.Presentationlayer;

import com.fooddelivery.restaurantservice.Datalayer.Items;
import lombok.*;

import java.util.List;

@Data
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MenuRequestModel {

    private String menuId;
    private String restaurantId;
    private String typeOfMenu;
    private List<Items> items;



}
