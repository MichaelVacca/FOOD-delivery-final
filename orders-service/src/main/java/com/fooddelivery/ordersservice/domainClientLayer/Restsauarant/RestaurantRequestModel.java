package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantRequestModel extends RepresentationModel<RestaurantRequestModel> {
    String restaurantName;
    String countryName;
    String streetName;
    String provinceName;
    String cityName;
    String postalCode;

/*    public RestaurantRequestModel() {
    }

    public RestaurantRequestModel(String restaurantName, String countryName, String streetName, String provinceName, String cityName, String postalCode) {
        this.restaurantName = restaurantName;
        this.countryName = countryName;
        this.streetName = streetName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postalCode = postalCode;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }*/
}
