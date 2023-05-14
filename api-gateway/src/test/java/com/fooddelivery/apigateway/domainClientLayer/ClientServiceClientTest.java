package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fooddelivery.apigateway.presentationLayer.ClientRequestModel;
import com.fooddelivery.apigateway.presentationLayer.ClientResponseModel;
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

@SpringBootTest
class ClientServiceClientTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ClientServiceClient clientServiceClient;

    private String clientId = "c3540a89-cb47-4c96-888e-ff96708db4d8";
    private String baseUrl = "http://localhost:8080/api/v1/clients";


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        clientServiceClient = new ClientServiceClient(restTemplate, new ObjectMapper(), "localhost", "8080");
    }

    @Test
    public void getAllClientsAggregateTest() {
        // Arrange
        String url = baseUrl;

        ClientResponseModel client1 = new ClientResponseModel("1", "user1", "pass1", "21", "email1@test.com", "phone1", "country1", "street1", "city1", "province1", "postal1");
        ClientResponseModel client2 = new ClientResponseModel("2", "user2", "pass2", "22", "email2@test.com", "phone2", "country2", "street2", "city2", "province2", "postal2");
        ClientResponseModel[] clientResponseModels = new ClientResponseModel[] { client1, client2 };

        when(restTemplate.getForObject(url, ClientResponseModel[].class)).thenReturn(clientResponseModels);

        // Act
        ClientResponseModel[] actualClientResponseModels = clientServiceClient.getAllClientsAggregate();

        // Assert
        assertEquals(clientResponseModels.length, actualClientResponseModels.length);
        for (int i = 0; i < clientResponseModels.length; i++) {
            assertEquals(clientResponseModels[i], actualClientResponseModels[i]);
        }

        verify(restTemplate, times(1)).getForObject(url, ClientResponseModel[].class);
    }

    @Test
    public void getClientTest() {
        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        when(restTemplate.getForObject(baseUrl + "/" + clientId, ClientResponseModel.class)).thenReturn(clientResponseModel);

        ClientResponseModel result = clientServiceClient.getClient(clientId);
        assertEquals(result.getClientId(), clientId);
        assertEquals(result.getUserName(), "1");
        assertEquals(result.getPassword(), "1");
        assertEquals(result.getAge(), "1");
        assertEquals(result.getEmailAddress(), "1");
        assertEquals(result.getPhoneNumber(), "1");
        assertEquals(result.getCountryName(), "1");
        assertEquals(result.getStreetName(), "1");
        assertEquals(result.getCityName(), "1");
        assertEquals(result.getProvinceName(), "1");
        assertEquals(result.getPostalCode(), "1");

        verify(restTemplate, times(1)).getForObject(baseUrl + "/" + clientId, ClientResponseModel.class);

    }
    @Test
    public void addClientTest() {
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");

        when(restTemplate.postForObject(baseUrl, clientRequestModel, ClientResponseModel.class)).thenReturn(clientResponseModel);

        ClientResponseModel result = clientServiceClient.addClient(clientRequestModel);
        assertEquals(result.getClientId(), clientId);

        // Verify that postForObject method was called
        verify(restTemplate, times(1)).postForObject(baseUrl, clientRequestModel, ClientResponseModel.class);
    }
    @Test
    public void testGetClient_NotFoundException() throws JsonProcessingException {
        String clientId = "non-existing-id";
        HttpErrorInfo errorInfo = new HttpErrorInfo( HttpStatus.NOT_FOUND, "/api/v1/clients/" + clientId,"Not Found");

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

        when(restTemplate.getForObject(baseUrl + "/" + clientId, ClientResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                clientServiceClient.getClient(clientId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }
    @Test
    public void testAddClient_NotFoundException() throws JsonProcessingException {
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/clients", "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        when(restTemplate.postForObject(baseUrl, clientRequestModel, ClientResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                clientServiceClient.addClient(clientRequestModel));

        assertTrue(exception.getMessage().contains("Not Found"));
    }


    @Test
    public void testAddClient_InvalidInputException() throws JsonProcessingException {
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/clients", "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = "";
        try {
            errorInfoJson = objectMapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        when(restTemplate.postForObject(baseUrl, clientRequestModel, ClientResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(InvalidInputException.class, () ->
                clientServiceClient.addClient(clientRequestModel));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void testUpdateClient_NotFoundException() throws JsonProcessingException {
        String clientId = "non-existing-id";
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/clients/" + clientId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(NotFoundException.class, () ->
                clientServiceClient.updateClient(clientId, clientRequestModel));

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testUpdateClient_InvalidInputException() throws JsonProcessingException {
        String clientId = "1";
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/clients/" + clientId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                clientServiceClient.updateClient(clientId, clientRequestModel));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void testDeleteClient_NotFoundException() throws JsonProcessingException {
        String clientId = "non-existing-id";

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/clients/" + clientId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());

        Exception exception = assertThrows(NotFoundException.class, () ->
                clientServiceClient.deleteClient(clientId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testDeleteClient_InvalidInputException() throws JsonProcessingException {
        String clientId = "1";

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/clients/" + clientId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                clientServiceClient.deleteClient(clientId));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void requestCallback_SetsCorrectHeadersAndBody() throws Exception {
        // Arrange
        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName("1")
                .password("1")
                .age("1")
                .emailAddress("1")
                .phoneNumber("1")
                .countryName("1")
                .streetName("1")
                .cityName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        // Mocking ClientHttpRequest
        ClientHttpRequest clientHttpRequest = mock(ClientHttpRequest.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(clientHttpRequest.getBody()).thenReturn(outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        when(clientHttpRequest.getHeaders()).thenReturn(httpHeaders);

        // Access private method via reflection
        Method requestCallbackMethod = ClientServiceClient.class.getDeclaredMethod("requestCallback", ClientRequestModel.class);
        requestCallbackMethod.setAccessible(true);

        // Act
        RequestCallback requestCallback = (RequestCallback) requestCallbackMethod.invoke(clientServiceClient, clientRequestModel);
        requestCallback.doWithRequest(clientHttpRequest);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writeValueAsString(clientRequestModel);
        String actualBody = outputStream.toString();
        assertEquals(expectedBody, actualBody);

        assertEquals(MediaType.APPLICATION_JSON_VALUE, httpHeaders.getContentType().toString());
        assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));
    }
}



