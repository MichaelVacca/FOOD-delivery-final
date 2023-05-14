package com.fooddelivery.apigateway.presentationLayer;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MenuResponseModel extends RepresentationModel<MenuResponseModel> {
    private  String restaurantId;
    private  String menuId;
    private  String typeOfMenu;
    private  List<Items> items;


}
