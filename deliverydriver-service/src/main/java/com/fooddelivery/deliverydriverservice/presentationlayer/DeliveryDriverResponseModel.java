package com.fooddelivery.deliverydriverservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@AllArgsConstructor
public class DeliveryDriverResponseModel extends RepresentationModel<DeliveryDriverResponseModel> {

    private final String deliveryDriverId;
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String description;
    private final String employeeSince;
    private final String countryName;
    private final String streetName;
    private final String cityName;
    private final String provinceName;
    private final String postalCode;

}
