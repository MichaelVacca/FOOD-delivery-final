package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestaurantResponseModel extends RepresentationModel<RestaurantResponseModel> {
    private  String restaurantId;
    private  String restaurantName;
    private  String countryName;
    private  String streetName;
    private  String provinceName;
    private  String cityName;
    private  String postalCode;
}
