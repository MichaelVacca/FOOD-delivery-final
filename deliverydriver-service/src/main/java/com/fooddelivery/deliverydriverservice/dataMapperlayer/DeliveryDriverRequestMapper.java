package com.fooddelivery.deliverydriverservice.dataMapperlayer;

import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryDriverRequestMapper {
    @Mapping(target= "deliveryDriverIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    DeliveryDriver entityToRequestModel(DeliveryDriverRequestModel deliveryDriverRequestModel);
}
