package com.fooddelivery.ordersservice.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.ordersservice.datalayer.Items;
import com.fooddelivery.ordersservice.datalayer.OrderStatus;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.MenuRequestModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.MenuResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.RestaurantMenuResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.RestaurantResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.client.ClientResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver.DeliveryDriverResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ClientOrderControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenFieldsArevalid_thenReturnOrderResponseModel() throws JsonProcessingException, URISyntaxException {
/*        String diverId = "bfec8718-92f3-410f-9343-d4dc6b763f10";
        String restaurantId = "055b4a20-d29d-46ce-bb46-2c15b8ed6526";
        String menuId = "8fb3d5f0-2ceb-4921-a978-736bb4d278b7";*/



        Items one = new Items("Burger", "Grilled hamburger", 6.99);
        Items two = new Items("French Fries", "Grilled hamburger", 3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one, two));

        OrderRequestModel orderRequestModel = OrderRequestModel.builder()
                .restaurantId("055b4a20-d29d-46ce-bb46-2c15b8ed6526")
                .menuId("8fb3d5f0-2ceb-4921-a978-736bb4d278b7")
                .totalPrice(100.0)
                .deliveryDriverId("bfec8718-92f3-410f-9343-d4dc6b763f10")
                .orderStatus(OrderStatus.MAKING_ORDER)
                .items(items)
                .estimatedDeliveryTime("10 minutes")
                .orderDate(LocalDate.of(2022, 12, 12))
                .build();
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "dgalletley0", "b33p3d4", "20", "jkunzelmann0@live.com", "514-123-1234", "canada", "test street", "test city", "test province", "test postcode");

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7002/api/v1/clients/0a1491af-551b-4b40-87c4-ff268d187b9a")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(clientResponseModel)));

        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel("bfec8718-92f3-410f-9343-d4dc6b763f10", "Osborne", "Ivanyukov", "testDateOfBirth", "testDescription", "testEmployeeSince", "canada", "test street", "test city", "test province", "test postcode");

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7003/api/v1/deliveryDrivers/bfec8718-92f3-410f-9343-d4dc6b763f10")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(deliveryDriverResponseModel)));

        RestaurantMenuResponseModel restaurantMenuResponseModel = new RestaurantMenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "SavoryStreet", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", "Appetizers");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/restaurants/055b4a20-d29d-46ce-bb46-2c15b8ed6526/menus/8fb3d5f0-2ceb-4921-a978-736bb4d278b7")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(restaurantMenuResponseModel)));

        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526","testName","testCountry", "test street", "test city", "test province", "test postcode");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/restaurants/055b4a20-d29d-46ce-bb46-2c15b8ed6526")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(restaurantResponseModel)));



       MenuResponseModel menuResponseModel = new MenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526","8fb3d5f0-2ceb-4921-a978-736bb4d278b7","Appetizers",items,20.0);
        /*mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/restaurants/055b4a20-d29d-46ce-bb46-2c15b8ed6526/menus/8fb3d5f0-2ceb-4921-a978-736bb4d278b7")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(menuResponseModel)));*/

        MenuRequestModel menuRequestModel = MenuRequestModel.builder()
                .restaurantId("055b4a20-d29d-46ce-bb46-2c15b8ed6526")
                .menuId("8fb3d5f0-2ceb-4921-a978-736bb4d278b7")
                .typeOfMenu("Appetizers")
                .items(items)
                .totalPrice(100.0)
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/restaurants/055b4a20-d29d-46ce-bb46-2c15b8ed6526/menus/8fb3d5f0-2ceb-4921-a978-736bb4d278b7")))
                .andExpect(method(HttpMethod.PUT))
                        .andExpect(content().json(mapper.writeValueAsString(menuRequestModel)))
                        .andRespond(withStatus(HttpStatus.OK));



        String url = "api/v1/clients/0a1491af-551b-4b40-87c4-ff268d187b9a/orders";
    webTestClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(orderRequestModel)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(OrderResponseModel.class)
            .value((orderResponseModel) -> {
                assertNotNull(orderResponseModel);
                assertNotNull(orderResponseModel.getOrderId());
                assertEquals(orderRequestModel.getRestaurantId(), orderResponseModel.getRestaurantId());
                assertEquals(clientId, orderResponseModel.getClientId());
                assertEquals(orderRequestModel.getMenuId(), orderResponseModel.getMenuId());
                assertEquals(orderRequestModel.getDeliveryDriverId(), orderResponseModel.getDeliveryDriverId());
                assertEquals(deliveryDriverResponseModel.getFirstName(), orderResponseModel.getDriverFirstName());
                assertEquals(deliveryDriverResponseModel.getLastName(), orderResponseModel.getDriverLastName());
                assertEquals(clientResponseModel.getUserName(), orderResponseModel.getClientUsername());
                assertEquals(clientResponseModel.getEmailAddress(), orderResponseModel.getClientEmail());
                assertEquals(orderRequestModel.getItems(), orderResponseModel.getItems());
                assertEquals(restaurantResponseModel.getRestaurantName(), orderResponseModel.getRestaurantName());
                assertEquals(restaurantMenuResponseModel.getTypeOfMenu(), orderResponseModel.getTypeOfMenu());
                assertEquals(orderRequestModel.getEstimatedDeliveryTime(), orderResponseModel.getEstimatedDeliveryTime());
                assertEquals(orderRequestModel.getOrderDate(), orderResponseModel.getOrderDate());
            });
    }

}