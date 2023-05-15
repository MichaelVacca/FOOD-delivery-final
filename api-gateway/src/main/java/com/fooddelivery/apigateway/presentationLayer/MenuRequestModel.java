package com.fooddelivery.apigateway.presentationLayer;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;


public class MenuRequestModel {

    private String menuId;
    private String restaurantId;
    private String typeOfMenu;
    private List<Items> items;

    public MenuRequestModel() {
    }

/*    public MenuRequestModel(String menuId, String restaurantId, String typeOfMenu, List<Items> items) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.typeOfMenu = typeOfMenu;
        this.items = items;
    }*/

    public MenuRequestModel(String menuId, String restaurantId, String typeOfMenu) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.typeOfMenu = typeOfMenu;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTypeOfMenu() {
        return typeOfMenu;
    }

    public void setTypeOfMenu(String typeOfMenu) {
        this.typeOfMenu = typeOfMenu;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
