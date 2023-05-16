package com.fooddelivery.restaurantservice.datamapperlayer;


import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantController;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @AfterMapping
    default void addLinks(@MappingTarget RestaurantResponseModel model, Restaurant restaurant) {
        Link selfLink = linkTo(methodOn(RestaurantController.class)
                .getRestaurantsByRestaurantId(model.getRestaurantId()))
                .withSelfRel();
        model.add(selfLink);

        Link restaurantLink = linkTo(methodOn(RestaurantController.class)
                .getRestaurants())
                .withRel("allRestaurants");
        model.add(restaurantLink);
    }






}
