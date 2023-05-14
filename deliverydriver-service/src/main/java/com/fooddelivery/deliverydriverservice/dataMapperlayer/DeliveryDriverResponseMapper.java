package com.fooddelivery.deliverydriverservice.dataMapperlayer;


import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryDriverResponseMapper {

    @Mapping(expression = "java(deliveryDriver.getDeliveryDriverIdentifier().getDeliveryDriverId())", target = "deliveryDriverId")
    @Mapping(expression = "java(deliveryDriver.getAddress().getCountryName())", target = "countryName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getStreetName())", target = "streetName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getCityName())", target = "cityName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getProvinceName())", target = "provinceName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getPostalCode())", target = "postalCode")
    DeliveryDriverResponseModel entityToResponseModel(DeliveryDriver deliveryDriver);

    List<DeliveryDriverResponseModel> entityListToResponseModelList(List<DeliveryDriver> deliveryDrivers);
}
