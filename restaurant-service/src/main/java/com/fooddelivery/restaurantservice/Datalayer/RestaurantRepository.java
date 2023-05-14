package com.fooddelivery.restaurantservice.Datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {

    Restaurant findByRestaurantIdentifier_RestaurantId(String restaurantId);

    Boolean existsByRestaurantIdentifier_RestaurantId(String restaurantId);
}
