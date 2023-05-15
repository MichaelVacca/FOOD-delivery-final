package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestaurantMenuResponseModel extends RepresentationModel<RestaurantMenuResponseModel> {

      String restaurantId;
      String restaurantName;
     String menuId;
     String typeOfMenu;



}



