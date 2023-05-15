package com.fooddelivery.clientservice.presentationlayer;



import com.fooddelivery.clientservice.Businesslayer.ClientService;
import com.fooddelivery.clientservice.Datalayer.Client;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {


    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientResponseModel>> getClients(){
        return ResponseEntity.ok().body(clientService.getAllClients());

    }
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseModel> getClientByClientId(@PathVariable String clientId){
        return ResponseEntity.ok().body(clientService.getClientById(clientId));

    }
    @PostMapping()
    ResponseEntity<ClientResponseModel>  addClient(@RequestBody ClientRequestModel clientRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addNewClient(clientRequestModel));
    }

    @PutMapping("/{clientId}")
    ResponseEntity <ClientResponseModel> updateClient(@RequestBody ClientRequestModel clientRequestModel, @PathVariable String clientId){
        return ResponseEntity.ok().body(clientService.updateExistingClients(clientRequestModel, clientId));
    }

    @DeleteMapping("/{clientId}")
    ResponseEntity <Void> removeClient(@PathVariable String clientId){
        clientService.deleteExistingClient(clientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
