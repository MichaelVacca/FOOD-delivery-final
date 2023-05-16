package com.fooddelivery.restaurantservice.Presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

@Value
@AllArgsConstructor
public class RestaurantResponseModel extends RepresentationModel<RestaurantResponseModel> {
      String restaurantId;
      String restaurantName;
      String countryName;
      String streetName;
      String provinceName;
      String cityName;
      String postalCode;
}
