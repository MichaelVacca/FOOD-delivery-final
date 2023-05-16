package com.fooddelivery.restaurantservice.Presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@AllArgsConstructor
public class RestaurantResponseModel extends RepresentationModel<RestaurantResponseModel> {
    private final String restaurantId;
    private final String restaurantName;
    private final String countryName;
    private final String streetName;
    private final String provinceName;
    private final String cityName;
    private final String postalCode;
}
