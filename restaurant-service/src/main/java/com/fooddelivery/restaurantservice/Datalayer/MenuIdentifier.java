package com.fooddelivery.restaurantservice.Datalayer;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuIdentifier {

    private String menuId;

    public MenuIdentifier(){

    }

    public MenuIdentifier(String menuId){
        this.menuId = menuId;
    }

    public String getMenuId() {
        return menuId;
    }
}
