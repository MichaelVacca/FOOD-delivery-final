package com.fooddelivery.clientservice.presentationlayer;

import com.fooddelivery.clientservice.Datalayer.ClientRepository;
import com.mysql.cj.xdevapi.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClientControllerIntegrationTest {
    //values ('0a1491af-551b-4b40-87c4-ff268d187b9a','dgalletley0','b33p3d4','26','jkunzelmann0@live.com','(129) 7685046','Canada','Marquette','St. Thomas','Ontario','N5R-5I9');
    private final String BASE_URI_CLIENTS = "/api/v1/clients";

    private final String VALID_CLIENT_ID = "0a1491af-551b-4b40-87c4-ff268d187b9a";
    private final String VALID_CLIENT_ID_2 = "29858c17-d623-49ac-97b4-eaf835fee98c";
    private final String VALID_USER_NAME = "dgalletley0";
    private final String VALID_PASSWORD = "b33p3d4";
    private final String VALID_AGE = "26";
    private final String VALID_EMAIL_ADDRESS = "jkunzelmann0@live.com";
    private final String VALID_PHONE_NUMBER = "(129) 7685046";
    private final String VALID_COUNTRY_NAME = "Canada";
    private final String VALID_STREET_NAME = "Marquette";
    private final String VALID_CITY_NAME = "St. Thomas";
    private final String VALID_PROVINCE_NAME = "Ontario";
    private final String VALID_POSTAL_CODE = "N5R-5I9";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void whenClientsExists_thenReturnAllClients(){
        Integer expectedNumberOfClients = 5;

        webTestClient.get()
                .uri(BASE_URI_CLIENTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumberOfClients);
    }

    @Test
    public void whenGetClientsWithValidCLientId_thenReturnClient(){
        webTestClient.get()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(VALID_CLIENT_ID)
                .jsonPath("$.userName").isEqualTo(VALID_USER_NAME)
                .jsonPath("$.password").isEqualTo(VALID_PASSWORD)
                .jsonPath("$.age").isEqualTo(VALID_AGE)
                .jsonPath("$.emailAddress").isEqualTo(VALID_EMAIL_ADDRESS)
                .jsonPath("$.phoneNumber").isEqualTo(VALID_PHONE_NUMBER)
                .jsonPath("$.countryName").isEqualTo(VALID_COUNTRY_NAME)
                .jsonPath("$.streetName").isEqualTo(VALID_STREET_NAME)
                .jsonPath("$.cityName").isEqualTo(VALID_CITY_NAME)
                .jsonPath("$.provinceName").isEqualTo(VALID_PROVINCE_NAME)
                .jsonPath("$.postalCode").isEqualTo(VALID_POSTAL_CODE);

    }

/*    @Test
    public void whenGetClientWithInvalidId_thenReturnNotFoundException()  {
        String INVALID_CLIENT_ID  = "9a1491af-551b-4b40-87c4-ff268d187b9b";
        webTestClient.get().uri(BASE_URI_CLIENTS + "/" + INVALID_CLIENT_ID).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_CLIENTS +"/" + INVALID_CLIENT_ID)
                .jsonPath("$.message").isEqualTo("Client with id: " + INVALID_CLIENT_ID + " not found");
    }*/

    @Test
    public void whenCreateClientWithValidValues_thenReturnNewClient(){
        String expectedUsername = "Michael";
        String expectedPassword = "password122";
        String expectedAge = "25";
        String expectedEmailAddress = "mvacca0@live.com";
        String expectedPhoneNumber = "(323) 456-7890";
        String expectedCountryName = "Canada";
        String expectedStreetName = "testStreet";
        String expectedCityName = "testCity";
        String expectedProvinceName = "Ontario";
        String expectedPostalCode = "N5R-5I9";

        ClientRequestModel clientRequestModel = new ClientRequestModel(expectedUsername, expectedPassword, expectedAge, expectedEmailAddress, expectedPhoneNumber, expectedCountryName, expectedStreetName, expectedCityName, expectedProvinceName, expectedPostalCode);


        webTestClient.post()
                .uri(BASE_URI_CLIENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isNotEmpty()
                .jsonPath("$.userName").isEqualTo(expectedUsername)
                .jsonPath("$.password").isEqualTo(expectedPassword)
                .jsonPath("$.age").isEqualTo(expectedAge)
                .jsonPath("$.emailAddress").isEqualTo(expectedEmailAddress)
                .jsonPath("$.phoneNumber").isEqualTo(expectedPhoneNumber)
                .jsonPath("$.countryName").isEqualTo(expectedCountryName)
                .jsonPath("$.streetName").isEqualTo(expectedStreetName)
                .jsonPath("$.cityName").isEqualTo(expectedCityName)
                .jsonPath("$.provinceName").isEqualTo(expectedProvinceName)
                .jsonPath("$.postalCode").isEqualTo(expectedPostalCode);
        
    }

    @Test
    public void whenUpdateClientWithValidValues_thenReturnUpdatedClient(){
        String updatedUsername = "Michael";
        String updatedPassword = "password122";
        String updatedAge = "25";
        String updatedEmailAddress = "mvacca0@live.com";
        String updatedPhoneNumber = "(323) 456-7890";
        String updatedCountryName = "Canada";
        String updatedStreetName = "testStreet";
        String updatedCityName = "testCity";
        String updatedProvinceName = "Ontario";
        String updatedPostalCode = "N5R-5I9";

        ClientRequestModel clientRequestModel = new ClientRequestModel(updatedUsername, updatedPassword, updatedAge, updatedEmailAddress, updatedPhoneNumber, updatedCountryName, updatedStreetName, updatedCityName, updatedProvinceName, updatedPostalCode);

        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENT_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isNotEmpty()
                .jsonPath("$.userName").isEqualTo(updatedUsername)
                .jsonPath("$.password").isEqualTo(updatedPassword)
                .jsonPath("$.age").isEqualTo(updatedAge)
                .jsonPath("$.emailAddress").isEqualTo(updatedEmailAddress)
                .jsonPath("$.phoneNumber").isEqualTo(updatedPhoneNumber)
                .jsonPath("$.countryName").isEqualTo(updatedCountryName)
                .jsonPath("$.streetName").isEqualTo(updatedStreetName)
                .jsonPath("$.cityName").isEqualTo(updatedCityName)
                .jsonPath("$.provinceName").isEqualTo(updatedProvinceName)
                .jsonPath("$.postalCode").isEqualTo(updatedPostalCode);

    }

    @Test
    public void whenDeleteClientByClientId_thenDeleteCLient(){
        webTestClient.delete()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENT_ID_2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void whenDeleteClientByInvalidClientId_thenThrowNotFoundException() {
        String invalidClientId = "9a1491af-551b-4b40-87c4-ff268d187b9b";

        webTestClient.delete()
                .uri(BASE_URI_CLIENTS + "/" + invalidClientId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Client with id: " + invalidClientId + " not found"));

    }

    @Test
    public void whenUpdateExistingClients_withNonexistentClientId_thenThrowNotFoundException() {
        // Arrange
        String nonExistentClientId = "non-existent-client-id";
        ClientRequestModel clientRequestModel = createNewRequestModel("Michael", "password122", "25", "mvacca0@live.com",
                "(323) 456-7890", "Canada", "testStreet", "testCity", "Ontario", "N5R-5I9");

        

        // Act and assert
        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + nonExistentClientId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Client with id: " + nonExistentClientId + " not found");
    }


    private ClientRequestModel createNewRequestModel(String userName, String password, String age, String emailAddress,
                                                     String phoneNumber, String countryName, String streetName,
                                                     String cityName, String provinceName,String postalCode) {

        ClientRequestModel clientRequestModel = ClientRequestModel.builder()
                .userName(userName)
                .password(password)
                .age(age)
                .emailAddress(emailAddress)
                .phoneNumber(phoneNumber)
                .countryName(countryName)
                .streetName(streetName)
                .cityName(cityName)
                .provinceName(provinceName)
                .postalCode(postalCode)
                .build();
        return clientRequestModel;


    }







}

