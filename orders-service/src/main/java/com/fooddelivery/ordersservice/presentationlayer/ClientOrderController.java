package com.fooddelivery.ordersservice.presentationlayer;

import com.fooddelivery.ordersservice.businesslayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/clients/{clientId}/orders")
@RequiredArgsConstructor
public class ClientOrderController {
    private final OrderService orderService;

    @PostMapping
    ResponseEntity<OrderResponseModel> processClientOrders(@RequestBody OrderRequestModel orderRequestModel,
                                                           @PathVariable String clientId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.processClientOrders(orderRequestModel, clientId));

    }
    @PutMapping("/{orderId}")
    ResponseEntity<OrderResponseModel> updateClientOrder(@PathVariable String clientId,@PathVariable String orderId, @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }
}
