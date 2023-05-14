package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;


import com.fooddelivery.ordersservice.datalayer.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
    private  Double totalPrice;


}
