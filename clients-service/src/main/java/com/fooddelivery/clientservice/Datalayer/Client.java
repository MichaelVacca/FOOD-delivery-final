package com.fooddelivery.clientservice.Datalayer;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    ClientIdentifier clientIdentifier;

    private String userName;
    private String password;
    private String age;
    private String emailAddress;
    private String phoneNumber;

    @Embedded
    private Address address;
    public Client() {
        this.clientIdentifier = new ClientIdentifier();
    }

    public Client(String userName, String password, String age, String emailAddress, String phoneNumber, Address address) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.clientIdentifier = new ClientIdentifier();
    }
}
