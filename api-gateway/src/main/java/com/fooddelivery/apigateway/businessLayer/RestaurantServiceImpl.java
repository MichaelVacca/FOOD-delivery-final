package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.domainClientLayer.RestaurantServiceClient;
import com.fooddelivery.apigateway.presentationLayer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService{
    private RestaurantServiceClient restaurantServiceClient;

    public RestaurantServiceImpl(RestaurantServiceClient restaurantServiceClient) {
        this.restaurantServiceClient = restaurantServiceClient;
    }

    @Override
    public RestaurantMenuResponseModel getRestaurantAggregate(String restaurantId) {
        return restaurantServiceClient.getRestaurantAggregate(restaurantId);
    }

    @Override
    public RestaurantResponseModel[] getAllRestaurantsAggregate() {
        return restaurantServiceClient.getAllRestaurantsAggregate();
    }

    @Override
    public RestaurantResponseModel addRestaurant(RestaurantRequestModel restaurantRequestModel) {
        return restaurantServiceClient.addRestaurantAggregate(restaurantRequestModel);
    }

    @Override
    public MenuResponseModel addMenuToRestaurant(String restaurantId, MenuRequestModel newMenu) {
        return restaurantServiceClient.addMenuToRestaurant(restaurantId, newMenu);
    }

    @Override
    public MenuResponseModel getMenuByMenuId(String restaurantId, String menuId) {
        return restaurantServiceClient.getMenuByMenuId(restaurantId, menuId);
    }

    @Override
    public void updateMenuInRestaurantByMenuId(MenuRequestModel menuRequestModel, String restaurantId, String menuId) {
        restaurantServiceClient.modifyMenuInRestaurant(restaurantId,menuId,menuRequestModel);
    }

    @Override
    public void updateRestaurantAggregate(String restaurantId, RestaurantRequestModel restaurantRequestModel) {
        restaurantServiceClient.updateRestaurantAggregate(restaurantId, restaurantRequestModel);
    }

    @Override
    public void deleteMenuFromRestaurant(String restaurantId, String menuId) {
        restaurantServiceClient.deleteMenuInRestaurant(restaurantId,menuId);
    }

    @Override
    public void deleteRestaurantAggregate(String url) {
        restaurantServiceClient.deleteRestaurant(url);
    }
}
