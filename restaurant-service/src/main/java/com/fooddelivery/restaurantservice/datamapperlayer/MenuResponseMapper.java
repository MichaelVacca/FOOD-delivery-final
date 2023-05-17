package com.fooddelivery.restaurantservice.datamapperlayer;

import com.fooddelivery.restaurantservice.Datalayer.Menu;

import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuResponseModel;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantController;
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
public interface MenuResponseMapper {
    @Mapping(expression = "java(menu.getMenuIdentifier().getMenuId())", target = "menuId")
    @Mapping(expression = "java(menu.getRestaurantIdentifier().getRestaurantId())", target = "restaurantId")
    //@Mapping(expression = "java(menu.getTypeOfMenu())", target = "typeOfMenu")

    MenuResponseModel entityToResponseModel(Menu menu);
    List<MenuResponseModel> entityToResponseModelList(List<Menu> menus);

    //TODO fic menu links
    @AfterMapping
    default void addLinks(@MappingTarget MenuResponseModel menuResponseModel, Menu  menu) {

        URI baseUri = URI.create("http://localhost:8080");

        Link selfLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "restaurants" + menuResponseModel.getRestaurantId() +"menus", menuResponseModel.getMenuId())
                        .toUriString(),
                "self");

        Link clientLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "restaurants" + menuResponseModel.getRestaurantId() + "menus")
                        .toUriString(),
                "allClients");

        menuResponseModel.add(selfLink);
        menuResponseModel.add(clientLink);
    }
}
