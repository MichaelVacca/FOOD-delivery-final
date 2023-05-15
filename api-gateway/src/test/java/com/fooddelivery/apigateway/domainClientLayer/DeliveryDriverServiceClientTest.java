package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fooddelivery.apigateway.presentationLayer.ClientResponseModel;
import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverRequestModel;
import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverResponseModel;
import com.fooddelivery.apigateway.utils.HttpErrorInfo;
import com.fooddelivery.apigateway.utils.exceptions.InvalidInputException;
import com.fooddelivery.apigateway.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DeliveryDriverServiceClientTest {


    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private DeliveryDriverServiceClient deliveryDriverServiceClient;

    private String deliveryDriverId = "c3540a89-cb47-4c96-888e-ff96708db4d8";
    private String baseUrl = "http://localhost:8080/api/v1/deliveryDrivers";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        deliveryDriverServiceClient = new DeliveryDriverServiceClient(restTemplate, new ObjectMapper(), "localhost", "8080");
    }

    @Test
    public void getAllDeliveryDriversAggregateTest() {
        String url = baseUrl;
        DeliveryDriverResponseModel deliveryDriver1 = new DeliveryDriverResponseModel("1", "firstName", "lastName", "21", "testDesc1", "2011", "country1", "street1", "city1", "province1", "postal1");
        DeliveryDriverResponseModel deliveryDriver2 = new DeliveryDriverResponseModel("2", "firstname", "pass2", "22", "testDesc2", "2012", "country2", "street2", "city2", "province2", "postal2");

        DeliveryDriverResponseModel[] deliveryDriverResponseModels = new DeliveryDriverResponseModel[]{deliveryDriver1, deliveryDriver2};

        when(restTemplate.getForObject(url, DeliveryDriverResponseModel[].class)).thenReturn(deliveryDriverResponseModels);

        DeliveryDriverResponseModel[] deliveryDriverResponseModels1 = deliveryDriverServiceClient.getAllDeliveryDriversAggregate();

        assertEquals(deliveryDriverResponseModels.length, deliveryDriverResponseModels1.length);
        for (int i = 0; i < deliveryDriverResponseModels.length; i++) {
            assertEquals(deliveryDriverResponseModels[i], deliveryDriverResponseModels1[i]);
        }

        verify(restTemplate, times(1)).getForObject(url, DeliveryDriverResponseModel[].class);


    }

    @Test
    public void getDeliveryDriverByIdTest() {
        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel(deliveryDriverId, "firstName", "lastName", "21", "testDesc1", "2011", "country1", "street1", "city1", "province1", "postal1");

        when(restTemplate.getForObject(baseUrl + "/" + deliveryDriverId, DeliveryDriverResponseModel.class)).thenReturn(deliveryDriverResponseModel);

        DeliveryDriverResponseModel result = deliveryDriverServiceClient.getDeliveryDrivers(deliveryDriverId);
        assertEquals(result.getDeliveryDriverId(), deliveryDriverId);
        assertEquals(result.getFirstName(), deliveryDriverResponseModel.getFirstName());
        assertEquals(result.getLastName(), deliveryDriverResponseModel.getLastName());
        assertEquals(result.getDateOfBirth(), deliveryDriverResponseModel.getDateOfBirth());
        assertEquals(result.getDescription(), deliveryDriverResponseModel.getDescription());
        assertEquals(result.getEmployeeSince(), deliveryDriverResponseModel.getEmployeeSince());
        assertEquals(result.getCountryName(), deliveryDriverResponseModel.getCountryName());
        assertEquals(result.getStreetName(), deliveryDriverResponseModel.getStreetName());
        assertEquals(result.getCityName(), deliveryDriverResponseModel.getCityName());
        assertEquals(result.getProvinceName(), deliveryDriverResponseModel.getProvinceName());
        assertEquals(result.getPostalCode(), deliveryDriverResponseModel.getPostalCode());

        verify(restTemplate, times(1)).getForObject(baseUrl + "/" + deliveryDriverId, DeliveryDriverResponseModel.class);
    }
    @Test
    public void getAllDeliveryDriversThrowsHttpClientErrorExceptionTest() {
        // Given
        String url = baseUrl;
        HttpClientErrorException mockException = mock(HttpClientErrorException.class);
        when(restTemplate.getForObject(url, DeliveryDriverResponseModel[].class)).thenThrow(mockException);

        // Then
        assertThrows(RuntimeException.class, () -> {
            // When
            deliveryDriverServiceClient.getAllDeliveryDriversAggregate();
        });

        verify(restTemplate, times(1)).getForObject(url, DeliveryDriverResponseModel[].class);
    }


    @Test
    public void addDeliveryDriverTest() {
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("firstName")
                .lastName("lastName")
                .dateOfBirth("2011")
                .description("testDesc1")
                .employeeSince("2011")
                .countryName("country1")
                .streetName("street1")
                .cityName("city1")
                .provinceName("province1")
                .postalCode("postal1")
                .build();

        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel(deliveryDriverId, "firstName", "lastName", "21", "testDesc1", "2011", "country1", "street1", "city1", "province1", "postal1");


        when(restTemplate.postForObject(baseUrl, deliveryDriverRequestModel, DeliveryDriverResponseModel.class)).thenReturn(deliveryDriverResponseModel);

        DeliveryDriverResponseModel result = deliveryDriverServiceClient.addDeliveryDriver(deliveryDriverRequestModel);
        assertEquals(result.getDeliveryDriverId(), deliveryDriverId);
        assertEquals(result.getFirstName(), deliveryDriverResponseModel.getFirstName());
        assertEquals(result.getLastName(), deliveryDriverResponseModel.getLastName());
        assertEquals(result.getDateOfBirth(), deliveryDriverResponseModel.getDateOfBirth());
        assertEquals(result.getDescription(), deliveryDriverResponseModel.getDescription());
        assertEquals(result.getEmployeeSince(), deliveryDriverResponseModel.getEmployeeSince());
        assertEquals(result.getCountryName(), deliveryDriverResponseModel.getCountryName());
        assertEquals(result.getStreetName(), deliveryDriverResponseModel.getStreetName());
        assertEquals(result.getCityName(), deliveryDriverResponseModel.getCityName());
        assertEquals(result.getProvinceName(), deliveryDriverResponseModel.getProvinceName());
        assertEquals(result.getPostalCode(), deliveryDriverResponseModel.getPostalCode());

        verify(restTemplate, times(1)).postForObject(baseUrl, deliveryDriverRequestModel, DeliveryDriverResponseModel.class);
    }

    @Test
    public void testGetDeliveryDriver_NotFoundException() throws JsonProcessingException {
        String deliveryDriverId = "not-foundId";
        HttpErrorInfo httpErrorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND,"/api/v1/deliveryDrivers/" + deliveryDriverId, "Not Found");


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = "";

        try{
            errorInfoJson = objectMapper.writeValueAsString(httpErrorInfo);
        }
        catch(JsonProcessingException e){
            fail("Failed to serialize HttpErrorInfo: " + e.getMessage());
        }

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        when(restTemplate.getForObject(baseUrl + "/" + deliveryDriverId, DeliveryDriverResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(Exception.class, () -> deliveryDriverServiceClient.getDeliveryDrivers(deliveryDriverId));

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testAddDeliveryDriver_NotFoundException() throws JsonProcessingException {
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("1990-01-01")
                .description("Driver")
                .employeeSince("2020-01-01")
                .countryName("Country")
                .streetName("Street")
                .cityName("City")
                .provinceName("Province")
                .postalCode("PostalCode")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/deliveryDrivers", "Not Found");

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

        when(restTemplate.postForObject(baseUrl, deliveryDriverRequestModel, DeliveryDriverResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(NotFoundException.class, () ->
                deliveryDriverServiceClient.addDeliveryDriver(deliveryDriverRequestModel));

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    public void testAddDeliveryDriver_InvalidInputException() throws JsonProcessingException {
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("1990-01-01")
                .description("Driver")
                .employeeSince("2020-01-01")
                .countryName("Country")
                .streetName("Street")
                .cityName("City")
                .provinceName("Province")
                .postalCode("PostalCode")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/deliveryDrivers", "Unprocessable Entity");

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

        when(restTemplate.postForObject(baseUrl, deliveryDriverRequestModel, DeliveryDriverResponseModel.class)).thenThrow(ex);

        Exception exception = assertThrows(InvalidInputException.class, () ->
                deliveryDriverServiceClient.addDeliveryDriver(deliveryDriverRequestModel));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }

    @Test
    public void testUpdateDeliveryDriver() {
        String driverId = "1";
        String url = baseUrl + "/" + driverId;
        DeliveryDriverRequestModel deliveryDriverRequestModel = new DeliveryDriverRequestModel();


        when(restTemplate.execute(eq(url), eq(HttpMethod.PUT), any(), any())).thenReturn(null);

        // Act
        deliveryDriverServiceClient.updateDeliveryDriver(driverId, deliveryDriverRequestModel);

        // Assert
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.PUT), any(), any());
    }

    @Test
    public void testUpdateDeliveryDriver_ExceptionHandling() {
        // Arrange
        String driverId = "1";
        String url = baseUrl + "/" + driverId;
        DeliveryDriverRequestModel deliveryDriverRequestModel = new DeliveryDriverRequestModel();
        // populate deliveryDriverRequestModel fields here...

        HttpClientErrorException exception = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request",
                HttpHeaders.EMPTY, null, null);

        when(restTemplate.execute(eq(url), eq(HttpMethod.PUT), any(), any())).thenThrow(exception);

        // Act
        Exception thrownException = assertThrows(RuntimeException.class, () -> deliveryDriverServiceClient.updateDeliveryDriver(driverId, deliveryDriverRequestModel));

        // Assert
        assertEquals("400 Bad Request", thrownException.getMessage());
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.PUT), any(), any());
    }



    @Test
    public void testUpdateDeliveryDriver_NotFoundException() throws JsonProcessingException {
        String driverId = "non-existing-id";
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("1990-01-01")
                .description("Driver")
                .employeeSince("2020-01-01")
                .countryName("Country")
                .streetName("Street")
                .cityName("City")
                .provinceName("Province")
                .postalCode("PostalCode")
                .build();

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.NOT_FOUND, "/api/v1/deliveryDrivers/" + driverId, "Not Found");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.PUT), any(), any());

        Exception exception = assertThrows(NotFoundException.class, () ->
                deliveryDriverServiceClient.updateDeliveryDriver(driverId, deliveryDriverRequestModel));

        assertTrue(exception.getMessage().contains("Not Found"));
    }
    @Test
    public void testDeleteDeliveryDriver_InvalidInputException() throws JsonProcessingException {
        String driverId = "1";

        HttpErrorInfo errorInfo = new HttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, "/api/v1/deliveryDrivers/" + driverId, "Unprocessable Entity");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // register JavaTimeModule to support Java 8 date/time types

        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        HttpClientErrorException ex = HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity",
                HttpHeaders.EMPTY, errorInfoJson.getBytes(), null);

        doThrow(ex).when(restTemplate).execute(anyString(), eq(HttpMethod.DELETE), any(), any());

        Exception exception = assertThrows(InvalidInputException.class, () ->
                deliveryDriverServiceClient.deleteDeliveryDriver(driverId));

        assertTrue(exception.getMessage().contains("Unprocessable Entity"));
    }
    @Test
    public void testDeleteDeliveryDriver() {
        // Arrange
        String driverId = "1";
        String url = baseUrl + "/" + driverId;

        when(restTemplate.execute(eq(url), eq(HttpMethod.DELETE), any(), any())).thenReturn(null);

        // Act
        deliveryDriverServiceClient.deleteDeliveryDriver(driverId);

        // Assert
        verify(restTemplate, times(1)).execute(eq(url), eq(HttpMethod.DELETE), any(), any());
    }



    @Test
    public void requestCallback_SetsCorrectHeadersAndBody() throws Exception {
        // Arrange
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("1990-01-01")
                .description("Driver")
                .employeeSince("2020-01-01")
                .countryName("Country")
                .streetName("Street")
                .cityName("City")
                .provinceName("Province")
                .postalCode("PostalCode")
                .build();

        // Mocking ClientHttpRequest
        ClientHttpRequest clientHttpRequest = mock(ClientHttpRequest.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(clientHttpRequest.getBody()).thenReturn(outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        when(clientHttpRequest.getHeaders()).thenReturn(httpHeaders);

        // Access private method via reflection
        Method requestCallbackMethod = DeliveryDriverServiceClient.class.getDeclaredMethod("requestCallback", DeliveryDriverRequestModel.class);
        requestCallbackMethod.setAccessible(true);

        // Act
        RequestCallback requestCallback = (RequestCallback) requestCallbackMethod.invoke(deliveryDriverServiceClient, deliveryDriverRequestModel);
        requestCallback.doWithRequest(clientHttpRequest);

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        String expectedBody = mapper.writeValueAsString(deliveryDriverRequestModel);
        String actualBody = outputStream.toString();
        assertEquals(expectedBody, actualBody);

        assertEquals(MediaType.APPLICATION_JSON_VALUE, httpHeaders.getContentType().toString());
        assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));
    }





}