package com.fooddelivery.restaurantservice.Presentationlayer;


import com.fooddelivery.restaurantservice.Datalayer.Items;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class MenuResponseModel extends RepresentationModel<MenuResponseModel> {
      String restaurantId;
      String menuId;
      String typeOfMenu;
      List<Items> items;


}
