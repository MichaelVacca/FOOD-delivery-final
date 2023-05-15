package com.fooddelivery.deliverydriverservice.presentationlayer;



import com.fooddelivery.deliverydriverservice.businessLayer.DeliveryDriverService;
import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/deliveryDrivers")
public class DeliveryDriverController {
    private DeliveryDriverService deliveryDriverService;

    public DeliveryDriverController(DeliveryDriverService deliveryDriverService) {
        this.deliveryDriverService = deliveryDriverService;
    }

    @GetMapping()
    public ResponseEntity<List<DeliveryDriverResponseModel>> getDeliveryDrivers(){
        return ResponseEntity.ok().body(deliveryDriverService.getAllDeliveryDrivers());
       // return deliveryDriverService.getAllDeliveryDrivers();
    }

    @GetMapping("/{deliveryDriverId}")
    public ResponseEntity <DeliveryDriverResponseModel> getDeliveryDriversById(@PathVariable String deliveryDriverId){
        return ResponseEntity.ok().body(deliveryDriverService.getDeliveryDriversById(deliveryDriverId));
        //return deliveryDriverService.getDeliveryDriversById(deliveryDriverId);
    }

    @PostMapping()
    ResponseEntity <DeliveryDriverResponseModel> addDeliveryDriver(@RequestBody DeliveryDriverRequestModel deliveryDriverRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryDriverService.addNewDeliveryDriver(deliveryDriverRequestModel));
    }

    @PutMapping("/{deliveryDriverId}")
    ResponseEntity <DeliveryDriverResponseModel> updateDeliveryDriverInfo(@RequestBody DeliveryDriverRequestModel deliveryDriverRequestModel,
                                                   @PathVariable String deliveryDriverId){
        return ResponseEntity.ok().body(deliveryDriverService.updateExistingDeliveryDriver(deliveryDriverRequestModel,deliveryDriverId));


    }

    @DeleteMapping("/{deliveryDriverId}")
    ResponseEntity <Void> deleteDeliveryDriverById(@PathVariable String deliveryDriverId){

        deliveryDriverService.deleteExistingDeliveryDriver(deliveryDriverId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

















}
