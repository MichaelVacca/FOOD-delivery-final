package com.fooddelivery.restaurantservice.Presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class RestaurantMenuResponseModel extends RepresentationModel<RestaurantMenuResponseModel> {

    private final String restaurantId;
    private final String restaurantName;
    private List<MenuResponseModel> allItems;



}



