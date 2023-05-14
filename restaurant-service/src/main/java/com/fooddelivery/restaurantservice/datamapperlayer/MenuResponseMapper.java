package com.fooddelivery.restaurantservice.datamapperlayer;

import com.fooddelivery.restaurantservice.Datalayer.Menu;

import com.fooddelivery.restaurantservice.Presentationlayer.MenuResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuResponseMapper {
    @Mapping(expression = "java(menu.getMenuIdentifier().getMenuId())", target = "menuId")
    @Mapping(expression = "java(menu.getRestaurantIdentifier().getRestaurantId())", target = "restaurantId")
    //@Mapping(expression = "java(menu.getTypeOfMenu())", target = "typeOfMenu")

    MenuResponseModel entityToResponseModel(Menu menu);
    List<MenuResponseModel> entityToResponseModelList(List<Menu> menus);
}
