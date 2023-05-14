package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fooddelivery.apigateway.presentationLayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.fooddelivery.apigateway.utils.HttpErrorInfo;
import com.fooddelivery.apigateway.utils.exceptions.InvalidInputException;
import com.fooddelivery.apigateway.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RestaurantServiceClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    private String restaurantId = "f1740a89-cb47-4c96-888e-ff96708db4d8";
    private String baseUrl = "http://localhost:8080/api/v1/restaurants";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        restaurantServiceClient = new RestaurantServiceClient(restTemplate, new ObjectMapper(), "localhost", "8080");
    }

    @Test
    public void getAllRestaurants()  {


        String url = baseUrl;

        RestaurantResponseModel restaurantResponseModel1 = new RestaurantResponseModel("1", "testRestaurant1", "country1", "21St", "quebec", "montreal", "A2C 4O9");
        RestaurantResponseModel restaurantResponseModel2 = new RestaurantResponseModel("2", "testRestaurant2", "country2", "22St", "quebec", "montreal", "A2C 4O9");

        RestaurantResponseModel[] restaurantResponseModels = new RestaurantResponseModel[]{restaurantResponseModel1, restaurantResponseModel2};
        when(restTemplate.getForObject(url, RestaurantResponseModel[].class)).thenReturn(restaurantResponseModels);

        RestaurantResponseModel[] restaurants = restaurantServiceClient.getAllRestaurantsAggregate();

        assertEquals(restaurantResponseModels.length, restaurants.length);
        for (int i = 0; i < restaurantResponseModels.length; i++) {
            assertEquals(restaurantResponseModels[i], restaurants[i]);
        }
        verify(restTemplate, times(1)).getForObject(url, RestaurantResponseModel[].class);
    }

    @Test
    public void getRestaurantById() {
        RestaurantMenuResponseModel restaurantMenuResponseModel = new RestaurantMenuResponseModel(restaurantId, "testRestaurant1", "menuId", "testMenu" );
    when(restTemplate.getForObject(baseUrl + "/" + restaurantId, RestaurantMenuResponseModel.class)).thenReturn(restaurantMenuResponseModel);
    RestaurantMenuResponseModel restaurant = restaurantServiceClient.getRestaurantAggregate(restaurantId);
    assertEquals(restaurant.getRestaurantId(),restaurantId);
    assertEquals(restaurant.getRestaurantName(), "testRestaurant1");
    assertEquals(restaurant.getMenuId(), "menuId");
    assertEquals(restaurant.getTypeOfMenu(), "testMenu");
    verify(restTemplate, times(1)).getForObject(baseUrl + "/" + restaurantId, RestaurantMenuResponseModel.class);
    }

    @Test
    public void addRestaurant()  {
        RestaurantRequestModel restaurantRequestModel =  RestaurantRequestModel.builder()
                .restaurantName("testRestaurant1")
                .countryName("country1")
                .streetName("21St")
                .provinceName("quebec")
                .cityName("montreal")
                .postalCode("A2C 4O9")
                .build();
        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel("1", "testRestaurant1", "country1", "21St", "quebec", "montreal", "A2C 4O9");
        when(restTemplate.postForObject(baseUrl, restaurantRequestModel, RestaurantResponseModel.class)).thenReturn(restaurantResponseModel);
        RestaurantResponseModel restaurant = restaurantServiceClient.addRestaurantAggregate(restaurantRequestModel);
        assertEquals(restaurant.getRestaurantId(), "1");
        assertEquals(restaurant.getRestaurantName(), "testRestaurant1");
        assertEquals(restaurant.getCountryName(), "country1");
        assertEquals(restaurant.getStreetName(), "21St");
        assertEquals(restaurant.getProvinceName(), "quebec");
        assertEquals(restaurant.getCityName(), "montreal");
        assertEquals(restaurant.getPostalCode(), "A2C 4O9");
        verify(restTemplate, times(1)).postForObject(baseUrl, restaurantRequestModel, RestaurantResponseModel.class);

    }
    @Test
    public void testGetRestaurant_NotFound() {
        String restaurantId = "not-foundId";
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND,"/api/v1/restaurants/" + restaurantId, "Not Found");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String errorInfoJson ="";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        }
        catch (JsonProcessingException e) {

            fail("Failed to serialize errorInfo:" + e.getMessage());
            }

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        when(restTemplate.getForObject(baseUrl + "/" + restaurantId, RestaurantMenuResponseModel.class)).thenThrow(ex);
        Exception exception = assertThrows(NotFoundException.class, () ->
                restaurantServiceClient.getRestaurantAggregate(restaurantId));
        assertTrue(exception.getMessage().contains("Not Found"));

        }

        @Test
    public void testUpdateRestaurant_NotFound()throws JsonProcessingException {
        String restaurantId = "not-foundId";
        RestaurantRequestModel restaurantRequestModel =  RestaurantRequestModel.builder()
                .restaurantName("testRestaurant1")
                .countryName("country1")
                .streetName("21St")
                .provinceName("quebec")
                .cityName("montreal")
                .postalCode("A2C 4O9")
                .build();
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND,"/api/v1/restaurants/" + restaurantId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

            doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

            Exception exception = assertThrows(NotFoundException.class, () ->
                    restaurantServiceClient.updateRestaurantAggregate(restaurantId, restaurantRequestModel));
            assertTrue(exception.getMessage().contains("Not Found"));

    }
    @Test
    public void testUpdateRestaurant_InvalidInput()throws JsonProcessingException {
        String restaurantId = "not-foundId";
        RestaurantRequestModel restaurantRequestModel =  RestaurantRequestModel.builder()
                .restaurantName("testRestaurant1")
                .countryName("country1")
                .streetName("21St")
                .provinceName("quebec")
                .cityName("montreal")
                .postalCode("A2C 4O9")
                .build();
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY,"/api/v1/restaurants/" + restaurantId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                restaurantServiceClient.updateRestaurantAggregate(restaurantId, restaurantRequestModel));
        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void testDeleteRestaurant_NotFoundException()throws JsonProcessingException {
        String restaurantId = "not-foundId";
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND,"/api/v1/restaurants/" + restaurantId, "Not Found");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());
        Exception exception = assertThrows(NotFoundException.class, () ->
                restaurantServiceClient.deleteRestaurant(restaurantId));
        assertTrue(exception.getMessage().contains("Not Found"));

    }

    @Test
    public void testDeleteRestaurant_InvalidInput()throws JsonProcessingException {
        String restaurantId = "not-foundId";
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY,"/api/v1/restaurants/" + restaurantId, "Unprocessable Entity");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());
        Exception exception = assertThrows(InvalidInputException.class, () ->
                restaurantServiceClient.deleteRestaurant(restaurantId));
        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }
    @Test
    public void testGetMenuByMenuId_ValidId() throws JsonProcessingException {
        String restaurantId = "validRestaurantId";
        String menuId = "validMenuId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));

        MenuResponseModel expectedMenuResponseModel = new MenuResponseModel();
        expectedMenuResponseModel.setRestaurantId(restaurantId);
        expectedMenuResponseModel.setMenuId(menuId);
        expectedMenuResponseModel.setTypeOfMenu("Dinner");
        expectedMenuResponseModel.setItems(items);


        when(restTemplate.getForObject(url, MenuResponseModel.class)).thenReturn(expectedMenuResponseModel);

        MenuResponseModel actualMenuResponseModel = restaurantServiceClient.getMenuByMenuId(restaurantId, menuId);

        assertEquals(expectedMenuResponseModel, actualMenuResponseModel);
    }

    @Test
    public void testGetMenuByMenuId_NotFound() throws JsonProcessingException {
        // Given
        String restaurantId = "not-foundId";
        String menuId = "not-foundId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, url, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        when(restTemplate.getForObject(url, MenuResponseModel.class)).thenThrow(ex);

        // When & Then
        Exception exception = assertThrows(NotFoundException.class, () ->
                restaurantServiceClient.getMenuByMenuId(restaurantId, menuId));
        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testAddMenuToRestaurant_ValidRequest() {
        // Given
        String restaurantId = "validRestaurantId";
        String url = baseUrl + "/" + restaurantId + "/menus";

        MenuRequestModel newMenu = new MenuRequestModel();


        MenuResponseModel expectedMenuResponseModel = new MenuResponseModel();


        when(restTemplate.postForObject(url, newMenu, MenuResponseModel.class))
                .thenReturn(expectedMenuResponseModel);


        MenuResponseModel actualMenuResponseModel = restaurantServiceClient.addMenuToRestaurant(restaurantId, newMenu);

        assertEquals(expectedMenuResponseModel, actualMenuResponseModel);
    }

    @Test
    public void testAddMenuToRestaurant_ThrowsException() {
        // Given
        String restaurantId = "validRestaurantId";
        String url = baseUrl + "/" + restaurantId + "/menus";

        MenuRequestModel newMenu = new MenuRequestModel();
        // set the fields of newMenu

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request",
                HttpHeaders.EMPTY, null, null);

        when(restTemplate.postForObject(url, newMenu, MenuResponseModel.class))
                .thenThrow(ex);

        // When & Then
        assertThrows(HttpClientErrorException.class, () ->
                restaurantServiceClient.addMenuToRestaurant(restaurantId, newMenu));
    }

    @Test
    public void testModifyMenuInRestaurant_ValidRequest() {
        // Given
        String restaurantId = "validRestaurantId";
        String menuId = "validMenuId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        MenuRequestModel menuRequestModel = new MenuRequestModel();
        // set the fields of menuRequestModel

        when(restTemplate.execute(eq(url), eq(HttpMethod.PUT), any(), any())).thenReturn(null);

        // When
        restaurantServiceClient.modifyMenuInRestaurant(restaurantId, menuId, menuRequestModel);

        // Then
        // no exception is thrown
    }


    @Test
    public void testModifyMenuInRestaurant_ThrowsException() {
        // Given
        String restaurantId = "validRestaurantId";
        String menuId = "validMenuId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        MenuRequestModel menuRequestModel = new MenuRequestModel();
        // set the fields of menuRequestModel

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request",
                HttpHeaders.EMPTY, null, null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        // When & Then
        assertThrows(HttpClientErrorException.class, () ->
                restaurantServiceClient.modifyMenuInRestaurant(restaurantId, menuId, menuRequestModel));
    }

    @Test
    public void testDeleteMenuInRestaurant_ValidRequest() {
        // Given
        String restaurantId = "validRestaurantId";
        String menuId = "validMenuId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        when(restTemplate.execute(eq(url), eq(HttpMethod.DELETE), any(), any())).thenReturn(null);

        // When
        restaurantServiceClient.deleteMenuInRestaurant(restaurantId, menuId);

        // Then
        // no exception is thrown
    }

    @Test
    public void testDeleteMenuInRestaurant_ThrowsException() {
        // Given
        String restaurantId = "validRestaurantId";
        String menuId = "validMenuId";
        String url = baseUrl + "/" + restaurantId + "/menus/" + menuId;

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request",
                HttpHeaders.EMPTY, null, null);

        when(restTemplate.execute(anyString(), eq(HttpMethod.DELETE), any(), any())).thenThrow(ex);

        // When & Then
        assertThrows(HttpClientErrorException.class, () ->
                restaurantServiceClient.deleteMenuInRestaurant(restaurantId, menuId));
    }

    @Test
    public void requestCallback_SetsCorrectHeadersAndBody() throws Exception {
        // Arrange
        RestaurantRequestModel restaurantRequestModel = new RestaurantRequestModel();
        // set the fields of restaurantRequestModel

        // Mocking ClientHttpRequest
        ClientHttpRequest clientHttpRequest = mock(ClientHttpRequest.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(clientHttpRequest.getBody()).thenReturn(outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        when(clientHttpRequest.getHeaders()).thenReturn(httpHeaders);

        // Access private method via reflection
        Method requestCallbackMethod = RestaurantServiceClient.class.getDeclaredMethod("requestCallback", RestaurantRequestModel.class);
        requestCallbackMethod.setAccessible(true);

        // Act
        RequestCallback requestCallback = (RequestCallback) requestCallbackMethod.invoke(restaurantServiceClient, restaurantRequestModel);
        requestCallback.doWithRequest(clientHttpRequest);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writeValueAsString(restaurantRequestModel);
        String actualBody = outputStream.toString();
        assertEquals(expectedBody, actualBody);

        assertEquals(MediaType.APPLICATION_JSON_VALUE, httpHeaders.getContentType().toString());
        assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));
    }

    @Test
    public void requestCallbackMenu_SetsCorrectHeadersAndBody() throws Exception {
        // Arrange
        MenuRequestModel menuRequestModel = new MenuRequestModel();
        // set the fields of menuRequestModel

        // Mocking ClientHttpRequest
        ClientHttpRequest clientHttpRequest = mock(ClientHttpRequest.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(clientHttpRequest.getBody()).thenReturn(outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        when(clientHttpRequest.getHeaders()).thenReturn(httpHeaders);

        // Access private method via reflection
        Method requestCallbackMethod = RestaurantServiceClient.class.getDeclaredMethod("requestCallbackMenu", MenuRequestModel.class);
        requestCallbackMethod.setAccessible(true);

        // Act
        RequestCallback requestCallback = (RequestCallback) requestCallbackMethod.invoke(restaurantServiceClient, menuRequestModel);
        requestCallback.doWithRequest(clientHttpRequest);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writeValueAsString(menuRequestModel);
        String actualBody = outputStream.toString();
        assertEquals(expectedBody, actualBody);

        assertEquals(MediaType.APPLICATION_JSON_VALUE, httpHeaders.getContentType().toString());
        assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));
    }




}