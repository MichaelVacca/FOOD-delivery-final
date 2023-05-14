package com.fooddelivery.restaurantservice.datamapperlayer;


import com.fooddelivery.restaurantservice.Datalayer.Menu;
import com.fooddelivery.restaurantservice.Datalayer.MenuIdentifier;
import com.fooddelivery.restaurantservice.Datalayer.RestaurantIdentifier;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MenuRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression = "java(menuIdentifier)", target="menuIdentifier"),
            @Mapping(expression = "java(restaurantIdentifier)", target="restaurantIdentifier"),
            //@Mapping(expression = "java(items)", target="items"),
    })
    Menu requestModelToEntity(MenuRequestModel menuRequestModel, MenuIdentifier menuIdentifier , RestaurantIdentifier restaurantIdentifier);


}
