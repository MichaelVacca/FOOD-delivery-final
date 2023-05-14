package com.fooddelivery.apigateway.presentationLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.businessLayer.RestaurantService;
import com.fooddelivery.apigateway.domainClientLayer.RestaurantServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestaurantControllerTest {
    @LocalServerPort
    private int port;
    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    RestTemplate restTemplate;

    @MockBean
    RestaurantServiceClient restaurantServiceClient;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate1;



    private ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl = "http://localhost:";
    @BeforeEach
    public void setup() {
        baseUrl = baseUrl + port + "/api/v1/restaurants";
    }


    @Test
    public void getAllRestaurantsAggregate() throws Exception{
        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel("1", "restaurant1", "country1", "street1", "province1", "city1", "postalCode1");
        RestaurantResponseModel restaurantResponseModel1 = new RestaurantResponseModel("2", "restaurant2", "country2", "street2", "province2", "city2", "postalCode2");
        RestaurantResponseModel[] restaurantResponseModels = new RestaurantResponseModel[]{restaurantResponseModel, restaurantResponseModel1};

        when(restaurantService.getAllRestaurantsAggregate()).thenReturn(restaurantResponseModels);

        mockMvc.perform(get("/api/v1/restaurants")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(restaurantService, times(1)).getAllRestaurantsAggregate();

    }

    @Test
    public void getRestaurantById() throws Exception{
        String restaurantId = "1";
        RestaurantMenuResponseModel restaurantMenuResponseModel = new RestaurantMenuResponseModel("1", "menu1", "typeOfMenu1", "items1");
        when(restaurantService.getRestaurantAggregate(restaurantId)).thenReturn(restaurantMenuResponseModel);
        mockMvc.perform(get("/api/v1/restaurants/"+restaurantId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(restaurantService, times(1)).getRestaurantAggregate(restaurantId);
    }

    @Test
    public void addRestaurant() throws Exception{
        String restaurantId = "1";
        RestaurantRequestModel restaurantRequestModel = RestaurantRequestModel.builder()
                .restaurantName("restaurant1")
                .countryName("country1")
                .streetName("street1")
                .provinceName("province1")
                .cityName("city1")
                .postalCode("postalCode1")
                .build();

        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel("1", "restaurant1", "country1", "street1", "province1", "city1", "postalCode1");
        when(restaurantService.addRestaurant(restaurantRequestModel)).thenReturn(restaurantResponseModel);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequestModel)))
                .andExpect(status().isCreated())
                .andReturn();

        RestaurantResponseModel restaurantResponseModel1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RestaurantResponseModel.class);
        assertEquals(restaurantRequestModel.getRestaurantName(), restaurantResponseModel1.getRestaurantName());
        assertEquals(restaurantRequestModel.getCountryName(), restaurantResponseModel1.getCountryName());
        assertEquals(restaurantRequestModel.getStreetName(), restaurantResponseModel1.getStreetName());
        assertEquals(restaurantRequestModel.getProvinceName(), restaurantResponseModel1.getProvinceName());
        assertEquals(restaurantRequestModel.getCityName(), restaurantResponseModel1.getCityName());
        assertEquals(restaurantRequestModel.getPostalCode(), restaurantResponseModel1.getPostalCode());
    }

    @Test
    public void updateRestaurant() throws Exception{
        String restaurantId = "1";
        RestaurantRequestModel restaurantRequestModel = RestaurantRequestModel.builder()
                .restaurantName("restaurant1")
                .countryName("country1")
                .streetName("street1")
                .provinceName("province1")
                .cityName("city1")
                .postalCode("postalCode1")
                .build();
        doNothing().when(restaurantService).updateRestaurantAggregate(restaurantId, restaurantRequestModel);
        mockMvc.perform(put("/api/v1/restaurants/"+restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequestModel)))
                .andExpect(status().isNoContent());

        verify(restaurantService, times(1)).updateRestaurantAggregate(restaurantId, restaurantRequestModel);
    }

    @Test
    public void deleteRestaurant() throws Exception{
        String restaurantId = "1";
        doNothing().when(restaurantService).deleteRestaurantAggregate(restaurantId);
        mockMvc.perform(delete("/api/v1/restaurants/"+restaurantId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(restaurantService, times(1)).deleteRestaurantAggregate(restaurantId);
    }

    @Test
    public void getMenuById() throws Exception {
        String restaurantId = "1";
        String menuId = "1";
        MenuResponseModel menuResponseModel = new MenuResponseModel();
        when(restaurantService.getMenuByMenuId(restaurantId, menuId)).thenReturn(menuResponseModel);
        mockMvc.perform(get("/api/v1/restaurants/"+restaurantId+"/menus/"+menuId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(restaurantService, times(1)).getMenuByMenuId(restaurantId, menuId);
    }

    @Test
    public void addMenuToRestaurant() throws Exception {
        String restaurantId = "1";

        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        MenuRequestModel menuRequestModel = new MenuRequestModel("1", "menu1", "typeOfMenu1");
        MenuResponseModel menuResponseModel = new MenuResponseModel();
        //when(restaurantService.addMenuToRestaurant(restaurantId, menuRequestModel)).thenReturn(menuResponseModel);
        when(restaurantService.addMenuToRestaurant(eq(restaurantId), any(MenuRequestModel.class))).thenReturn(menuResponseModel);



        MvcResult mvcResult = mockMvc.perform(post("/api/v1/restaurants/" +restaurantId+"/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequestModel)))
                .andExpect(status().isCreated())
                .andReturn();

        MenuResponseModel response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MenuResponseModel.class);
/*            assertEquals(menuRequestModel.getMenuId(), response.getMenuId());
                assertEquals(menuRequestModel.getRestaurantId(), response.getRestaurantId());
                assertEquals(menuRequestModel.getTypeOfMenu(), response.getTypeOfMenu());
                assertEquals(menuRequestModel.getItems(), response.getItems());*/
    }

    @Test
    public void updateMenuInRestaurantByMenuId() throws Exception {
        String restaurantId = "1";
        String menuId = "1";
        Items one = new Items("Burger","Grilled hamburger",6.99);
        Items two = new Items("French Fries","Grilled hamburger",3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one,two));

        MenuRequestModel menuRequestModel = new MenuRequestModel("1", "menu1", "typeOfMenu1");
        doNothing().when(restaurantService).updateMenuInRestaurantByMenuId(any(MenuRequestModel.class), eq(restaurantId), eq(menuId));
        mockMvc.perform(put("/api/v1/restaurants/"+restaurantId+"/menus/"+menuId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequestModel)))
                .andExpect(status().isNoContent());
        verify(restaurantService, times(1)).updateMenuInRestaurantByMenuId(any(MenuRequestModel.class), eq(restaurantId), eq(menuId));
    }


    @Test
    public void deleteMenuFromRestaurant() throws Exception {
        String restaurantId = "1";
        String menuId = "1";
        doNothing().when(restaurantService).deleteMenuFromRestaurant(restaurantId, menuId);
        mockMvc.perform(delete("/api/v1/restaurants/"+restaurantId+"/menus/"+menuId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(restaurantService, times(1)).deleteMenuFromRestaurant(restaurantId, menuId);
    }



}