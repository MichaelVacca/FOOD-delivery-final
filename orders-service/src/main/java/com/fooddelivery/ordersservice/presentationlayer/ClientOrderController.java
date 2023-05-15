package com.fooddelivery.ordersservice.presentationlayer;

import com.fooddelivery.ordersservice.businesslayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/v1/clients/{clientId}/orders")
@RequiredArgsConstructor
public class ClientOrderController {
    private final OrderService orderService;

    @GetMapping()
    ResponseEntity<List<OrderResponseModel>> getAllOrdersByClientId(@PathVariable String clientId) {
        return ResponseEntity.ok()
                .body(orderService.getAllOrdersAggregateByClientId(clientId));
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponseModel> getOrderById(@PathVariable String clientId, @PathVariable String orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrderByOrderIdAndByClientId(clientId, orderId));
    }

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

    @DeleteMapping("/{orderId}")
    ResponseEntity<Void> deleteOrderById(@PathVariable String clientId, @PathVariable String orderId) {
        orderService.deleteOrderByIdAndClientId(clientId, orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
