package com.fooddelivery.apigateway.presentationLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.domainClientLayer.OrderServiceClient;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {
    /*@LocalServerPort
    private int port;
*//*    @MockBean
    private OrderService orderService;*//*

    @Autowired
    RestTemplate restTemplate;

    @MockBean
    OrderServiceClient orderserviceClient;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate1;


    private ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl = "http://localhost:";
    private String baseUrlClients = "http://localhost:";

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl + port + "/orders";
        baseUrlClients = baseUrlClients + port + "/clients/";
    }

    @Test
    public void getAllOrders() throws Exception {
        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));
        OrderResponseModel order1 = new OrderResponseModel("orderId1", "client1", "restoId1", "menuId1", "driverId1",
                "John", "Doe", "testUser", "test@email.com", items, "resto1", "typeOfMenu1", OrderStatus.MAKING_ORDER, 20.0, "10 minutes", "2023-01-01");
        OrderResponseModel order2 = new OrderResponseModel("orderId2", "client2", "restoId2", "menuId2", "driverId2",
                "jane", "Doe", "testUser2", "test@email.com2", items, "resto2", "typeOfMenu2", OrderStatus.MAKING_ORDER, 10.0, "20 minutes", "2023-02-02");
        OrderResponseModel[] orders = new OrderResponseModel[]{order1, order2};


        when(orderserviceClient.getAllOrdersAggregate()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(orderserviceClient, times(1)).getAllOrdersAggregate();
    }

    @Test
    public void getOrderById() throws Exception {
        String orderId = "orderId1";

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));

        OrderResponseModel order1 = new OrderResponseModel("orderId1", "client1", "restoId1", "menuId1", "driverId1",
                "John", "Doe", "testUser", "test@email.com", items, "resto1", "typeOfMenu1", OrderStatus.MAKING_ORDER, 20.0, "10 minutes", "2023-01-01");


        when(orderserviceClient.getOrderById(orderId)).thenReturn(order1);
        mockMvc.perform(get("/api/v1/orders/" + orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(orderserviceClient, times(1)).getOrderById(orderId);
    }


    @Test
    public void deleteOrder()throws Exception {
        String orderId = "orderId1";
        doNothing().when(orderserviceClient).deleteOrder(orderId);
        mockMvc.perform(delete("/api/v1/orders/" + orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(orderserviceClient, times(1)).deleteOrder(orderId);
    }*/

}