package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestaurantResponseModel extends RepresentationModel<RestaurantResponseModel> {
      String restaurantId;
      String restaurantName;
      String countryName;
      String streetName;
      String provinceName;
      String cityName;
      String postalCode;
}
