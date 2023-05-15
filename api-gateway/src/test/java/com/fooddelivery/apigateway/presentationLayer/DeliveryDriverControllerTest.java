package com.fooddelivery.apigateway.presentationLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.businessLayer.ClientsService;
import com.fooddelivery.apigateway.domainClientLayer.ClientServiceClient;
import com.fooddelivery.apigateway.domainClientLayer.DeliveryDriverServiceClient;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeliveryDriverControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    RestTemplate restTemplate;

    @MockBean
    DeliveryDriverServiceClient deliveryDriverServiceClient;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate1;



    private ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl = "http://localhost:";

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl + port + "/api/v1/deliveryDrivers";
    }

    @Test
    public void getAllDeliveryDrivers() throws Exception {
        DeliveryDriverResponseModel deliveryDriverResponseModel1 = new DeliveryDriverResponseModel("deliveryDriverId","firstName1","lastName1",
                "2000","testDesc1","employeeSince1","countryName1","cityName1","streetName1","provinceName1","postalCode1");
        DeliveryDriverResponseModel deliveryDriverResponseModel2 = new DeliveryDriverResponseModel("deliveryDriverId","firstName2","lastName2","2001","testDesc2","employeeSince2","countryName2","cityName2","streetName2","provinceName2","postalCode2");
        DeliveryDriverResponseModel[] deliveryDriverResponseModels = new DeliveryDriverResponseModel[]{deliveryDriverResponseModel1,deliveryDriverResponseModel2};


        when(deliveryDriverServiceClient.getAllDeliveryDriversAggregate()).thenReturn(deliveryDriverResponseModels);

        mockMvc.perform(get("/api/v1/deliveryDrivers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(deliveryDriverServiceClient, times(1)).getAllDeliveryDriversAggregate();

    }

    @Test
    public void getDeliveryDriverById() throws Exception {
        String deliveryDriverId = "1";
        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel("deliveryDriverId","firstName1","lastName1",
                "2000","testDesc1","employeeSince1","countryName1","cityName1","streetName1","provinceName1","postalCode1");
        when(deliveryDriverServiceClient.getDeliveryDrivers(deliveryDriverId)).thenReturn(deliveryDriverResponseModel);
        mockMvc.perform(get("/api/v1/deliveryDrivers/"+deliveryDriverId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(deliveryDriverServiceClient, times(1)).getDeliveryDrivers(deliveryDriverId);
    }

    @Test
    public void addDeliveryDriver() throws Exception {
        String deliveryDriverId = "1";
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("1")
                .lastName("1")
                .dateOfBirth("1")
                .description("1")
                .employeeSince("1")
                .countryName("1")
                .cityName("1")
                .streetName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

                DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel("1","1","1",
                        "1","1","1","1","1","1","1","1");
        when(deliveryDriverServiceClient.addDeliveryDriver(deliveryDriverRequestModel)).thenReturn(deliveryDriverResponseModel);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/deliveryDrivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryDriverRequestModel)))
                .andExpect(status().isCreated())
                .andReturn();
        DeliveryDriverResponseModel deliveryDriverResponseModel1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DeliveryDriverResponseModel.class);

        assertEquals(deliveryDriverRequestModel.firstName, deliveryDriverResponseModel1.getFirstName());
        assertEquals(deliveryDriverRequestModel.lastName, deliveryDriverResponseModel1.getLastName());
        assertEquals(deliveryDriverRequestModel.dateOfBirth, deliveryDriverResponseModel1.getDateOfBirth());
        assertEquals(deliveryDriverRequestModel.description, deliveryDriverResponseModel1.getDescription());
        assertEquals(deliveryDriverRequestModel.employeeSince, deliveryDriverResponseModel1.getEmployeeSince());
        assertEquals(deliveryDriverRequestModel.countryName, deliveryDriverResponseModel1.getCountryName());
        assertEquals(deliveryDriverRequestModel.cityName, deliveryDriverResponseModel1.getCityName());
        assertEquals(deliveryDriverRequestModel.streetName, deliveryDriverResponseModel1.getStreetName());
        assertEquals(deliveryDriverRequestModel.provinceName, deliveryDriverResponseModel1.getProvinceName());
        assertEquals(deliveryDriverRequestModel.postalCode, deliveryDriverResponseModel1.getPostalCode());
    }

    @Test
    public void updateDeliveryDriver() throws Exception {
        String deliveryDriverId = "1";
        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName("1")
                .lastName("1")
                .dateOfBirth("1")
                .description("1")
                .employeeSince("1")
                .countryName("1")
                .cityName("1")
                .streetName("1")
                .provinceName("1")
                .postalCode("1")
                .build();

        doNothing().when(deliveryDriverServiceClient).updateDeliveryDriver(deliveryDriverId,deliveryDriverRequestModel);
        mockMvc.perform(put("/api/v1/deliveryDrivers/"+deliveryDriverId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryDriverRequestModel)))
                .andExpect(status().isNoContent());
        verify(deliveryDriverServiceClient, times(1)).updateDeliveryDriver(deliveryDriverId,deliveryDriverRequestModel);
    }
    @Test
    public void deleteDeliveryDriver() throws Exception {
        String deliveryDriverId = "1";
        doNothing().when(deliveryDriverServiceClient).deleteDeliveryDriver(deliveryDriverId);
        mockMvc.perform(delete("/api/v1/deliveryDrivers/"+deliveryDriverId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(deliveryDriverServiceClient, times(1)).deleteDeliveryDriver(deliveryDriverId);
    }

}