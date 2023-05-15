package com.fooddelivery.apigateway.domainClientLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.apigateway.presentationLayer.ClientRequestModel;
import com.fooddelivery.apigateway.presentationLayer.ClientResponseModel;
import com.fooddelivery.apigateway.utils.HttpErrorInfo;
import com.fooddelivery.apigateway.utils.exceptions.InvalidInputException;
import com.fooddelivery.apigateway.utils.exceptions.NotFoundException;
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

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
public class ClientServiceClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CLIENT_SERVICE_BASE_URL;

    public ClientServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.clients-service.host}") String clientServiceHost,
                               @Value("${app.clients-service.port}") String clientServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CLIENT_SERVICE_BASE_URL = "http://" + clientServiceHost + ":" + clientServicePort + "/api/v1/clients";
    }

    public ClientResponseModel[] getAllClientsAggregate() {
        ClientResponseModel[] clientResponseModels;

            String url = CLIENT_SERVICE_BASE_URL;
            clientResponseModels = restTemplate.getForObject(url, ClientResponseModel[].class);
            log.debug("5. Received in API-Gateway Client Service Client getAllClientsAggregate");

        return clientResponseModels;
    }

    public ClientResponseModel getClient(String clientId) {
        ClientResponseModel clientResponseModel;
        try {
            String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
            clientResponseModel = restTemplate
                    .getForObject(url, ClientResponseModel.class);

            log.debug("5. Received in API-Gateway Client Service Client getClientAggregate with clientResponseModel : " + clientResponseModel.getClientId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return clientResponseModel;
    }

    public ClientResponseModel addClient(ClientRequestModel clientRequestModel) {
        ClientResponseModel clientResponseModel;
        try {
            String url = CLIENT_SERVICE_BASE_URL;
            clientResponseModel = restTemplate
                    .postForObject(url, clientRequestModel, ClientResponseModel.class);
            log.debug("5. Received in API-Gateway Client Service Client addClient");
        }
        catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return clientResponseModel;
    }


    public void updateClient(String clientId, ClientRequestModel clientRequestModel) {
        try{
            String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
            restTemplate.execute(url, HttpMethod.PUT, requestCallback(clientRequestModel), clientHttpResponse -> null);
           // log.debug("5. Received in API-Gateway Client Service Client updateClient");
        }
        catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }



    private RequestCallback requestCallback(final ClientRequestModel clientRequestModel) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), clientRequestModel);
            clientHttpRequest.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            clientHttpRequest.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }


public void deleteClient(String clientId) {
    try{
        String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
        restTemplate.execute(url, HttpMethod.DELETE, null, null);
        //log.debug("5. Received in API-Gateway Client Service Client deleteClient");
    }
    catch (HttpClientErrorException ex) {
        log.debug("5.");
        throw handleHttpClientException(ex);
    }
}


    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }

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
