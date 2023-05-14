package com.fooddelivery.deliverydriverservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DeliveryDriverRequestModel {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String description;
    private String employeeSince;
    private String countryName;
    private String streetName;
    private String cityName;
    private String provinceName;
    private String postalCode;
}
