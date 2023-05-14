package com.fooddelivery.restaurantservice.Presentationlayer;

import com.fooddelivery.restaurantservice.Datalayer.*;
import jakarta.transaction.Transactional;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantControllerIntegrationTest {

    private final String BASE_URI_RESTAURANTS = "/api/v1/restaurants";

    private final String VALID_RESTAURANT_ID= "055b4a20-d29d-46ce-bb46-2c15b8ed6526";
    private final String VALID_RESTAURANT_NAME= "Savory Street";

    private final String VALID_COUNTRY_NAME= "Canada";
    private final String VALID_STREET_NAME = "Brown St";
    private final String VALID_CITY_NAME = "ChÃ¢teau-Richer";
    private final String VALID_PROVINCE_NAME = "QuÃ©bec";
    private final String VALID_POSTAL_CODE = "B3H-7U5";

    private final String VALID_MENU_ID = "8fb3d5f0-2ceb-4921-a978-736bb4d278b7";
    private final String VALID_MENU_ID_2 = "c4341840-87b8-4c2c-865f-c58b933e1994";

    private final String VALID_TYPE_OF_MENU = "Appetizers";
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

/*    @AfterEach
    void tearDown(){restaurantRepository.deleteAll();}*/

//    @Sql({"/data-mysql.sql"})
    @Test
    public void whenRestaurantsExist_thenReturnAllRestaurants(){
        Integer expectedNumInventories = 5;

        webTestClient.get()
                .uri(BASE_URI_RESTAURANTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumInventories);
    }

    @Test
    public void whenGetRestaurantWithValidIdExists_thenReturnRestaurants(){
        webTestClient.get().uri(BASE_URI_RESTAURANTS + "/" +VALID_RESTAURANT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                //.jsonPath("$.restaurantId").isEqualTo(VALID_RESTAURANT_ID)
                .jsonPath("$.restaurantName").isEqualTo(VALID_RESTAURANT_NAME)
                .jsonPath("$.countryName").isEqualTo(VALID_COUNTRY_NAME)
                .jsonPath("$.streetName").isEqualTo(VALID_STREET_NAME)
                .jsonPath("$.cityName").isEqualTo(VALID_CITY_NAME)
                .jsonPath("$.provinceName").isEqualTo(VALID_PROVINCE_NAME)
                .jsonPath("$.postalCode").isEqualTo(VALID_POSTAL_CODE);


    }

    @Test
    public void whenGetRestaurantById_withNonexistentRestaurantId_thenThrowNotFoundException() {
        // Arrange
        String nonExistentRestaurantId = "non-existent-restaurant-id";

        // Act and Assert
        webTestClient.get()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .* not found."));
    }

    @Test
    public void whenCreateRestaurantWithValidValues_thenReturnNewRestaurant(){
        String expectedName= "Wendy's";
        String expectedCountryName="Unites States";
        String expectedStreetName= "489th Street";
        String expectedProvinceName = "Ontario";
        String expectedCityName = "Toronto";
        String expectedPostalCode ="M9Z 4A1";

/*        RestaurantRequestModel restaurantRequestModel = new RestaurantRequestModel(
                expectedName,
                expectedCountryName,
                expectedStreetName,
                expectedProvinceName,
                expectedCityName,
                expectedPostalCode);*/


        Restaurant restaurant = new Restaurant(expectedName,expectedCountryName,expectedStreetName,expectedCityName,expectedProvinceName,expectedPostalCode);
        RestaurantRequestModel restaurantRequestModel = new RestaurantRequestModel(expectedName,expectedCountryName,expectedStreetName,expectedCityName,expectedProvinceName,expectedPostalCode);


        webTestClient.post()
                .uri(BASE_URI_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(restaurantRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                //.jsonPath("$.restaurantId").isNotEmpty()
                .jsonPath("$.restaurantName").isEqualTo(expectedName)
                .jsonPath("$.countryName").isEqualTo(expectedCountryName)
                .jsonPath("$.streetName").isEqualTo(expectedStreetName)
                .jsonPath("$.cityName").isEqualTo(expectedCityName)
                .jsonPath("$.provinceName").isEqualTo(expectedProvinceName)
                .jsonPath("$.postalCode").isEqualTo(expectedPostalCode);


    }

/*    @Test
    public void whenUpdateRestaurantWithValidValues_thenReturnRestaurant(){
        String expectedName= "Wendy's";
        String expectedCountryName="Canada";
        String expectedStreetName= "489th Street";
        String expectedProvinceName = "Ontario";
        String expectedCityName = "Toronto";
        String expectedPostalCode ="M9Z 4A1";
        RestaurantRequestModel restaurantRequestModel = new RestaurantRequestModel(
                expectedName,
                expectedCountryName,
                expectedStreetName,
                expectedProvinceName,
                expectedCityName,
                expectedPostalCode);

        Address address = new Address(
                expectedCountryName,
                expectedStreetName,
                expectedCityName,
                expectedProvinceName,
                expectedPostalCode);

        Restaurant restaurant = new Restaurant(expectedName, address);

        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(restaurant)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.restaurantName").isEqualTo(expectedName)
                .jsonPath("$.address.countryName").isEqualTo(expectedCountryName)
                .jsonPath("$.address.streetName").isEqualTo(expectedStreetName)
                .jsonPath("$.address.cityName").isEqualTo(expectedCityName)
                .jsonPath("$.address.provinceName").isEqualTo(expectedProvinceName)
                .jsonPath("$.address.postalCode").isEqualTo(expectedPostalCode);

    }*/

    @Test
    public void whenUpdateRestaurantWithValidValues_thenReturnRestaurant(){
        String expectedName= "Wendy's21";
        String expectedCountryName="Canada";
        String expectedStreetName= "489th Street";
        String expectedProvinceName = "Ontario";
        String expectedCityName = "Toronto";
        String expectedPostalCode ="M9Z 4A1";
        RestaurantRequestModel requestModel = createRestaurantRequestModel(expectedName, expectedCountryName, expectedStreetName, expectedProvinceName, expectedCityName, expectedPostalCode);

        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.restaurantId").isEqualTo(VALID_RESTAURANT_ID)
                .jsonPath("$.restaurantName").isEqualTo(expectedName)
                .jsonPath("$.countryName").isEqualTo(expectedCountryName)
                .jsonPath("$.streetName").isEqualTo(expectedStreetName)
                .jsonPath("$.cityName").isEqualTo(expectedCityName)
                .jsonPath("$.provinceName").isEqualTo(expectedProvinceName)
                .jsonPath("$.postalCode").isEqualTo(expectedPostalCode);

    }


    @Test
    public void whenUpdateRestaurant_withNonExistentRestaurantId_thenThrowNotFoundException() {
        // Arrange
        String expectedName= "Wendy's";
        String expectedCountryName="Canada";
        String expectedStreetName= "489th Street";
        String expectedProvinceName = "Ontario";
        String expectedCityName = "Toronto";
        String expectedPostalCode ="M9Z 4A1";
        String nonExistentRestaurantId = "non-existent-restaurant-id";
        RestaurantRequestModel requestModel = createRestaurantRequestModel(expectedName, expectedCountryName, expectedStreetName, expectedProvinceName, expectedCityName, expectedPostalCode);


        // Act and Assert
        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId)
                .body(BodyInserters.fromValue(requestModel))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .* not found."));
    }


    @Test
    public void whenDeleteRestaurant_withValidRestaurantId_thenDeleteRestaurant() {
        // Arrange
        String restaurantId = VALID_RESTAURANT_ID;

        // Act
        webTestClient.delete()
                .uri(BASE_URI_RESTAURANTS + "/" + restaurantId)
                .exchange()
                .expectStatus().isNoContent();

        // Assert
        assertFalse(restaurantRepository.existsByRestaurantIdentifier_RestaurantId(restaurantId));
    }


    @Test
    public void whenDeleteRestaurant_withNonExistentRestaurantId_thenThrowNotFoundException() {
        String nonExistentRestaurantId = "non-existent-restaurant-id";

        webTestClient.delete()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .* not found."));
    }



    @Test
    public void whenGetMenusInRestaurantsById_withValidRestaurantIdAndMenuId_thenReturnMenu() {
        String existingRestaurantId = "f9824810-d4a8-11ed-afa1-0242ac120001";
        String existingMenuId = "f9824810-d4a8-11ed-afa1-0242ac120002";
        String existingTypeOfMenu = "Appetizers";

        webTestClient.get()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus/" + VALID_MENU_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseModel.class)
                .value((dto) -> {
                    assertNotNull(dto);
                    assertEquals(dto.getRestaurantId(), VALID_RESTAURANT_ID);
                    assertEquals(dto.getMenuId(), VALID_MENU_ID);
                    assertEquals(dto.getTypeOfMenu(), existingTypeOfMenu);
                });
    }

    @Test
    public void whenGetMenusInRestaurantsByField_withValidRestaurantIdAndTypeOfMenuQueryParams_thenReturnFilteredMenus() {
        String existingRestaurantId = "f9824810-d4a8-11ed-afa1-0242ac120001";
        String typeOfMenu = "Entrees";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus")
                        .queryParam("typeOfMenu", typeOfMenu)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MenuResponseModel.class)
                .value((menus) -> {
                    assertNotNull(menus);
                    if (!menus.isEmpty()) {
                        menus.forEach(menu -> {
                            assertEquals(menu.getRestaurantId(), VALID_RESTAURANT_ID);
                            assertEquals(menu.getTypeOfMenu(), typeOfMenu);
                        });
                    } else {
                        System.out.println("No menus found with the specified typeOfMenu");
                    }
                });
    }


    @Test
    public void whenGetMenusInRestaurantsByField_withValidRestaurantIdAndNoQueryParams_thenReturnAllMenus() {
        String existingRestaurantId = "f9824810-d4a8-11ed-afa1-0242ac120001";

        webTestClient.get()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MenuResponseModel.class)
                .value((menus) -> {
                    assertNotNull(menus);
                    assertFalse(menus.isEmpty());
                    menus.forEach(menu -> {
                        assertEquals(menu.getRestaurantId(), VALID_RESTAURANT_ID);
                    });
                });
    }


    @Test
    public void whenAddMenuToRestaurantWithValidId_WithValidMenuID_thenReturnNewMenu(){
        String newMenuId= "f9824810-d4a8-11ed-afa1-0242ac120002";
        String newTypeOfMenu = "Extras";
        String itemId;
        MenuIdentifier menuIdentifier  = new MenuIdentifier();
        itemIdentifier itemIdentifier = new itemIdentifier();
        itemId = itemIdentifier.getItemId();

        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        Menu menu = new Menu(menuIdentifier,newTypeOfMenu,items);

        MenuRequestModel menuRequestModel = createNewMenuRequestModel(VALID_RESTAURANT_ID,newMenuId,newTypeOfMenu );

        webTestClient.post()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID +"/" + "menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseModel.class)
                .value((dto) -> {
                    assertNotNull(dto);
                    assertEquals(dto.getRestaurantId(), VALID_RESTAURANT_ID);
                    assertEquals(dto.getMenuId(), newMenuId);
                    assertEquals(dto.getTypeOfMenu(), newTypeOfMenu);
                });
    }

    @Test
    public void whenUpdateMenuInExistingRestaurant_withValidMenuId_thenUpdateMenu() {
        String existingRestaurantId = "f9824810-d4a8-11ed-afa1-0242ac120001";
        String existingMenuId = "f9824810-d4a8-11ed-afa1-0242ac120002";
        String updatedTypeOfMenu = "Updated Extras";

        MenuRequestModel menuRequestModel = createNewMenuRequestModel(existingRestaurantId, existingMenuId, updatedTypeOfMenu);

        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" +VALID_RESTAURANT_ID + "/menus/" + VALID_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuResponseModel.class)
                .value((dto) -> {
                    assertNotNull(dto);
                    assertEquals(dto.getRestaurantId(), VALID_RESTAURANT_ID);
                    assertEquals(dto.getMenuId(), VALID_MENU_ID);
                    assertEquals(dto.getTypeOfMenu(), updatedTypeOfMenu);
                });
    }
    @Test
    public void whenDeleteMenuInRestaurant_withValidRestaurantId_andValidMenuId_thenDeleteMenu() {
        // Arrange
        String restaurantId = VALID_RESTAURANT_ID;
        String menuId = VALID_MENU_ID;

        // Act
        webTestClient.delete()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus/" + VALID_MENU_ID)
                .exchange()
                .expectStatus().isOk();

        // Assert
        // You can add an optional assertion to check if the menu is actually deleted, by attempting to fetch the menu and expect a NOT_FOUND status.
    }

    @Test
    public void whenDeleteMenuInRestaurant_withInvalidRestaurantId_thenThrowNotFoundException() {
        // Arrange
        String invalidRestaurantId = "invalid-restaurant-id";
        String menuId = VALID_MENU_ID;

        // Act
        webTestClient.delete()
                .uri(BASE_URI_RESTAURANTS + "/" + invalidRestaurantId + "/menus/" + menuId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .*not found."));
    }

    @Test
    public void whenDeleteMenuInRestaurant_withValidRestaurantId_andInvalidMenuId_thenDoNothing() {
        // Arrange
        String restaurantId = VALID_RESTAURANT_ID;
        String invalidMenuId = "invalid-menu-id";

        // Act
        webTestClient.delete()
                .uri(BASE_URI_RESTAURANTS + "/" + restaurantId + "/menus/" + invalidMenuId)
                .exchange()
                .expectStatus().isOk();
    }



/*    @Test
    public void whenMenuIdIsDuplicateForNewMenu_thenReturnDuplicateMenuIdentifierException(){

        String DUPLICATE_MENU_ID = VALID_MENU_ID;
        Items items1 = new Items("Cheeseburger","A delicious burger with cheese",9.99);
        List<Items> items = new ArrayList<>(Arrays.asList(items1));

        MenuRequestModel menuRequestModel = new MenuRequestModel(DUPLICATE_MENU_ID,VALID_RESTAURANT_ID,VALID_TYPE_OF_MENU,items);
    }*/

    private MenuRequestModel createNewMenuRequestModel(String restaurantId, String menuId,String typeOfMenu) {
        Items one = new Items("Cake","goood Cake",6.49);
        Items two = new Items("Water","Spring Water",1.99);

        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        MenuRequestModel menuRequestModel = MenuRequestModel.builder()
                .menuId(menuId)
                .restaurantId(restaurantId)
                .typeOfMenu(typeOfMenu)
                .items(items)
                .build();
        return menuRequestModel;
    }

    private RestaurantRequestModel createRestaurantRequestModel(String restaurantName, String countryName, String streetName, String provinceName, String cityName, String postalCode) {
        RestaurantRequestModel restaurantRequestModel = RestaurantRequestModel.builder()
                .restaurantName(restaurantName)
                .countryName(countryName)
                .streetName(streetName)
                .provinceName(provinceName)
                .cityName(cityName)
                .postalCode(postalCode)
                .build();
        return restaurantRequestModel;

    }

    @Test
    public void whenAddMenuToRestaurantWithInvalidId_thenReturnNotFoundException() {
        String nonExistentRestaurantId = "non-existent-restaurant-id";
        String newMenuId = "f9824810-d4a8-11ed-afa1-0242ac120002";
        String newTypeOfMenu = "Extras";

        MenuRequestModel menuRequestModel = createNewMenuRequestModel(nonExistentRestaurantId, newMenuId, newTypeOfMenu);

        webTestClient.post()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Unknown restaurant id provided.*"));
    }
    @Test
    public void whenGetMenusInRestaurantsByField_withNonExistentRestaurantId_thenThrowNotFoundException() {
        String nonExistentRestaurantId = "non-existent-restaurant-id";

        webTestClient.get()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId + "/menus")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .* not found."));
    }

    @Test
    public void whenUpdateMenuInRestaurant_withNonExistentRestaurantId_thenThrowNotFoundException() {
        String nonExistentRestaurantId = "non-existent-restaurant-id";
        String existingMenuId = VALID_MENU_ID;
        MenuRequestModel menuRequestModel = createNewMenuRequestModel(nonExistentRestaurantId, existingMenuId, "Entrees");

        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" + nonExistentRestaurantId + "/menus/" + existingMenuId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant with id: .* not found."));
    }

    @Test
    public void whenCreateMenuInRestaurant_withInvalidMenuId_thenTHrowInvalidMenuId() {
        String invalidMenuId = VALID_MENU_ID + "1";
        String newTypeOfMenu = "Entrees";
        MenuRequestModel menuRequestModel = createNewMenuRequestModel(VALID_RESTAURANT_ID, invalidMenuId, newTypeOfMenu);

        webTestClient.post()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Invalid menu id provided.*"));

    }

    @Test
    public void whenUpdateMenuInRestaurantByInvalidMenuId_thenThrowInvalidMenuId() {
        String invalidMenuId = VALID_MENU_ID + "1";
        String newTypeOfMenu = "Entrees";
        MenuRequestModel menuRequestModel = createNewMenuRequestModel(VALID_RESTAURANT_ID, invalidMenuId, newTypeOfMenu);

        webTestClient.put()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus/" + invalidMenuId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Invalid menu id provided.*"));
    }


/*        @Test
        @Transactional
        public void testAddMenuToRestaurantDuplicateMenuIdentifierException() {
            String expectedName= "Wendy's21";
            String expectedCountryName="Canada";
            String expectedStreetName= "489th Street";
            String expectedProvinceName = "Ontario";
            String expectedCityName = "Toronto";
            String expectedPostalCode ="M9Z 4A1";

            String restaurantId = "testRestaurantId";
            String menuId = "testMenuId";
            MenuRequestModel menuRequestModel = new MenuRequestModel();
            menuRequestModel.setMenuId(menuId);

            // Create a restaurant and save it in the repository
            Restaurant restaurant = new Restaurant(expectedName);
            restaurant.setRestaurantIdentifier(new RestaurantIdentifier(restaurantId));
            restaurantRepository.save(restaurant);

            // Create a menu and save it in the repository
            Menu menu = new Menu();
            menu.setMenuIdentifier(new MenuIdentifier(menuId));
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);

            // Call addMenuToRestaurant and expect a DuplicateMenuIdentifierException to be thrown
            assertThrows(DuplicateMenuIdentifierException.class, () -> {
                menuService.addMenuToRestaurant(menuRequestModel, restaurantId);
            });
        }
    }*/




/*    @Test
    public void whenAddMenuToRestaurantWithValidId_WithDuplicateMenuID_thenReturnDuplicateMenuIdentifierException() {
        String existingMenuId = "f9824810-d4a8-11ed-afa1-0242ac120001";
        String newTypeOfMenu = "Entrees";

        MenuRequestModel menuRequestModel = createNewMenuRequestModel(VALID_RESTAURANT_ID, VALID_MENU_ID, newTypeOfMenu);

        webTestClient.post()
                .uri(BASE_URI_RESTAURANTS + "/" + VALID_RESTAURANT_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(menuRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Restaurant already has a menu with the menu id.*"));
    }*/







}


