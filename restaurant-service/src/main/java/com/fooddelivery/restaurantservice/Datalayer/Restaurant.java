package com.fooddelivery.restaurantservice.Datalayer;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String restaurantName;

    private String countryName;
    private String streetName;
    private String cityName;
    private String provinceName;
    private String postalCode;

    public Restaurant(String restaurantName, String countryName, String streetName, String cityName, String provinceName, String postalCode) {
        this.restaurantName = restaurantName;
        this.countryName = countryName;
        this.streetName = streetName;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.postalCode = postalCode;
        this.restaurantIdentifier = new RestaurantIdentifier();
    }


    @Embedded
    private MenuIdentifier menuIdentifier;

/*    @Embedded
    private Address address;*/


@Embedded
    private RestaurantIdentifier restaurantIdentifier;

    public Restaurant() {
        this.restaurantIdentifier = new RestaurantIdentifier();
        this.menuIdentifier = new MenuIdentifier();
    }

    public Restaurant(String restaurantName) {
        this.restaurantIdentifier = new RestaurantIdentifier();
        this.restaurantName = restaurantName;
    }

/*    public Restaurant(String restaurantName, Address address) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.restaurantIdentifier = new RestaurantIdentifier();
    }*/
}
