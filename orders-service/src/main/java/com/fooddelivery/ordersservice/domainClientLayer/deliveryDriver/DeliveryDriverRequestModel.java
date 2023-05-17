package com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryDriverRequestModel extends RepresentationModel<DeliveryDriverRequestModel> {

     String firstName;
     String lastName;
     String dateOfBirth;
     String description;
     String employeeSince;
     String countryName;
     String streetName;
     String cityName;
     String provinceName;
     String postalCode;

/*    public DeliveryDriverRequestModel() {
    }

    public DeliveryDriverRequestModel(String firstName, String lastName, String dateOfBirth, String description, String employeeSince, String countryName, String streetName, String cityName, String provinceName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.employeeSince = employeeSince;
        this.countryName = countryName;
        this.streetName = streetName;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeSince() {
        return employeeSince;
    }

    public void setEmployeeSince(String employeeSince) {
        this.employeeSince = employeeSince;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }*/
}


