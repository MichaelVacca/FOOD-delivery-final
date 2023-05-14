package com.fooddelivery.apigateway.presentationLayer;


import com.fooddelivery.apigateway.businessLayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/clients/{clientId}/orders")

public class ClientOrderController {
    private  OrderService orderService;

    public ClientOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<OrderResponseModel> processClientOrders(@RequestBody OrderRequestModel orderRequestModel,
                                                           @PathVariable String clientId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.processClientOrders(orderRequestModel, clientId));

    }
    @PutMapping(value = "/{orderId}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateClientOrder(@PathVariable String clientId,@PathVariable String orderId, @RequestBody OrderRequestModel orderRequestModel) {
        orderService.updateClientOrder(clientId, orderId, orderRequestModel);
        return ResponseEntity.noContent().build();

    }
}
