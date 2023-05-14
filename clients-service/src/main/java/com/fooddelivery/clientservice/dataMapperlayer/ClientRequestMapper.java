package com.fooddelivery.clientservice.dataMapperlayer;

import com.fooddelivery.clientservice.Datalayer.Client;
import com.fooddelivery.clientservice.presentationlayer.ClientRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {
    @Mapping(target = "clientIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    Client entityToRequestModel(ClientRequestModel clientRequestModel);

}
