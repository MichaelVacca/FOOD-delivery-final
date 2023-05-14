package com.fooddelivery.deliverydriverservice.presentationlayer;

import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DeliveryDriverControllerIntegrationTest {

    private final String BASE_URI_DELIVERY_DRIVERS = "api/v1/deliveryDrivers";
    private final String VALID_DELIVERY_DRIVER_ID = "bfec8718-92f3-410f-9343-d4dc6b763f10";
    private final String VALID_FIRST_NAME = "Osborne";
    private final String VALID_LAST_NAME = "Ivanyukov";
    private final String VALID_DATE_OF_BIRTH = "February 3, 1998";
    private final String VALID_DESCRIPTION = "Delivery driver with 5 years of experience";
    private final String VALID_EMPLOYEE_SINCE = "March 15, 2017";
    private final String VALID_EMAIL_ADDRESS = "jkunzelmann0@live.com";
    private final String VALID_PHONE_NUMBER = "(129) 7685046";
    private final String VALID_COUNTRY_NAME = "Canada";
    private final String VALID_STREET_NAME = "Donald";
    private final String VALID_CITY_NAME = "Saint-Andre-Avellin";
    private final String VALID_PROVINCE_NAME = "Quebec";
    private final String VALID_POSTAL_CODE = "M5V 2L7";


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DeliveryDriverRepository deliveryDriverRepository;

    @Test
    public void whenDriversExist_thenReturnAllDrivers() {
        Integer expectedNumberOfDeliveryDrivers = 5;

        webTestClient.get()
                .uri(BASE_URI_DELIVERY_DRIVERS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumberOfDeliveryDrivers);

    }

    @Test
    public void WhenGetDriversWithValidDriverId_thenReturnDeliveryDriver() {
        webTestClient.get()
                .uri(BASE_URI_DELIVERY_DRIVERS + "/" + VALID_DELIVERY_DRIVER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.deliveryDriverId").isEqualTo(VALID_DELIVERY_DRIVER_ID)
                .jsonPath("$.firstName").isEqualTo(VALID_FIRST_NAME)
                .jsonPath("$.lastName").isEqualTo(VALID_LAST_NAME)
                .jsonPath("$.dateOfBirth").isEqualTo(VALID_DATE_OF_BIRTH)
                .jsonPath("$.description").isEqualTo(VALID_DESCRIPTION)
                .jsonPath("$.employeeSince").isEqualTo(VALID_EMPLOYEE_SINCE)
                .jsonPath("$.countryName").isEqualTo(VALID_COUNTRY_NAME)
                .jsonPath("$.streetName").isEqualTo(VALID_STREET_NAME)
                .jsonPath("$.cityName").isEqualTo(VALID_CITY_NAME)
                .jsonPath("$.provinceName").isEqualTo(VALID_PROVINCE_NAME)
                .jsonPath("$.postalCode").isEqualTo(VALID_POSTAL_CODE);

    }

    @Test
    public void whenCreateDriverWithValidValues_thenReturnNewDriver() {
        String firstName = "VALID_FIRST_NAME";
        String lastName = "VALID_LAST_NAME";
        String dateOfBirth = "VALID_DATE_OF_BIRTH";
        String description = "VALID_DESCRIPTION";
        String employeeSince = "VALID_EMPLOYEE_SINCE";
        String countryName = "VALID_COUNTRY_NAME";
        String streetName = "VALID_STREET_NAME";
        String cityName = "VALID_CITY_NAME";
        String provinceName = "VALID_PROVINCE_NAME";
        String postalCode = "VALID_POSTAL_CODE";

        DeliveryDriverRequestModel deliveryDriverRequestModel = new DeliveryDriverRequestModel(firstName, lastName, dateOfBirth, description, employeeSince, countryName, streetName, cityName, provinceName, postalCode);

        webTestClient.post()
                .uri(BASE_URI_DELIVERY_DRIVERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryDriverRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.firstName").isEqualTo(firstName)
                .jsonPath("$.lastName").isEqualTo(lastName)
                .jsonPath("$.dateOfBirth").isEqualTo(dateOfBirth)
                .jsonPath("$.description").isEqualTo(description)
                .jsonPath("$.employeeSince").isEqualTo(employeeSince)
                .jsonPath("$.countryName").isEqualTo(countryName)
                .jsonPath("$.streetName").isEqualTo(streetName)
                .jsonPath("$.cityName").isEqualTo(cityName)
                .jsonPath("$.provinceName").isEqualTo(provinceName)
                .jsonPath("$.postalCode").isEqualTo(postalCode);
    }

    @Test
    public void whenUpdateDriverWithValidValues_thenReturnUpdatedDriver() {
        String firstName = "updatedFirstName12";
        String lastName = "updatedLastName12";
        String dateOfBirth = "updatedDateOfBirth12";
        String description = "updatedDescription12";
        String employeeSince = "updatedEmployeeSince12";
        String countryName = "updatedCountryName12";
        String streetName = "updatedStreetName12";
        String cityName = "updatedCityName12";
        String provinceName = "updatedProvinceName12";
        String postalCode = "updatedPostalCode12";


        DeliveryDriverRequestModel deliveryDriverRequestModel = new DeliveryDriverRequestModel(firstName, lastName, dateOfBirth, description, employeeSince, countryName, streetName, cityName, provinceName, postalCode);


        webTestClient.put()
                .uri(BASE_URI_DELIVERY_DRIVERS + "/" + VALID_DELIVERY_DRIVER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryDriverRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.deliveryDriverId").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo(firstName)
                .jsonPath("$.lastName").isEqualTo(lastName)
                .jsonPath("$.dateOfBirth").isEqualTo(dateOfBirth)
                .jsonPath("$.description").isEqualTo(description)
                .jsonPath("$.employeeSince").isEqualTo(employeeSince)
                .jsonPath("$.countryName").isEqualTo(countryName)
                .jsonPath("$.streetName").isEqualTo(streetName)
                .jsonPath("$.cityName").isEqualTo(cityName)
                .jsonPath("$.provinceName").isEqualTo(provinceName)
                .jsonPath("$.postalCode").isEqualTo(postalCode);
    }

    @Test
    public void whenDeleteDriverWithValidDriverId_thenReturnDeletedDriver() {
        webTestClient.delete()
                .uri(BASE_URI_DELIVERY_DRIVERS + "/" + VALID_DELIVERY_DRIVER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void whenDeleteDriverWithInvalidDriverId_thenReturnNotFound() {

        String invalidDriverId = "dfec8718-92f3-410f-9343-d4dc6b763S30";

        webTestClient.delete()
                .uri(BASE_URI_DELIVERY_DRIVERS + "/" + invalidDriverId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Deliver driver with id: " + invalidDriverId + " not found"));

    }

    @Test
    public void whenUpdatedDriver_withNonExistentDriverId_thenReturnNotFound() {
        String invalidDriverId = "jfec8718-92f3-410f-9342-a4dc6b763S70";
        DeliveryDriverRequestModel deliveryDriverRequestModel = createNewRequestModel("firstName", "lastName", "dateOfBirth",
                "description", "employeeSince", "countryName", "streetName", "cityName", "provinceName", "postalCode");
        webTestClient.put()
                .uri(BASE_URI_DELIVERY_DRIVERS + "/" + invalidDriverId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryDriverRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").value(matchesPattern("Deliver driver with id: " + invalidDriverId + " not found"));
    }



    private DeliveryDriverRequestModel createNewRequestModel(String firstName, String lastName,
                                                             String dateOfBirth, String description, String employeeSince, String countryName,
                                                             String streetName, String cityName, String provinceName, String postalCode) {

        DeliveryDriverRequestModel deliveryDriverRequestModel = DeliveryDriverRequestModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .description(description)
                .employeeSince(employeeSince)
                .countryName(countryName)
                .streetName(streetName)
                .cityName(cityName)
                .provinceName(provinceName)
                .postalCode(postalCode)
                .build();

        return deliveryDriverRequestModel;
    }




}