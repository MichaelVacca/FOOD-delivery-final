package com.fooddelivery.apigateway.presentationLayer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.businessLayer.ClientsService;
import com.fooddelivery.apigateway.domainClientLayer.ClientServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerTest {

    @LocalServerPort
    private int port;
    @MockBean
    private ClientsService clientsService;

    @Autowired
    RestTemplate restTemplate;

    @MockBean
    ClientServiceClient clientServiceClient;
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate1;



    private ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl = "http://localhost:";





    @BeforeEach
    public void setup() {
        baseUrl = baseUrl + port + "/api/v1/clients";
    }

    @Test
    public void getAllClientsTest() throws Exception {
        ClientResponseModel clientResponseMode1 = new ClientResponseModel("clientId", "username", "password", "age",
                "email", "phone", "country", "street", "city", "province", "postalCode");
        ClientResponseModel clientResponseMode2 = new ClientResponseModel("clientId2", "username2", "password2", "age2",
                "email2", "phone2", "country2", "street2", "city2", "province2", "postalCode2");
        ClientResponseModel[] clientResponseModels = new ClientResponseModel[]{clientResponseMode1, clientResponseMode2};



        when(clientsService.getAllClientsAggregate()).thenReturn(clientResponseModels);

        mockMvc.perform(get("/api/v1/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientsService, times(1)).getAllClientsAggregate();

    }


    @Test
    public void getClientTest() throws Exception {
        String clientId = "1";
        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "username", "password", "age",
                "email", "phone", "country", "street", "city", "province", "postalCode");

        when(clientsService.getClient(clientId)).thenReturn(clientResponseModel);

        mockMvc.perform(get("/api/v1/clients/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientsService, times(1)).getClient(clientId);
    }

    @Test
    public void returnAllClients() throws Exception {

        String clientId = "c3540a89-cb47-4c96-888e-ff96708db4d8";
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

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId,"1","1","1","1","1","1","1","1","1","1");

/*        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/api/v1/clients/" + clientId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(clientResponseModel)));*/
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel);
        assertEquals(clientResponseModel.getClientId(), clientId);
        assertEquals(clientResponseModel.getUserName(), "1");
        assertEquals(clientResponseModel.getPassword(), "1");
        assertEquals(clientResponseModel.getAge(), "1");
        assertEquals(clientResponseModel.getEmailAddress(), "1");
        assertEquals(clientResponseModel.getPhoneNumber(), "1");
        assertEquals(clientResponseModel.getCountryName(), "1");
        assertEquals(clientResponseModel.getStreetName(), "1");
        assertEquals(clientResponseModel.getCityName(), "1");
        assertEquals(clientResponseModel.getProvinceName(), "1");
        assertEquals(clientResponseModel.getPostalCode(), "1");
    }

    @Test
    public void addClientTest() throws Exception {
        String clientId = "c3540a89-cb47-4c96-888e-ff96708db4d8";
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

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId,"1","1","1","1","1","1","1","1","1","1");
// set the properties of clientResponseModel
        when(clientsService.addClient(clientRequestModel)).thenReturn(clientResponseModel);


        MvcResult mvcResult = mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestModel)))
                .andExpect(status().isCreated())
                .andReturn();

        ClientResponseModel response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClientResponseModel.class);
        assertEquals(clientRequestModel.getUserName(), response.getUserName());
        assertEquals(clientRequestModel.getPassword(), response.getPassword());
        assertEquals(clientRequestModel.getAge(), response.getAge());
        assertEquals(clientRequestModel.getEmailAddress(), response.getEmailAddress());
        assertEquals(clientRequestModel.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(clientRequestModel.getCountryName(), response.getCountryName());
        assertEquals(clientRequestModel.getStreetName(), response.getStreetName());
        assertEquals(clientRequestModel.getCityName(), response.getCityName());
        assertEquals(clientRequestModel.getProvinceName(), response.getProvinceName());
        assertEquals(clientRequestModel.getPostalCode(), response.getPostalCode());
    }

    @Test
    public void updateClientTest() throws Exception {
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

        doNothing().when(clientsService).updateClient(clientId, clientRequestModel);

        mockMvc.perform(put("/api/v1/clients/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestModel)))
                .andExpect(status().isNoContent());

        verify(clientsService, times(1)).updateClient(clientId, clientRequestModel);
    }

    @Test
    public void deleteClientTest() throws Exception {
        String clientId = "1";

        doNothing().when(clientsService).deleteClient(clientId);

        mockMvc.perform(delete("/api/v1/clients/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clientsService, times(1)).deleteClient(clientId);
    }
}










