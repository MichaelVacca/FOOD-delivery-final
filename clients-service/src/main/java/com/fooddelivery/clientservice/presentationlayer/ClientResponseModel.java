package com.fooddelivery.clientservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponseModel {

    private final String clientId;
    private final String userName;
    private final String password;
    private final String age;
    private final String emailAddress;
    private final String phoneNumber;
    private final String countryName;
    private final String streetName;
    private final String cityName;
    private final String provinceName;
    private final String postalCode;
}
