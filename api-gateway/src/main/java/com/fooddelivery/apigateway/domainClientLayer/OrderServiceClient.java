package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.presentationLayer.OrderRequestModel;
import com.fooddelivery.apigateway.presentationLayer.OrderResponseModel;
import com.fooddelivery.apigateway.presentationLayer.RestaurantRequestModel;
import com.fooddelivery.apigateway.utils.HttpErrorInfo;
import com.fooddelivery.apigateway.utils.exceptions.InvalidInputException;
import com.fooddelivery.apigateway.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
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
public class OrderServiceClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String ORDER_BASE_URL;
    private final String ORDER_CLIENT_BASE_URL;

    public OrderServiceClient(RestTemplate restTemplate,ObjectMapper objectMapper,
                              @Value("${app.orders-service.host}") String orderServiceHost,
                              @Value("${app.orders-service.port}") String orderServicePort) {


        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.ORDER_BASE_URL = "http://" + orderServiceHost+":"+orderServicePort+"/api/v1/orders";
        this.ORDER_CLIENT_BASE_URL = "http://" + orderServiceHost+":"+orderServicePort+"/api/v1/clients";
    }

    public OrderResponseModel[] getAllOrdersAggregate() {
        OrderResponseModel[] orderResponseModels;
        try{
            String url = ORDER_BASE_URL;
            orderResponseModels =  restTemplate.getForObject(url, OrderResponseModel[].class);
            log.debug("5. Received in API-Gateway Order Service Client getAllOrdersAggregate");
        }
        catch(HttpClientErrorException ex){
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return orderResponseModels;
    }

    public OrderResponseModel getOrderById(String orderId){
        OrderResponseModel orderResponseModel;
        try{
            String url = ORDER_BASE_URL + "/" + orderId;
            orderResponseModel =  restTemplate.getForObject(url, OrderResponseModel.class);
            log.debug("6. Received in API-Gateway Order Service Client getOrderById");
        }
        catch(HttpClientErrorException ex){
            log.debug("6.");
            throw handleHttpClientException(ex);
        }
        return orderResponseModel;
    }
    public OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId) {
        OrderResponseModel orderResponseModel;
        String url = ORDER_CLIENT_BASE_URL + "/" + clientId + "/orders";
        orderResponseModel =
                restTemplate.postForObject(url, orderRequestModel, OrderResponseModel.class);
        return orderResponseModel;
    }
    public void updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel){
        try{
            String url = ORDER_CLIENT_BASE_URL + "/" + clientId + "/orders/" + orderId;
            restTemplate.execute(url, HttpMethod.PUT, requestCallback(orderRequestModel), clientHttpResponse -> null);
            //log.debug("7. Received in API-Gateway Order Service Client updateClientOrder");
        }
        catch(HttpClientErrorException ex){
            log.debug("7.");
            throw handleHttpClientException(ex);
        }
    }

    public void deleteOrder(String orderId){
        try{
            String url = ORDER_BASE_URL + "/" + orderId;
            restTemplate.execute(url, HttpMethod.DELETE, requestCallback(null),   null);
            log.debug("8. Received in API-Gateway Order Service Client deleteOrder");
        }
        catch(HttpClientErrorException ex){
            log.debug("8.");
            throw handleHttpClientException(ex);
        }
    }

    private RequestCallback requestCallback(final OrderRequestModel orderRequestModel) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), orderRequestModel);
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
