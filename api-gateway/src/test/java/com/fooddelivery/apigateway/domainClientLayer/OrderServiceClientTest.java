package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fooddelivery.apigateway.presentationLayer.*;
import com.fooddelivery.apigateway.utils.HttpErrorInfo;
import com.fooddelivery.apigateway.utils.exceptions.InvalidInputException;
import com.fooddelivery.apigateway.utils.exceptions.NoItemsException;
import com.fooddelivery.apigateway.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private OrderServiceClient orderServiceClient;

    private String clientId = "1c9284d9-3804-43f1-9eb8-ed500806e5ce";
    private String baseClientOrderUrl1 = "http://localhost:8080/api/v1/clients/"+clientId+"/orders";
    private String baseCLientOrderUrl2 = "http://localhost:8080/api/v1/clients";
    private String baseOrderUrl1 = "http://localhost:8080/api/v1/orders";

    private String orderServiceHost = "localhost";
    private String orderServicePort = "8080";
    private String baseOrderUrl = "http://" + orderServiceHost + ":" + orderServicePort + "/api/v1/orders";
    private String baseClientOrderUrl = "http://" + orderServiceHost + ":" + orderServicePort + "/api/v1/clients";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        orderServiceClient = new OrderServiceClient(restTemplate, new ObjectMapper(), "localhost", "8080");
    }

    @Test
    public void getAllOrdersTest() {
        String url = baseOrderUrl1;

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));
        OrderResponseModel order1 = new OrderResponseModel("orderId1","client1","restoId1","menuId1","driverId1",
                "John","Doe","testUser","test@email.com",items,"resto1","typeOfMenu1", OrderStatus.MAKING_ORDER,20.0,"10 minutes","2023-01-01");
        OrderResponseModel order2 = new OrderResponseModel("orderId2","client2","restoId2","menuId2","driverId2",
                "jane","Doe","testUser2","test@email.com2",items,"resto2","typeOfMenu2", OrderStatus.MAKING_ORDER,10.0,"20 minutes","2023-02-02");
        OrderResponseModel[] orders = new OrderResponseModel[]{order1, order2};

        when(restTemplate.getForObject(url, OrderResponseModel[].class)).thenReturn(orders);

        OrderResponseModel[] actual = orderServiceClient.getAllOrdersAggregate();

        assertEquals(orders.length, actual.length);
        for (int i = 0; i < orders.length; i++) {
            assertEquals(orders[i], actual[i]);
        }
        verify(restTemplate, times(1)).getForObject(url, OrderResponseModel[].class);

    }

    @Test
    public void getAllOrdersThrowsHttpClientErrorExceptionTest() {
        // Given
        String url = baseOrderUrl1;
        HttpClientErrorException mockException = mock(HttpClientErrorException.class);
        when(restTemplate.getForObject(url, OrderResponseModel[].class)).thenThrow(mockException);

        // Then
        assertThrows(RuntimeException.class, () -> {
            // When
            orderServiceClient.getAllOrdersAggregate();
        });

        verify(restTemplate, times(1)).getForObject(url, OrderResponseModel[].class);
    }
    @Test
    public void getOrderByIdTest_Found() {
        // Given
        String orderId = "testOrderId";
        String url = baseOrderUrl1 + "/" + orderId;

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));

        OrderResponseModel expectedOrder = new OrderResponseModel("orderId1","client1","restoId1","menuId1","driverId1",
                "John","Doe","testUser","test@email.com",items,"resto1","typeOfMenu1", OrderStatus.MAKING_ORDER,20.0,"10 minutes","2023-01-01");

        when(restTemplate.getForObject(url, OrderResponseModel.class)).thenReturn(expectedOrder);

        // When
        OrderResponseModel actualOrder = orderServiceClient.getOrderById(orderId);

        // Then
        assertEquals(expectedOrder, actualOrder);
        verify(restTemplate, times(1)).getForObject(url, OrderResponseModel.class);
    }


    @Test
    public void getOrderByIdTest_NotFound()throws JsonProcessingException {
        String orderId= "notFound";
        HttpErrorInfo errorInfo = new HttpErrorInfo( HttpStatus.NOT_FOUND, "/api/v1/orders/" + orderId,"Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }
        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        when(restTemplate.getForObject(baseOrderUrl1 + "/" + orderId, OrderResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                orderServiceClient.getOrderById(orderId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }
    @Test
    public void getOrdersByClientIdTest() {
        String url = baseClientOrderUrl1;

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));
        OrderResponseModel order1 = new OrderResponseModel("orderId1","client1","restoId1","menuId1","driverId1",
                "John","Doe","testUser","test@email.com",items,"resto1","typeOfMenu1", OrderStatus.MAKING_ORDER,20.0,"10 minutes","2023-01-01");
        OrderResponseModel order2 = new OrderResponseModel("orderId2","client2","restoId2","menuId2","driverId2",
                "jane","Doe","testUser2","test@email.com2",items,"resto2","typeOfMenu2", OrderStatus.MAKING_ORDER,10.0,"20 minutes","2023-02-02");
        OrderResponseModel[] orders = new OrderResponseModel[]{order1, order2};

        when(restTemplate.getForObject(url, OrderResponseModel[].class)).thenReturn(orders);

        OrderResponseModel[] actual = orderServiceClient.getAllOrdersAggregateByClientId(clientId);

        assertEquals(orders.length, actual.length);
        for (int i = 0; i < orders.length; i++) {
            assertEquals(orders[i], actual[i]);
        }
        verify(restTemplate, times(1)).getForObject(url, OrderResponseModel[].class);
    }

    @Test
    public void getOrderByOrderIdAndByClientIdTest() {
        // Setup
        String clientId = "client1";
        String orderId = "orderId1";

        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders/" + orderId;

        List<Items> items = new ArrayList<>();
        items.add(new Items("item1", "desc1", 10.0));
        items.add(new Items("item2", "desc2", 20.0));

        OrderResponseModel expectedOrder = new OrderResponseModel("orderId1","client1","restoId1","menuId1","driverId1",
                "John","Doe","testUser","test@email.com",items,"resto1","typeOfMenu1", OrderStatus.MAKING_ORDER,20.0,"10 minutes","2023-01-01");

        // Mock the restTemplate call
        when(restTemplate.getForObject(url, OrderResponseModel.class)).thenReturn(expectedOrder);

        // Call the method to test
        OrderResponseModel actualOrder = orderServiceClient.getOrderByOrderIdAndByClientId(clientId, orderId);

        // Assert the response
        assertEquals(expectedOrder, actualOrder);

        // Verify that restTemplate.getForObject was called
        verify(restTemplate, times(1)).getForObject(url, OrderResponseModel.class);
    }


    @Test
    public void getOrderByOrderIdAndByClientIdTest_ThrowsException() {
        // Setup
        String clientId = "client1";
        String orderId = "notFound";

        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders/" + orderId;

        HttpErrorInfo errorInfo = new HttpErrorInfo( HttpStatus.NOT_FOUND, "/api/v1/clients" + clientId + "/orders/" + orderId,"Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }
        // Create an HttpClientErrorException
        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        // Mock the restTemplate call to throw an HttpClientErrorException
        when(restTemplate.getForObject(url, OrderResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                orderServiceClient.getOrderByOrderIdAndByClientId(clientId,orderId));
        // Assert that an HttpClientErrorException is thrown
        assertTrue(exception.getMessage().contains("Not Found"));
    }



/*    @Test
    public void getOrderByIdTest_NotFound()throws JsonProcessingException {
        String orderId= "notFound";
        HttpErrorInfo errorInfo = new HttpErrorInfo( HttpStatus.NOT_FOUND, "/api/v1/orders/" + orderId,"Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }
        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        when(restTemplate.getForObject(baseOrderUrl1 + "/" + orderId, OrderResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                orderServiceClient.getOrderById(orderId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }*/


    @Test
    public void addOrderTest() {

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
        OrderResponseModel orderResponseModel = new OrderResponseModel("orderId1",clientId,"restoId1","menuId1","driverId1",
                "John","Doe","testUser","test@email.com",items,"resto1","typeOfMenu1", OrderStatus.MAKING_ORDER,20.0,"10 minutes","2023-01-01");

        when(restTemplate.postForObject(baseClientOrderUrl1, orderRequestModel, OrderResponseModel.class)).thenReturn(orderResponseModel);

        OrderResponseModel actual = orderServiceClient.processClientOrders(orderRequestModel,clientId);

        assertEquals(actual.getOrderId(), orderResponseModel.getOrderId());
        assertEquals(actual.getClientId(), orderResponseModel.getClientId());
        assertEquals(actual.getRestaurantId(), orderResponseModel.getRestaurantId());
        assertEquals(actual.getMenuId(), orderResponseModel.getMenuId());
        assertEquals(actual.getDeliveryDriverId(), orderResponseModel.getDeliveryDriverId());
        assertEquals(actual.getOrderStatus(), orderResponseModel.getOrderStatus());
        assertEquals(actual.getItems(), orderResponseModel.getItems());
        assertEquals(actual.getFinalPrice(), orderResponseModel.getFinalPrice());
        assertEquals(actual.getEstimatedDeliveryTime(), orderResponseModel.getEstimatedDeliveryTime());
        assertEquals(actual.getOrderDate(), orderResponseModel.getOrderDate());

        verify(restTemplate, times(1)).postForObject(baseClientOrderUrl1, orderRequestModel, OrderResponseModel.class);
    }

    @Test
    public void processClientOrdersTest_ThrowsException() {
        // Setup
        String clientId = "client1";

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

        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders";

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, url, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        // Create an HttpClientErrorException
        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        // Mock the restTemplate call to throw an HttpClientErrorException
        when(restTemplate.postForObject(url, orderRequestModel, OrderResponseModel.class)).thenThrow(ex);

        // Assert that a NotFoundException is thrown
        Exception exception = assertThrows(NotFoundException.class, () -> {
            // Call the method to test
            orderServiceClient.processClientOrders(orderRequestModel, clientId);
        });

        // Verify that restTemplate.postForObject was called
        verify(restTemplate, times(1)).postForObject(url, orderRequestModel, OrderResponseModel.class);

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void processClientOrdersTest_ThrowsNoItemsException() {
        // Setup
        String clientId = "client1";

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

        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders";

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.BAD_REQUEST, url, "No Items in the Order");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        // Create an HttpClientErrorException
        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "No Items in the Order",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        // Mock the restTemplate call to throw an HttpClientErrorException
        when(restTemplate.postForObject(url, orderRequestModel, OrderResponseModel.class)).thenThrow(ex);

        // Assert that a NoItemsException is thrown
        Exception exception = assertThrows(NoItemsException.class, () -> {
            // Call the method to test
            orderServiceClient.processClientOrders(orderRequestModel, clientId);
        });

        // Verify that restTemplate.postForObject was called
        verify(restTemplate, times(1)).postForObject(url, orderRequestModel, OrderResponseModel.class);

        assertTrue(exception.getMessage().contains("No Items in the Order"));
    }


    @Test
    public void testUpdateClientOrder() {
        // Given
        String clientId = "clientId1";
        String orderId = "orderId1";
        String url = baseClientOrderUrl + "/clientId1/orders/orderId1";

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

        when(restTemplate.execute(eq(url), eq(HttpMethod.PUT), any(),  any())).thenReturn(null);

        // When
        orderServiceClient.updateClientOrder(clientId, orderId, orderRequestModel);

        // Then
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.PUT), any(), any());
    }



    @Test
    public void testUpdateOrder_NotFoundException() throws JsonProcessingException {
        String orderId="not-found";

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

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/clients/" + clientId + "/orders/" + orderId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(NotFoundException.class, () ->
                orderServiceClient.updateClientOrder(clientId,orderId ,orderRequestModel));
        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testUpdateOrder_InvalidInput() throws JsonProcessingException {
        String orderId="1";

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

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/clients/" + clientId + "/orders/" + orderId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                orderServiceClient.updateClientOrder(clientId,orderId ,orderRequestModel));
        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void testDeleteOrder() {
        // Given
        String orderId = "orderId1";
        String url = baseOrderUrl1 + "/" + orderId;

        when(restTemplate.execute(eq(url), eq(HttpMethod.DELETE), any(),  any())).thenReturn(null);

        // When
        orderServiceClient.deleteOrder(orderId);

        // Then
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.DELETE), any(),  any());
    }


    @Test
    public void testDeleteOrder_NotFoundException() throws JsonProcessingException {
        String orderId="not-found";
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/clients/" + clientId + "/orders/" + orderId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());

        Exception exception = assertThrows(NotFoundException.class, () ->
                orderServiceClient.deleteOrder(orderId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }
    @Test
    public void testDeleteOrder_InvalidInput() throws JsonProcessingException {
        String orderId="1";
        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/clients/" + clientId + "/orders/" + orderId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);
        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                orderServiceClient.deleteOrder(orderId));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void deleteClientOrderTest() {
        // Setup
        String clientId = "client1";
        String orderId = "orderId1";
        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders/" + orderId;

        when(restTemplate.execute(eq(url), eq(HttpMethod.DELETE), any(),  any())).thenReturn(null);

        // Call the method to test
        orderServiceClient.deleteClientOrder(clientId, orderId);

        // Verify that restTemplate.execute was called
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.DELETE), any(), any());
    }

    @Test
    public void deleteClientOrderTest_ThrowsException() {
        // Setup
        String clientId = "client1";
        String orderId = "orderId1";
        String url = baseCLientOrderUrl2 + "/" + clientId + "/orders/" + orderId;

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, url, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(eq(url), eq(HttpMethod.DELETE), any(), any());

        // Assert that a NotFoundException is thrown
        Exception exception = assertThrows(NotFoundException.class, () -> {
            // Call the method to test
            orderServiceClient.deleteClientOrder(clientId, orderId);
        });

        // Verify that restTemplate.execute was called
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.DELETE), any(), any());

        assertTrue(exception.getMessage().contains("Not Found"));
    }


    @Test
    public void requestCallBack_SetsCorrectHeadersAndBody() throws Exception {
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
        ClientHttpRequest clientHttpRequest = mock(ClientHttpRequest.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(clientHttpRequest.getBody()).thenReturn(outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        when(clientHttpRequest.getHeaders()).thenReturn(httpHeaders);

        Method requestCallbackMethod = OrderServiceClient.class.getDeclaredMethod("requestCallback", OrderRequestModel.class);
        requestCallbackMethod.setAccessible(true);

        // Act
        RequestCallback requestCallback = (RequestCallback) requestCallbackMethod.invoke(orderServiceClient, orderRequestModel);
        requestCallback.doWithRequest(clientHttpRequest);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writeValueAsString(orderRequestModel);
        String actualBody = outputStream.toString();
        assertEquals(expectedBody, actualBody);

        assertEquals(MediaType.APPLICATION_JSON_VALUE, httpHeaders.getContentType().toString());
        assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));

    }

}