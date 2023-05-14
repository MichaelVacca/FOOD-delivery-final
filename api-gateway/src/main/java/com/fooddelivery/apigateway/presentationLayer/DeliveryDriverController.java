package com.fooddelivery.apigateway.presentationLayer;



import com.fooddelivery.apigateway.businessLayer.DeliveryDriverService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/v1/deliveryDrivers")
public class DeliveryDriverController {

    private DeliveryDriverService deliveryDriverService;

    public DeliveryDriverController(DeliveryDriverService deliveryDriverService) {
        this.deliveryDriverService = deliveryDriverService;
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<DeliveryDriverResponseModel[]> getAllDeliveryDrivers() {
        log.debug("1, Received in api-gateway deliveryDriversController getAllDeliveryDrivers");
        return ResponseEntity.ok().body(deliveryDriverService.getAllDeliveryDrivers());
    }
    @GetMapping(value = "/{deliveryDriverId}", produces = "application/json")
    ResponseEntity<DeliveryDriverResponseModel> getDeliveryDriverAggregate(@PathVariable String deliveryDriverId) {
        log.debug("1, Received in api-gateway deliveryDriversController getDeliveryDriverAggregate with deliverDriverId: " + deliveryDriverId);
        return  ResponseEntity.ok().body(deliveryDriverService.getDeliveryDriverAggregate(deliveryDriverId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<DeliveryDriverResponseModel> addDeliveryDriver(@RequestBody DeliveryDriverRequestModel deliveryDriverRequestModel) {
        log.debug("1, Received in api-gateway deliveryDriversController addDeliveryDriver");
        return  ResponseEntity.status(HttpStatus.CREATED).body(deliveryDriverService.addDeliveryDriver(deliveryDriverRequestModel));
    }

    @PutMapping(value="/{deliveryDriverId}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateDeliveryDriver(@PathVariable String deliveryDriverId, @RequestBody DeliveryDriverRequestModel deliveryDriverRequestModel) {
        log.debug("1, Received in api-gateway deliveryDriversController updateDeliveryDriver");
        deliveryDriverService.updateDeliveryDriver(deliveryDriverId, deliveryDriverRequestModel);
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{deliveryDriverId}", produces = "application/json")
    ResponseEntity<Void> deleteDeliveryDriver(@PathVariable String deliveryDriverId) {
        log.debug("1, Received in api-gateway deliveryDriversController deleteDeliveryDriver");
        deliveryDriverService.deleteDeliveryDriver(deliveryDriverId);
        return  ResponseEntity.noContent().build();
    }
}
