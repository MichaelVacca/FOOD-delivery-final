package com.fooddelivery.clientservice.Datalayer;



import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public class Address {

    private String countryName;
    private String streetName;
    private String cityName;
    private String provinceName;
    private String postalCode;

    public Address(@NotNull String countryName, @NotNull String streetName, @NotNull String cityName, @NotNull String provinceName, @NotNull String postalCode) {
        Objects.requireNonNull(this.countryName = countryName);
        Objects.requireNonNull(this.streetName = streetName);
        Objects.requireNonNull(this.cityName = cityName);
        Objects.requireNonNull(this.provinceName = provinceName);
        Objects.requireNonNull(this.postalCode = postalCode);
    }

    public @NotNull String getCountryName() {
        return countryName;
    }

    public @NotNull String getStreetName() {return streetName;}

    public @NotNull String getCityName() {
        return cityName;
    }

    public @NotNull String getProvinceName() {
        return provinceName;
    }

    public @NotNull String getPostalCode() {
        return postalCode;
    }
    @SuppressWarnings("unused") //unused by the code but used by JPA
    Address() {

    }
}
