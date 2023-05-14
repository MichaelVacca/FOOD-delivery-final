package com.fooddelivery.restaurantservice.businesslayer.Menu;


import com.fooddelivery.restaurantservice.Datalayer.*;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuRequestModel;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuResponseModel;
import com.fooddelivery.restaurantservice.datamapperlayer.MenuRequestMapper;
import com.fooddelivery.restaurantservice.datamapperlayer.MenuResponseMapper;
import com.fooddelivery.restaurantservice.utils.exceptions.DuplicateMenuIdentifierException;
import com.fooddelivery.restaurantservice.utils.exceptions.InvalidInputException;
import com.fooddelivery.restaurantservice.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class menuServiceImpl implements MenuService {

    private MenuRepository menuRepository;
    private MenuResponseMapper menuResponseMapper;

    private RestaurantRepository restaurantRepository;

    private MenuRequestMapper menuRequestMapper;

    public menuServiceImpl(MenuRepository menuRepository, MenuResponseMapper menuResponseMapper, RestaurantRepository restaurantRepository, MenuRequestMapper menuRequestMapper) {
        this.menuRepository = menuRepository;
        this.menuResponseMapper = menuResponseMapper;
        this.restaurantRepository = restaurantRepository;
        this.menuRequestMapper = menuRequestMapper;
    }

    @Override
    public List<MenuResponseModel> getMenusInRestaurantsByField(String restaurantId, Map<String, String> queryParams) {
        if(!restaurantRepository.existsByRestaurantIdentifier_RestaurantId(restaurantId)){
            throw new NotFoundException("Restaurant with id: " + restaurantId + " not found.");
        }

        String typeOfMenu = queryParams.get("typeOfMenu");

        Map<String, TypeOfMenu> typeOfMenuMap = new HashMap<String, TypeOfMenu>();
        typeOfMenuMap.put("Pizza", TypeOfMenu.Pizza);
        typeOfMenuMap.put("Pasta", TypeOfMenu.Pasta);
        typeOfMenuMap.put("Appetizers", TypeOfMenu.Appetizers);
        typeOfMenuMap.put("Drinks", TypeOfMenu.Drinks);
        typeOfMenuMap.put("Entrees", TypeOfMenu.Entrees);
        if(typeOfMenu != null){
            return menuResponseMapper.entityToResponseModelList(menuRepository.
            findAllByMenuIdentifier_MenuIdAndTypeOfMenuEquals(restaurantId,
                    typeOfMenuMap.get(typeOfMenu.toLowerCase())));

        }

        //Should get all the menus in a restaurant
        return menuResponseMapper.entityToResponseModelList(menuRepository.
                findByRestaurantIdentifier_RestaurantId(restaurantId));

    }

    @Override
    public MenuResponseModel getMenusInRestaurantsById(String restaurantId, String menuId) {
        if(!restaurantRepository.existsByRestaurantIdentifier_RestaurantId(restaurantId)){
            throw new NotFoundException("Restaurant with id: " + restaurantId + " not found.");
        }

        Menu foundMenu = menuRepository.
                findByRestaurantIdentifier_RestaurantIdAndMenuIdentifier_MenuId(restaurantId, menuId);

        return menuResponseMapper.entityToResponseModel(foundMenu);

    }



    @Override
    public MenuResponseModel addMenuToRestaurant(MenuRequestModel menuRequestModel, String restaurantId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);

        if(restaurant == null){
            throw new NotFoundException("Unknown restaurant id provided: " + restaurantId);
        }

        MenuIdentifier menuIdentifier = new MenuIdentifier(menuRequestModel.getMenuId());

        //new
        Menu menu = menuRequestMapper.requestModelToEntity(menuRequestModel,menuIdentifier,restaurant.getRestaurantIdentifier());

        try {
/*            return menuResponseMapper.entityToResponseModel(menuRepository.save(menuRequestMapper.
                    requestModelToEntity(menuRequestModel, menuIdentifier, restaurant.getRestaurantIdentifier())));*/
            return menuResponseMapper.entityToResponseModel(menuRepository.save(menu));
        }
        catch (Exception ex){
            if(ex.getMessage().contains("constraint [menu_id]") ||
            ex.getCause().toString().contains("ConstraintViolationException")){
                throw new DuplicateMenuIdentifierException("Restaurant already has a menu with the menu id:" +
                        menuRequestModel.getMenuId());
                }
            throw new InvalidInputException("An unknown error has occurred");
            }
    }

    @Override
    public MenuResponseModel updateMenuInRestaurant(MenuRequestModel menuRequestModel, String restaurantId, String menuId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);

        if(restaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId + " not found.");
        }
        Menu oldMenu = menuRepository.findByMenuIdentifier_MenuId(menuId);

        Menu toBeUpdated = menuRequestMapper.requestModelToEntity(menuRequestModel,
                oldMenu.getMenuIdentifier(),oldMenu.getRestaurantIdentifier());
        toBeUpdated.setId(oldMenu.getId());

        return menuResponseMapper.entityToResponseModel(menuRepository.save(toBeUpdated));
    }

    @Override
    public void deleteMenuInRestaurant(String restaurantId, String menuId) {
        Restaurant restaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);

        if(restaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId +" not found.");
        }

        Menu oldMenu = menuRepository.findByMenuIdentifier_MenuId(menuId);

        if(oldMenu == null){
            return;
        }
        menuRepository.delete(oldMenu);

    }

}
