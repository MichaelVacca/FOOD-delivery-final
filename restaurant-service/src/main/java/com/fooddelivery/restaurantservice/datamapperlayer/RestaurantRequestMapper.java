package com.fooddelivery.restaurantservice.datamapperlayer;


import com.fooddelivery.restaurantservice.Datalayer.Address;
import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantIdentifier",ignore = true)
    Restaurant requestModelToEntity(RestaurantRequestModel requestModel);

/*    @Mapping(target = "countryName", source = "countryName")
    @Mapping(target = "streetName", source = "streetName")
    @Mapping(target = "cityName", source = "cityName")
    @Mapping(target = "provinceName", source = "provinceName")
    @Mapping(target = "postalCode", source = "postalCode")*/
   // Address requestModelToAddress(RestaurantRequestModel requestModel);

}
