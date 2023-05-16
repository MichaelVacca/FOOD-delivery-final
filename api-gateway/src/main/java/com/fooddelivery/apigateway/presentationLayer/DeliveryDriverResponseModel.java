package com.fooddelivery.apigateway.presentationLayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = false)
public class DeliveryDriverResponseModel extends RepresentationModel<DeliveryDriverResponseModel> {

      String deliveryDriverId;
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

}
