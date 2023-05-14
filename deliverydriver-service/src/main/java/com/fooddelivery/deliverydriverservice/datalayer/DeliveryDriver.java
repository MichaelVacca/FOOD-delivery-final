package com.fooddelivery.deliverydriverservice.datalayer;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drivers")
public class DeliveryDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String description;
    private String employeeSince;

    @Embedded
    DeliveryDriverIdentifier deliveryDriverIdentifier;

    @Embedded
    Address address;

    public DeliveryDriver(){
        this.deliveryDriverIdentifier = new DeliveryDriverIdentifier();
    }

    public DeliveryDriver(String firstName, String lastName, String dateOfBirth, String description, String employeeSince,  Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.employeeSince = employeeSince;
        this.deliveryDriverIdentifier = new DeliveryDriverIdentifier();
        this.address = address;
    }
}
