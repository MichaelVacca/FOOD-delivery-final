package com.fooddelivery.restaurantservice.Datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findByMenuIdentifier_MenuId(String menuId);

    Menu findByRestaurantIdentifier_RestaurantIdAndMenuIdentifier_MenuId(String restaurantId, String menuId);

    List<Menu> findAllByMenuIdentifier_MenuIdAndTypeOfMenuEquals(String restaurantId, TypeOfMenu typeOfMenu);

    List<Menu> findByRestaurantIdentifier_RestaurantId(String restaurantId);

    List<Menu> findAllByRestaurantIdentifier_RestaurantId(String restaurantId);

}
