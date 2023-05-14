package com.fooddelivery.restaurantservice.businesslayer.Restaurant;


import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantRequestModel;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantResponseModel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RestaurantService {

    //All Restaurant methods
    List<RestaurantResponseModel> getRestaurants();

    //TODO: change to requestModel and responseModel
    RestaurantResponseModel getRestaurantsById(String restaurantId);
    //Restaurant addRestaurant(RestaurantRequestModel restaurantRequestModel);

    //TODO: change to requestModel
    //Restaurant addRestaurant(Restaurant newRestaurant);
    RestaurantResponseModel addRestaurant(RestaurantRequestModel restaurantRequestModel);

    //TODO: change to requestModel

    RestaurantResponseModel updateRestaurant(RestaurantRequestModel restaurantRequestModel, String restaurantId);
    void deleteRestaurant(String restaurantId);



}
