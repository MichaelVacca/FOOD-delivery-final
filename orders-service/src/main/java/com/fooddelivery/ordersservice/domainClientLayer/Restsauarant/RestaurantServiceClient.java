package com.fooddelivery.ordersservice.domainClientLayer.Restsauarant;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fooddelivery.ordersservice.utils.HttpErrorInfo;
import com.fooddelivery.ordersservice.utils.exceptions.InvalidInputException;
import com.fooddelivery.ordersservice.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class RestaurantServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String RESTAURANT_SERVICE_BASE_URL;
    public RestaurantServiceClient(RestTemplate restTemplate,
                                  ObjectMapper objectMapper,
                                  @Value("${app.restaurant-service.host}") String restaurantServiceHost,
                                  @Value("${app.restaurant-service.port}") String restaurantServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.RESTAURANT_SERVICE_BASE_URL =
                "http://" + restaurantServiceHost+":"+restaurantServicePort+"/api/v1/restaurants";

    }

    public RestaurantResponseModel[] getAllRestaurantsAggregate() {
        RestaurantResponseModel[] restaurantResponseModels;
        try{
            String url = RESTAURANT_SERVICE_BASE_URL;
             restaurantResponseModels =  restTemplate.getForObject(url, RestaurantResponseModel[].class);
            log.debug("5. Received in API-Gateway Restaurant Service Client getAllRestaurantsAggregate");
        }
        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return restaurantResponseModels;
    }

    public RestaurantMenuResponseModel getRestaurantAggregate(String restaurantId){
        RestaurantMenuResponseModel restaurantMenuResponseModel;
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId;
            restaurantMenuResponseModel = restTemplate.getForObject(url, RestaurantMenuResponseModel.class);
            log.debug("5. Received in API-Gateway Restaurant Service Client getRestaurantAggregate with RestaurantMenuResponseModel : " + restaurantMenuResponseModel.getRestaurantId());
        }
        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return restaurantMenuResponseModel;
    }
    public MenuResponseModel getMenuByMenuId(String restaurantId, String menuId){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId + "/" +"menus" + "/" + menuId;
            MenuResponseModel menuResponseModel = restTemplate.getForObject(url, MenuResponseModel.class);
            return menuResponseModel;
        }
        catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public RestaurantMenuResponseModel getMenuByMenuId1(String restaurantId, String menuId){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId + "/" +"menus" + "/" + menuId;
            RestaurantMenuResponseModel restaurantMenuResponseModel = restTemplate.getForObject(url, RestaurantMenuResponseModel.class);
            return restaurantMenuResponseModel;
        }
        catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }


    public RestaurantResponseModel addRestaurantAggregate(RestaurantRequestModel restaurantRequestModel){
        RestaurantResponseModel restaurantResponseModel;

            String url = RESTAURANT_SERVICE_BASE_URL;
            restaurantResponseModel =
                    restTemplate.postForObject(url, restaurantRequestModel, RestaurantResponseModel.class);
            log.debug("5. Received in API_Gateway Restaurant Service Client addRestaurantAggregate with name: " + restaurantRequestModel.getRestaurantName());


/*        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }*/
        return restaurantResponseModel;
    }

    public void updateRestaurantAggregate(String restaurantId, RestaurantRequestModel restaurantRequestModel){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId;
/*            restTemplate.put(url, restaurantRequestModel);*/
            restTemplate.execute(url, HttpMethod.PUT,requestCallback(restaurantRequestModel), clientHttpResponse -> null);

            log.debug("5. Received in API-Gateway Restaurant Service Client updateRestaurantAggregate with name: " + restaurantRequestModel.getRestaurantName());
        }

        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }

    public MenuResponseModel addMenuToRestaurant(String restaurantId, MenuRequestModel newMenu){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId + "/" +"menus";
            MenuResponseModel menuResponseModel = restTemplate.postForObject(url,newMenu,
                    MenuResponseModel.class);
            return menuResponseModel;
        }
        catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public void modifyMenuInRestaurant(String restaurantId, String menuId, MenuRequestModel menuRequestModel){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId + "/menus/" + menuId;
            /*restTemplate.put(url,menuRequestModel);*/
            restTemplate.execute(url, HttpMethod.PUT,requestCallbackMenu(menuRequestModel), clientHttpResponse -> null);
        }
        catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public void deleteMenuInRestaurant(String restaurantId, String menuId){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId + "/menus/" + menuId;
            restTemplate.execute(url, HttpMethod.DELETE,null, null);
        }
        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }

    public void deleteRestaurant(String restaurantId){
        try{
            String url = RESTAURANT_SERVICE_BASE_URL + "/" + restaurantId;
            restTemplate.delete(url);
            restTemplate.execute(url, HttpMethod.DELETE,null,   null);
            log.debug("Received in API-Gateway Restaurant Service Client delete Restaurant with restaurant id: " + restaurantId);
        }
        catch(HttpClientErrorException ex){
            log.debug("5. failed in delete restaurant");
            throw handleHttpClientException(ex);
        }
    }

    private RequestCallback requestCallback(final RestaurantRequestModel restaurantRequestModel) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), restaurantRequestModel);
            clientHttpRequest.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            clientHttpRequest.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }
    private RequestCallback requestCallbackMenu(final MenuRequestModel menuRequestModel) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), menuRequestModel);
            clientHttpRequest.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            clientHttpRequest.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }
    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
