package com.fooddelivery.ordersservice.datalayer;

import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.OrderStatus;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class OrderPersistenceTest {

    Order preSavedOrder;

    @Autowired
    OrdersRepository ordersRepository;

    Items one = new Items("Burger", "Grilled hamburger", 6.99);
    Items two = new Items("French Fries", "Grilled hamburger", 3.99);
    List<Items> items = new ArrayList<>(Arrays.asList(one, two));
/*    @BeforeEach
    public void setup(){
        ordersRepository.deleteAll();
        preSavedOrder = ordersRepository.save(new Order());
    }*/

    @BeforeEach
    public void setup(){
        ordersRepository.deleteAll();
        preSavedOrder = ordersRepository.save(new Order(new OrderIdentifier("order123")));
    }

    @Test
    public void saveNewOrder(){
        //arrange
        String expectedId = "order456";
        Order order = new Order(new OrderIdentifier(expectedId));

        //act
        Order savedOrder = ordersRepository.save(order);

        //assert
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getOrderIdentifier().getOrderId());
        assertNotNull(savedOrder.getId()); //db id
    }

    @Test
    public void updateOrder_ShouldSucceed(){
        //arrange
        String updatedId = "order789";
        preSavedOrder.setOrderIdentifier(new OrderIdentifier(updatedId));

        //act
        Order updatedOrder = ordersRepository.save(preSavedOrder);

        //assert
        assertEquals(updatedOrder, preSavedOrder);
        assertNotEquals("order123", updatedOrder.getOrderIdentifier().getOrderId());
    }

    @Test
    public void deleteOrder_ShouldSucceed(){
        //act
        ordersRepository.delete(preSavedOrder);
        Order found = ordersRepository.findByOrderIdentifier_OrderId(preSavedOrder.getOrderIdentifier().getOrderId());

        //assert
        assertNull(found);
    }

    @Test
    public void findByOrderIdentifier_OrderId_shouldSucceed(){
        //act
        Order found = ordersRepository.findByOrderIdentifier_OrderId(preSavedOrder.getOrderIdentifier().getOrderId());

        //assert
        assertNotNull(found);
        assertEquals(preSavedOrder, found);
    }

    @Test
    public void findByInvalidOrderIdentifier_OrderId_shouldReturnNull(){
        //act
        Order found = ordersRepository.findByOrderIdentifier_OrderId(preSavedOrder.getOrderIdentifier().getOrderId() + "invalid");

        //assert
        assertNull(found);
    }

    @Test
    public void findAllOrdersByClientIdentifier_ClientId_shouldReturnNotEmptyList(){
        //arrange
        String clientId = "client123";
        Order order = new Order(new OrderIdentifier("order456"), new ClientIdentifier(clientId));
        ordersRepository.save(order);

        //act
        List<Order> foundOrders = ordersRepository.findAllOrdersByClientIdentifier_ClientId(clientId);

        //assert
        assertNotNull(foundOrders);
        assertFalse(foundOrders.isEmpty());
        assertTrue(foundOrders.stream().anyMatch(o -> o.getClientIdentifier().getClientId().equals(clientId)));
    }

}