package com.fooddelivery.apigateway.presentationLayer;


import com.fooddelivery.apigateway.businessLayer.RestaurantService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {

    private final Integer UUID_SIZE = 36;
    private RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping(produces = "application/json")
    ResponseEntity<RestaurantResponseModel[]> getAllRestaurantsAggregate() {
        log.debug("1. Received in Restaurant Controller getAllRestaurantsAggregate");
        return ResponseEntity.ok().body(restaurantService.getAllRestaurantsAggregate());
    }

    @GetMapping(value="/{restaurantId}", produces = "application/json")
    ResponseEntity<RestaurantMenuResponseModel> getRestaurantAggregate(@PathVariable String restaurantId){

        log.debug("1. Received in Restaurant Controller getRestaurantMenuAggregate with restaurantId: " + restaurantId);
        return ResponseEntity.ok().body(restaurantService.getRestaurantAggregate(restaurantId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<RestaurantResponseModel> addRestaurant(@RequestBody RestaurantRequestModel restaurantRequestModel){
        log.debug("1. Received in Restaurant Controller addRestaurant");
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addRestaurant(restaurantRequestModel));
    }
    @PutMapping(value="/{restaurantId}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateRestaurant(@RequestBody RestaurantRequestModel restaurantRequestModel, @PathVariable String restaurantId){
/*        if(restaurantId.length()!=UUID_SIZE){
            throw new InvalidInputException("Restaurant id is invalid: " + restaurantId);
        }*/
        restaurantService.updateRestaurantAggregate(restaurantId, restaurantRequestModel);
        log.debug("1. Received in Restaurant Controller updateRestaurant");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{restaurantId}", produces = "application/json")
    ResponseEntity<Void> deleteRestaurant(@PathVariable String restaurantId){
/*        if(restaurantId.length()!=UUID_SIZE){
            throw new InvalidInputException("Restaurant id is invalid: " + restaurantId);
        }*/
        restaurantService.deleteRestaurantAggregate(restaurantId);
        log.debug("1. Received in Restaurant Controller deleteRestaurant");
        return ResponseEntity.noContent().build();
    }

    //Menus ------------------------------------------------------------------------------------------------------
    //Menus ------------------------------------------------------------------------------------------------------
    //Menus ------------------------------------------------------------------------------------------------------
    //Menus ------------------------------------------------------------------------------------------------------

    @GetMapping(value="/{restaurantId}/menus/{menuId}", produces = "application/json")
    ResponseEntity<MenuResponseModel> getMenuById(@PathVariable String restaurantId, @PathVariable String menuId){

        log.debug("1. Received in Restaurant Controller getMenu");
        return ResponseEntity.ok().body(restaurantService.getMenuByMenuId(restaurantId, menuId));
    }

    @PostMapping(value="/{restaurantId}/menus", consumes = "application/json" ,produces = "application/json")
    ResponseEntity<MenuResponseModel> addMenuToRestaurant(@RequestBody MenuRequestModel menuRequestModel, @PathVariable String restaurantId){
        log.debug("1. Received in Restaurant Controller addMenuToRestaurant");
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addMenuToRestaurant(restaurantId, menuRequestModel));
    }

    @PutMapping(value="/{restaurantId}/menus/{menuId}", consumes = "application/json" ,produces = "application/json")
    ResponseEntity<Void> updateMenuInRestaurantByMenuId(@RequestBody MenuRequestModel menuRequestModel, @PathVariable String restaurantId, @PathVariable String menuId){
        restaurantService.updateMenuInRestaurantByMenuId(menuRequestModel, restaurantId, menuId);
        log.debug("1. Received in Restaurant Controller updateMenuInRestaurantByMenuId");
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value="/{restaurantId}/menus/{menuId}", produces = "application/json")
    ResponseEntity<Void> deleteMenuFromInventory(@PathVariable String restaurantId, @PathVariable String menuId){
        restaurantService.deleteMenuFromRestaurant(restaurantId, menuId);
        log.debug("1. Received in Restaurant Controller deleteMenuFromInventory");
        return ResponseEntity.noContent().build();
    }




}
