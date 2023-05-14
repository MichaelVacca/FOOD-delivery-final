package com.fooddelivery.apigateway.presentationLayer;




import com.fooddelivery.apigateway.businessLayer.ClientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/clients")
public class ClientsController {
    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<ClientResponseModel[]> getAllClientsAggregate() {
        log.debug("1, Received in api-gateway restaurant controller getAllClientsAggregate");
        return ResponseEntity.ok().body(clientsService.getAllClientsAggregate());
    }

    @GetMapping(
            value = "/{clientId}",
            produces = "application/json"
    )
    ResponseEntity<ClientResponseModel> getClientAggregate(@PathVariable String clientId){
        log.debug("1, Received in api-gateway restaurant controller getClientAggregate with clientId: " + clientId);
        return ResponseEntity.ok().body(clientsService.getClient(clientId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<ClientResponseModel> addClient(@RequestBody ClientRequestModel clientRequestModel){
        log.debug("1, Received in api-gateway restaurant controller addClient");
        return ResponseEntity.status(HttpStatus.CREATED).body(clientsService.addClient(clientRequestModel));
    }

    @PutMapping(value="/{clientId}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateClient(@RequestBody ClientRequestModel clientRequestModel, @PathVariable String clientId){
        log.debug("1, Received in api-gateway restaurant controller updateClient");
        clientsService.updateClient(clientId, clientRequestModel);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value="/{clientId}", produces = "application/json")
    ResponseEntity<Void> deleteClient(@PathVariable String clientId){
        log.debug("1, Received in api-gateway restaurant controller deleteClient");
        clientsService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

}
