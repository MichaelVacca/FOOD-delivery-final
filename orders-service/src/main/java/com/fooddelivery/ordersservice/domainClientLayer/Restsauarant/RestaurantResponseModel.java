package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
