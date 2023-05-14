package com.fooddelivery.ordersservice.presentationlayer;

import com.fooddelivery.ordersservice.businesslayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<List<OrderResponseModel>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }
}
