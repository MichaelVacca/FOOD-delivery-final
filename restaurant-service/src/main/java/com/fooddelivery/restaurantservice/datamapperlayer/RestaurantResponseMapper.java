package com.fooddelivery.restaurantservice.datamapperlayer;


import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantResponseMapper {
    @Mapping(expression = "java(restaurant.getRestaurantIdentifier().getRestaurantId())", target = "restaurantId")
/*    @Mapping(expression = "java(restaurant.getAddress().getCountryName())", target = "countryName")
    @Mapping(expression = "java(restaurant.getAddress().getStreetName())", target = "streetName")
    @Mapping(expression = "java(restaurant.getAddress().getCityName())", target = "cityName")
    @Mapping(expression = "java(restaurant.getAddress().getProvinceName())", target = "provinceName")
    @Mapping(expression = "java(restaurant.getAddress().getPostalCode())", target = "postalCode")*/
    RestaurantResponseModel entityToResponseModel(Restaurant restaurant);
    List<RestaurantResponseModel> entityToResponseModelList(List<Restaurant> restaurants);






}
