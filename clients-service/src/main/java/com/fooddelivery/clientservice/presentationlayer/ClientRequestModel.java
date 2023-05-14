package com.fooddelivery.clientservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ClientRequestModel {

    private String userName;
    private String password;
    private String age;
    private String emailAddress;
    private String phoneNumber;
    private String countryName;
    private String streetName;
    private String cityName;
    private String provinceName;
    private String postalCode;

}
