package com.fooddelivery.restaurantservice.Presentationlayer;


import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Datalayer.RestaurantRepository;
import com.fooddelivery.restaurantservice.businesslayer.Menu.MenuService;
import com.fooddelivery.restaurantservice.businesslayer.Restaurant.RestaurantService;
import com.fooddelivery.restaurantservice.utils.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;
/*    public RestaurantController(com.example.fooddeliveryws.restaurantSubDomain.businesslayer.Restaurant.restaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }*/
    //TODO: RESPONSE ENTITY
    @GetMapping
    public List<RestaurantResponseModel> getRestaurants(){
        return restaurantService.getRestaurants();
    }


    //TODO: RESPONSE ENTITY
    @GetMapping("/{restaurantId}")
    public RestaurantResponseModel getRestaurantsByRestaurantId(@PathVariable String restaurantId){
        return restaurantService.getRestaurantsById(restaurantId);
    }
    //my lab
    @PostMapping()
    ResponseEntity <RestaurantResponseModel> addRestaurant(@RequestBody  RestaurantRequestModel restaurantRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addRestaurant(restaurantRequestModel));
    }


/*    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable String restaurantId){
        return restaurantService.updateRestaurant(restaurant, restaurantId);
    }*/

/*    @PostMapping
    ResponseEntity<RestaurantResponseModel> addRestaurant(@RequestBody RestaurantRequestModel restaurantRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addRestaurant(restaurantRequestModel));
    }*/

    @PutMapping("/{restaurantId}")
    ResponseEntity <RestaurantResponseModel> updateRestaurant(@RequestBody RestaurantRequestModel restaurantRequestModel, @PathVariable String restaurantId){


        return ResponseEntity.ok().body(restaurantService.updateRestaurant(restaurantRequestModel, restaurantId));
    }
/*    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@PathVariable String restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
    }*/
    @DeleteMapping("/{restaurantId}")
    ResponseEntity <Void> deleteRestaurant(@PathVariable String restaurantId){

        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Menus

    @GetMapping("/{restaurantId}/menus/{menuId}")
    MenuResponseModel getMenusInRestaurantById(@PathVariable String restaurantId,@PathVariable String menuId){
        return menuService.getMenusInRestaurantsById(restaurantId, menuId);
    }

/*    @PostMapping("/{restaurantId}/menus")
    public MenuResponseModel addMenuToRestaurant(@RequestBody MenuRequestModel menuRequestModel, @PathVariable String restaurantId){
        return menuService.addMenuToRestaurant(menuRequestModel, restaurantId);

    }*/
    @PostMapping("/{restaurantId}/menus")
    ResponseEntity <MenuResponseModel> addMenuToRestaurant(@RequestBody MenuRequestModel menuRequestModel,
                                                           @PathVariable String restaurantId){
        if(menuRequestModel.getMenuId().length() != 36){
            throw new InvalidInputException("Invalid menu id provided:" + menuRequestModel.getMenuId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.addMenuToRestaurant(menuRequestModel,restaurantId));
    }

    @GetMapping("/{restaurantId}/menus")
    List<MenuResponseModel> getMenusInRestaurants(@PathVariable String restaurantId, @RequestParam(required = false) Map<String,String> queryParams){
        return menuService.getMenusInRestaurantsByField(restaurantId,queryParams);
    }

    @PutMapping("/{restaurantId}/menus/{menuId}")
    MenuResponseModel updateMenuInRestaurant(@RequestBody MenuRequestModel menuRequestModel,
                                             @PathVariable String restaurantId, @PathVariable String menuId){
        if(menuRequestModel.getMenuId().length() != 36){
            throw new InvalidInputException("Invalid menu id provided:" + menuRequestModel.getMenuId() + "| must be 36 characters long.");
        }
        return menuService.updateMenuInRestaurant(menuRequestModel,restaurantId,menuId);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    void deleteMenuFromRestaurantById(@PathVariable String restaurantId, @PathVariable String menuId){
        menuService.deleteMenuInRestaurant(restaurantId,menuId);
    }
}
