package com.fooddelivery.restaurantservice.businesslayer.Menu;



import com.fooddelivery.restaurantservice.Presentationlayer.MenuRequestModel;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


public interface MenuService {
    //List<MenuResponseModel> getAllMenus();
    List<MenuResponseModel> getMenusInRestaurantsByField(String restaurantId, Map<String, String> queryParams);

    MenuResponseModel getMenusInRestaurantsById(String restaurantId, String menuId );
    MenuResponseModel addMenuToRestaurant(MenuRequestModel menuRequestModel, String restaurantId);




    //todo Put
    MenuResponseModel updateMenuInRestaurant(MenuRequestModel menuRequestModel, String restaurantId, String menuId);
    




    //todo Del
    void deleteMenuInRestaurant(String restaurantId, String menuId);


}
