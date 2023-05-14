package com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryDriverResponseModel {

    private  String deliveryDriverId;
    private  String firstName;
    private  String lastName;
    private  String dateOfBirth;
    private  String description;
    private  String employeeSince;
    private  String countryName;
    private  String streetName;
    private  String cityName;
    private  String provinceName;
    private  String postalCode;

}
