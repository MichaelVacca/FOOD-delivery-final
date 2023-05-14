package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RestaurantMenuResponseModel extends RepresentationModel<RestaurantMenuResponseModel> {

    private  String restaurantId;
    private  String restaurantName;
    private String menuId;
    private String typeOfMenu;
}



