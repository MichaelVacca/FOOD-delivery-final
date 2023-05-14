package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestaurantMenuResponseModel extends RepresentationModel<RestaurantMenuResponseModel> {

    private  String restaurantId;
    private  String restaurantName;
    private String menuId;
    private String typeOfMenu;



}



