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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientOrderControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    RestTemplate restTemplate;

    @MockBean
    OrderServiceClient orderServiceClient;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate1;


    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrlClients = "http://localhost:";

    @BeforeEach
    public void setUp() {
        baseUrlClients = baseUrlClients + port + "api/v1/clients/";
    }

    @Test
    public void processClientOrder() throws Exception {
        String clientId = "c3540a89-cb47-4c96-888e-ff96708db4d8";
        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));
        OrderRequestModel orderRequestModel = OrderRequestModel.builder()
                .restaurantId("restoId1")
                .menuId("menuId1")
                .totalPrice(20.0)
                .deliveryDriverId("driverId1")
                .orderStatus(OrderStatus.MAKING_ORDER)
                .items(items)
                .estimatedDeliveryTime("10 minutes")
                .orderDate("2023-01-01")
                .build();

        OrderResponseModel orderResponseModel = new OrderResponseModel("orderId1", clientId, "restoId1", "menuId1", "driverId1",
                "John", "Doe", "testUser", "test@email.com", items, "resto1", "typeOfMenu1", OrderStatus.MAKING_ORDER, 20.0, "10 minutes", "2023-01-01");

        when(orderServiceClient.processClientOrders(orderRequestModel,clientId)).thenReturn(orderResponseModel);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/clients/" + clientId + "/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestModel)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);


        OrderResponseModel actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderResponseModel.class);

        assertEquals(orderRequestModel.getRestaurantId(), actual.getRestaurantId());
        assertEquals(orderRequestModel.getMenuId(), actual.getMenuId());
        assertEquals(orderRequestModel.getTotalPrice(), actual.getFinalPrice());
        assertEquals(orderRequestModel.getDeliveryDriverId(), actual.getDeliveryDriverId());
        assertEquals(orderRequestModel.getOrderStatus(), actual.getOrderStatus());
        assertEquals(orderRequestModel.getItems(), actual.getItems());
        assertEquals(orderRequestModel.getEstimatedDeliveryTime(), actual.getEstimatedDeliveryTime());
        assertEquals(orderRequestModel.getOrderDate(), actual.getOrderDate());

    }

    @Test
    public void updateClientOrder() throws Exception {
        String clientId = "1";
        String orderId = "orderId1";
        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));
        OrderRequestModel orderRequestModel = OrderRequestModel.builder()
                .restaurantId("restoId1")
                .menuId("menuId1")
                .totalPrice(20.0)
                .deliveryDriverId("driverId1")
                .orderStatus(OrderStatus.MAKING_ORDER)
                .items(items)
                .estimatedDeliveryTime("10 minutes")
                .orderDate("2023-01-01")
                .build();
        doNothing().when(orderServiceClient).updateClientOrder(clientId,orderId, orderRequestModel);

          mockMvc.perform(put("/api/v1/clients/" + clientId + "/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestModel)))
                .andExpect(status().isNoContent());

          verify(orderServiceClient, times(1)).updateClientOrder(clientId,orderId, orderRequestModel);

    }

}