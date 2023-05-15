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

    @AfterMapping
    default void addLinks(@MappingTarget MenuResponseModel menuResponseModel, Menu menu) {
        Link selfLink = linkTo(methodOn(RestaurantController.class)
                .getMenusInRestaurantById(menuResponseModel.getRestaurantId(),menuResponseModel.getMenuId()))
                .withSelfRel();
        menuResponseModel.add(selfLink);

        Link restaurantLink = linkTo(methodOn(RestaurantController.class)
                .getRestaurantsByRestaurantId(menuResponseModel.getRestaurantId()))
                .withRel("allMenus");
        menuResponseModel.add(restaurantLink);

        Link menusLink = linkTo(methodOn(RestaurantController.class)
                .getMenusInRestaurants(menuResponseModel.getRestaurantId(), null))
                .withRel("allMenusInRestaurant");
        menuResponseModel.add(menusLink);

    }
}
