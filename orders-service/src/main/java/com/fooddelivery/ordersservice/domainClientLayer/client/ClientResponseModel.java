package com.fooddelivery.ordersservice.domainClientLayer.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientResponseModel {

    private  String clientId;
    private  String userName;
    private  String password;
    private  String age;
    private  String emailAddress;
    private  String phoneNumber;
    private  String countryName;
    private  String streetName;
    private  String cityName;
    private  String provinceName;
    private  String postalCode;
}
