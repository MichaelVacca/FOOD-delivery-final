package com.fooddelivery.restaurantservice.datamapperlayer;


import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantController;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface RestaurantResponseMapper {
    @Mapping(expression = "java(restaurant.getRestaurantIdentifier().getRestaurantId())", target = "restaurantId")

    RestaurantResponseModel entityToResponseModel(Restaurant restaurant);
    List<RestaurantResponseModel> entityToResponseModelList(List<Restaurant> restaurants);

/*    @AfterMapping
    default void addLinks(@MappingTarget RestaurantResponseModel model, Restaurant restaurant) {
        Link selfLink = linkTo(methodOn(RestaurantController.class)
                .getRestaurantsByRestaurantId(model.getRestaurantId()))
                .withSelfRel();
        model.add(selfLink);

        Link restaurantLink = linkTo(methodOn(RestaurantController.class)
                .getRestaurants())
                .withRel("allRestaurants");
        model.add(restaurantLink);
    }*/

    @AfterMapping
    default void addLinks(@MappingTarget RestaurantResponseModel restaurantResponseModel, Restaurant restaurant) {

        URI baseUri = URI.create("http://localhost:8080");

        Link selfLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "restaurants", restaurantResponseModel.getRestaurantId())
                        .toUriString(),
                "self");

        Link clientLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "restaurants")
                        .toUriString(),
                "allClients");

        restaurantResponseModel.add(selfLink);
        restaurantResponseModel.add(clientLink);
    }






}
