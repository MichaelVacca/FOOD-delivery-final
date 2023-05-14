package com.fooddelivery.apigateway.presentationLayer;


import com.fooddelivery.apigateway.businessLayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private  OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping(produces = "application/json")
    ResponseEntity<OrderResponseModel[]> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrdersAggregate());
    }
    @GetMapping(value = "/{orderId}", produces = "application/json")
    ResponseEntity<OrderResponseModel> getOrderById(@PathVariable  String orderId) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }
    @DeleteMapping(value = "/{orderId}", produces = "application/json")
    ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
