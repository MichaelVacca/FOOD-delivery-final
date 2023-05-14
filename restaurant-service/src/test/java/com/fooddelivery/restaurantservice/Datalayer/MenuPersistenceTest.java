package com.fooddelivery.restaurantservice.Datalayer;

import com.fooddelivery.restaurantservice.Presentationlayer.MenuRequestModel;
import com.fooddelivery.restaurantservice.Presentationlayer.MenuResponseModel;
import com.fooddelivery.restaurantservice.businesslayer.Menu.MenuService;
import com.fooddelivery.restaurantservice.businesslayer.Menu.menuServiceImpl;
import com.fooddelivery.restaurantservice.datamapperlayer.MenuResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
 public class MenuPersistenceTest {

    @Mock
    private MenuResponseMapper menuResponseMapper;

    @InjectMocks
    private menuServiceImpl menuServiceImpl;


    private Restaurant preCreatedRestaurant;

    private Menu preSavedMenu;

    String VALID_RESTAURANT_ID = "055b4a20-d29d-46ce-bb46-2c15b8ed6526";

    String FOUND_MENUID = "ba213745-df23-425a-b9cd-3c50b311c019";
    String NOT_FOUND_MENUID = "";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    @BeforeEach
    public void setup(){
        menuRepository.deleteAll();
        preCreatedRestaurant = new Restaurant("Mcdonalds");
        RestaurantIdentifier restaurantIdentifier = preCreatedRestaurant.getRestaurantIdentifier();
        //preSavedMenu = menuRepository.save(createNewMenu(restaurantIdentifier, FOUND_MENUID));
        preSavedMenu = menuRepository.save(new Menu("Desserts"));
    }

    //failed
    @Test
    public void findByMenuIdentifier_MenuID_ShouldSucceed(){
        //Menu found = menuRepository.findByMenuIdentifier_MenuId(FOUND_MENUID);
        Menu found = menuRepository.findByMenuIdentifier_MenuId(preSavedMenu.getMenuIdentifier().getMenuId());

        assertNotNull(found);
        assertThat(preSavedMenu, samePropertyValuesAs(found));
    }
    @Test
    public void findByInvalidMenuIdentifier_MenuId_ShouldReturnNull(){
        Menu found = menuRepository.findByMenuIdentifier_MenuId(preSavedMenu.getMenuIdentifier().getMenuId() + 1);

        assertNull(found);
    }

    @Test
    public void saveNewMenu(){
        String menuId = "4ae30218-d4b7-11ed-afa1-0242ac120002";
        String typeOfMenu = "Salads";
        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        Menu menu = new Menu(typeOfMenu, items);

        MenuRequestModel menuRequestModel = new MenuRequestModel(menuId,VALID_RESTAURANT_ID, typeOfMenu, items);

        Menu savedMenu = menuRepository.save(menu);

        assertNotNull(menu);
        //assertNotNull(menu.getMenuIdentifier().getMenuId());
        assertNotNull(menu.getId());
        assertNotNull(menu.getTypeOfMenu());
        assertNotNull(menu.getItems());
    }

    @Test
    public void updateMenu(){
        String expectedTypeOfMenu = "Fish";

        preSavedMenu.setTypeOfMenu(expectedTypeOfMenu);

        Menu updatedMenu = menuRepository.save(preSavedMenu);

        assertThat(updatedMenu,samePropertyValuesAs(preSavedMenu));
        assertNotEquals("Desserts",updatedMenu.getTypeOfMenu());

    }

/*    @Test
    public void testUpdateMenuInRestaurant() {
        // Create a new restaurant entity and save it
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setRestaurantIdentifier(new RestaurantIdentifier());

        restaurantRepository.save(restaurant);

        // Create a new menu entity and save it
        Menu menu = new Menu();
        menu.setTypeOfMenu("Test Menu");
        menu.setTypeOfMenu("Seafood");
        menu.setRestaurantIdentifier(restaurant.getRestaurantIdentifier());
        menu.setMenuIdentifier(new MenuIdentifier("test-menu"));
        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));
        menuRepository.save(menu);

        // Create a new MenuRequestModel to update the menu
        MenuRequestModel requestModel = new MenuRequestModel(menu.getMenuIdentifier().toString(),restaurant.getRestaurantIdentifier().getRestaurantId(), menu.getTypeOfMenu(), items);

        // Call the updateMenuInRestaurant method with the restaurant and menu IDs and the request model
        MenuResponseModel responseModel = menuService.updateMenuInRestaurant(requestModel, restaurant.getRestaurantIdentifier().getRestaurantId(), menu.getMenuIdentifier().getMenuId());

        // Assert that the response model contains the expected data
        assertEquals("Fish", responseModel.getTypeOfMenu());
        assertEquals(restaurant.getRestaurantIdentifier().getRestaurantId(), responseModel.getRestaurantId());
        assertEquals(menu.getMenuIdentifier().getMenuId(), responseModel.getMenuId());
    }*/


    private Menu createNewMenu(RestaurantIdentifier restaurantIdentifier, String menuID){
        MenuIdentifier menuIdentifier  = new MenuIdentifier(menuID);
        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        return new Menu(menuIdentifier, "Burgers", items);
    }
    //@Test
    /*public void testGetMenusInRestaurantsByField() {
        // Create a test menu
        Menu menu = new Menu(
                new MenuIdentifier("menu1"),
                "Pizza",
                Arrays.asList(new Items("item1", "description1", 10.0))
        );

        // Set up the behavior of the mock repository
        when(menuRepository.findByMenuIdentifier_MenuId("restaurant1")).thenReturn(true);
        when(menuRepository.findByMenuIdentifier_MenuId("menu1")).thenReturn(Optional.of(menu));

        // Set up the behavior of the mock mapper
        List<MenuResponseModel> menuResponseModels = Arrays.asList(
                new MenuResponseModel("menu1", "Pizza")
        );
        when(menuResponseMapper.entityToResponseModelList(anyList())).thenReturn(menuResponseModels);

        // Call the method with valid input
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("typeOfMenu", "Pizza");
        List<MenuResponseModel> result = menuService.getMenusInRestaurantsByField("restaurant1", queryParams);

        // Assert the output
        assertEquals(1, result.size());
        assertEquals("menu1", result.get(0).getMenuId());
        assertEquals("Pizza", result.get(0).getTypeOfMenu());

        // Call the method with invalid input
        assertThrows(NotFoundException.class, () -> {
            menuService.getMenusInRestaurantsByField("invalid-restaurant", queryParams);
        });
    }*/



}