package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = false)
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
