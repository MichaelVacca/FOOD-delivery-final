package com.fooddelivery.restaurantservice.Datalayer;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="menus")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    MenuIdentifier menuIdentifier;
    @Embedded
    RestaurantIdentifier restaurantIdentifier;

    private String typeOfMenu;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "items", joinColumns = @JoinColumn(name="menu_id"))
    private List<Items> items;

    public Menu(){

    }

    public Menu(MenuIdentifier menuIdentifier, String typeOfMenu, List<Items> items) {
        this.menuIdentifier = menuIdentifier;
        this.typeOfMenu = typeOfMenu;
        this.items = items;
    }
    public Menu( String typeOfMenu, List<Items> items) {
        this.menuIdentifier = new MenuIdentifier();
        this.typeOfMenu = typeOfMenu;
        this.items = items;
    }
    public Menu( String typeOfMenu) {
        this.menuIdentifier = new MenuIdentifier();
        this.typeOfMenu = typeOfMenu;
    }
}
