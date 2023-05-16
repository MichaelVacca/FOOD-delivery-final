package com.fooddelivery.clientservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

@Value
@AllArgsConstructor
public class ClientResponseModel extends RepresentationModel<ClientResponseModel> {

      String clientId;
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
}
