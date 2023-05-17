package com.fooddelivery.ordersservice.domainClientLayer.client;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientRequestModel extends RepresentationModel<ClientRequestModel> {

     String userName;
     String password;
     String age;
     String emailAddress;
     String phoneNumber;
     String countryName;
     String streetName;
     String cityName;
     String provinceName;
     String postalCode;


    /*public ClientRequestModel() {
    }

    public ClientRequestModel(String userName, String password, String age, String emailAddress, String phoneNumber, String countryName, String streetName, String cityName, String provinceName, String postalCode) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.countryName = countryName;
        this.streetName = streetName;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.postalCode = postalCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
