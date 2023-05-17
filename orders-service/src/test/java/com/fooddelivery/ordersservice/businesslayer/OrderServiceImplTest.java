package com.fooddelivery.ordersservice.businesslayer;

import com.fooddelivery.ordersservice.dataMappingLayer.OrderResponseModelMapper;
import com.fooddelivery.ordersservice.datalayer.*;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.MenuResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.RestaurantMenuResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.RestaurantResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.RestaurantServiceClient;
import com.fooddelivery.ordersservice.domainClientLayer.client.ClientResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.client.ClientServiceClient;
import com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver.DeliveryDriverResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver.DeliveryDriverServiceClient;
import com.fooddelivery.ordersservice.presentationlayer.OrderRequestModel;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import com.fooddelivery.ordersservice.utils.exceptions.NoItemsException;
import com.fooddelivery.ordersservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static  org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @MockBean
    ClientServiceClient clientServiceClient;

    @MockBean
    DeliveryDriverServiceClient deliveryDriverServiceClient;

    @MockBean
    RestaurantServiceClient restaurantServiceClient;

    @MockBean
    OrdersRepository ordersRepository;

    @SpyBean
    OrderResponseModelMapper orderResponseModelMapper;

    @Test
    void getAllOrders_ShouldReturnAllOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(buildOrder1(), buildOrder2()); // Create a list of orders
        when(ordersRepository.findAll()).thenReturn(orders); // Mock the repository to return the list of orders

        // Act
        List<OrderResponseModel> orderResponseModels = orderService.getAllOrders();

        // Assert
        assertNotNull(orderResponseModels);
        assertEquals(orders.size(), orderResponseModels.size());

        // Verify that the orderResponseModelMapper.entityToResponseModelList() method is called
        verify(orderResponseModelMapper, times(1)).entityToResponseModelList(orders);
    }

    @Test
    void whenValidCLiendId_DriverId_RestaurantId_MenuId_thenProcessOrderRequest_ShouldSucceed() {
        String diverId = "bfec8718-92f3-410f-9343-d4dc6b763f10";
        String restaurantId = "055b4a20-d29d-46ce-bb46-2c15b8ed6526";
        String menuId = "8fb3d5f0-2ceb-4921-a978-736bb4d278b7";

        Items one = new Items("Burger", "Grilled hamburger", 6.99);
        Items two = new Items("French Fries", "Grilled hamburger", 3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one, two));
        OrderRequestModel orderRequestModel = new OrderRequestModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", 20.0, "bfec8718-92f3-410f-9343-d4dc6b763f10", OrderStatus.ORDER_COMPLETED, items, "20 minutes", LocalDate.of(2022, 12, 12));

        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";

        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel(restaurantId,"testName","testCountry", "test street", "test city", "test province", "test postcode");


        // when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(restaurantMenuResponseModel);

        RestaurantMenuResponseModel restaurantMenuResponseModel = new RestaurantMenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "SavoryStreet", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", "Appetizers");

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "dgalletley0", "b33p3d4", "20", "jkunzelmann0@live.com", "514-123-1234", "canada", "test street", "test city", "test province", "test postcode");

        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel("bfec8718-92f3-410f-9343-d4dc6b763f10", "Osborne", "Ivanyukov", "testDateOfBirth", "testDescription", "testEmployeeSince", "canada", "test street", "test city", "test province", "test postcode");

        MenuResponseModel menuResponseModel = new MenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", "Appetizers", items, 20.0);

        Order order = buildOrder1();
        Order saved = buildOrder1();
        saved.setId("0001");

        when(restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId())).thenReturn(restaurantMenuResponseModel);
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel);

        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(deliveryDriverResponseModel);

        when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(restaurantMenuResponseModel);

        when(ordersRepository.save(any(Order.class))).thenReturn(saved);

        OrderResponseModel orderResponseModel = orderService.processClientOrders(orderRequestModel,clientId);

        assertNotNull(orderResponseModel);
        assertNotNull(orderResponseModel.getOrderId());
        assertEquals(orderRequestModel.getRestaurantId(), orderResponseModel.getRestaurantId());
        assertEquals(orderRequestModel.getMenuId(), orderResponseModel.getMenuId());
        assertEquals(orderRequestModel.getMenuId(), orderResponseModel.getMenuId());
        assertEquals(clientId, orderResponseModel.getClientId());
        assertEquals(orderRequestModel.getDeliveryDriverId(), orderResponseModel.getDeliveryDriverId());
        assertEquals(deliveryDriverResponseModel.getFirstName(), orderResponseModel.getDriverFirstName());
        assertEquals(deliveryDriverResponseModel.getLastName(), orderResponseModel.getDriverLastName());
        assertEquals(clientResponseModel.getUserName(), orderResponseModel.getClientUsername());
        assertEquals(clientResponseModel.getEmailAddress(), orderResponseModel.getClientEmail());
        assertEquals(orderRequestModel.getItems(), orderResponseModel.getItems());
        assertEquals(orderResponseModel.getRestaurantName(), orderResponseModel.getRestaurantName());
        assertEquals(orderResponseModel.getTypeOfMenu(), orderResponseModel.getTypeOfMenu());
        assertEquals(orderRequestModel.getOrderStatus(), orderResponseModel.getOrderStatus());
        assertEquals(orderRequestModel.getTotalPrice(), orderResponseModel.getFinalPrice());
        assertEquals(orderRequestModel.getEstimatedDeliveryTime(), orderResponseModel.getEstimatedDeliveryTime());
        assertEquals(orderRequestModel.getOrderDate(), orderResponseModel.getOrderDate());

        verify(orderResponseModelMapper, times(1)).entityToResponseModel(saved);


        //bfec8718-92f3-410f-9343-d4dc6b763f10', 'Osborne', 'Ivanyukov

        //0a1491af-551b-4b40-87c4-ff268d187b9a','dgalletley0','b33p3d4,, jkunzelmann0@live.com

        //'055b4a20-d29d-46ce-bb46-2c15b8ed6526', '8fb3d5f0-2ceb-4921-a978-736bb4d278b7', 'Appetizers'

    }

        private Order buildOrder1() {

            Items one = new Items("Burger", "Grilled hamburger", 6.99);
            Items two = new Items("French Fries", "Grilled hamburger", 3.99);
            List<Items> items = new ArrayList<>(Arrays.asList(one, two));

            var OrderIdentifier1 = new OrderIdentifier();
            var restaurantIdentifier1 = new RestaurantIdentifier("055b4a20-d29d-46ce-bb46-2c15b8ed6526");
            var menuIdentifier1 = new MenuIdentifier("8fb3d5f0-2ceb-4921-a978-736bb4d278b7");
            var clientIdentifier1 = new ClientIdentifier("0a1491af-551b-4b40-87c4-ff268d187b9a");
            var deliveryDriverIdentifier1 = new DeliveryDriverIdentifier("bfec8718-92f3-410f-9343-d4dc6b763f10");

            var order1 = Order.builder()
                    .orderIdentifier(OrderIdentifier1)
                    .restaurantIdentifier(restaurantIdentifier1)
                    .menuIdentifier(menuIdentifier1)
                    .clientIdentifier(clientIdentifier1)
                    .deliveryDriverIdentifier(deliveryDriverIdentifier1)
                    .orderStatus(OrderStatus.ORDER_COMPLETED)
                    .driverFirstName("Osborne")
                    .driverLastName("Ivanyukov")
                    .clientUsername("dgalletley0")
                    .clientEmail("jkunzelmann0@live.com")
                    .items(items)
                    .restaurantName("Savory Street")
                    .typeOfMenu("Appetizers")
                    .orderStatus(OrderStatus.ORDER_COMPLETED)
                    .finalPrice(20.0)
                    .estimatedDeliveryTime("20 minutes")
                    .orderDate(LocalDate.of(2022,12,12))
                    .build();
            return order1;

    }

    @Test
    void whenValidOrderId_thenGetOrderById_ShouldReturnOrder() {
        String orderId = "aee3b186-9699-42d8-ad47-5fd3b2c63b7a";

        Order order = buildOrder2();
        order.setOrderIdentifier(new OrderIdentifier("aee3b186-9699-42d8-ad47-5fd3b2c63b7a"));

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(order);

        OrderResponseModel orderResponseModel = orderService.getOrderById(orderId);

        assertNotNull(orderResponseModel);
        assertEquals(orderId, orderResponseModel.getOrderId());

        verify(ordersRepository, times(1)).findByOrderIdentifier_OrderId(orderId);
        verify(orderResponseModelMapper, times(1)).entityToResponseModel(order);
    }
    @Test
    void getOrderByNonExistentId_ShouldThrowNotFoundException() {
        String orderId = "1234";
        Order order = buildOrder2();
        order.setOrderIdentifier(new OrderIdentifier("aee3b186-9699-42d8-ad47-5fd3b2c63b7a"));
        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(order);

        OrderResponseModel orderResponseModel = orderService.getOrderById(orderId);

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> orderService.getOrderById(orderId));

    }

    @Test
    void whenValidClientId_thenGetAllOrdersAggregateByClientId_ShouldReturnOrders() {
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";

        Order order1 = buildOrder2();
        Order order2 = buildOrder2();

        List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));

        when(ordersRepository.findAllOrdersByClientIdentifier_ClientId(clientId)).thenReturn(orders);

        List<OrderResponseModel> orderResponseModels = orderService.getAllOrdersAggregateByClientId(clientId);

        assertNotNull(orderResponseModels);
        assertEquals(2, orderResponseModels.size());

        verify(ordersRepository, times(1)).findAllOrdersByClientIdentifier_ClientId(clientId);
        verify(orderResponseModelMapper, times(1)).entityToResponseModelList(orders);
    }

    @Test
    void whenValidOrderIdAndClientId_thenGetOrderByOrderIdAndByClientId_ShouldReturnOrder() {
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";
        String orderId = "aee3b186-9699-42d8-ad47-5fd3b2c63b7a";

        Order order = buildOrder2();

        when(ordersRepository.findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId)).thenReturn(order);

        OrderResponseModel orderResponseModel = orderService.getOrderByOrderIdAndByClientId(clientId, orderId);

        assertNotNull(orderResponseModel);
        assertEquals(orderId, orderResponseModel.getOrderId());

        verify(ordersRepository, times(1)).findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId);
        verify(orderResponseModelMapper, times(1)).entityToResponseModel(order);
    }
/*    @Test
    void getAllOrders_WhenNoOrdersExist_ShouldReturnEmptyList() {
        // Arrange
        List<Order> orders = Collections.emptyList(); // Create an empty list of orders
        when(ordersRepository.findAll()).thenReturn(orders); // Mock the repository to return the empty list

        // Act
        List<OrderResponseModel> orderResponseModels = orderService.getAllOrders();

        // Assert
        assertNotNull(orderResponseModels);
        assertTrue(orderResponseModels.isEmpty());

        // Verify that the orderResponseModelMapper.entityToResponseModelList() method is not called
        verify(orderResponseModelMapper, never()).entityToResponseModelList(orders);
    }*/

    @Test
    void whenValidOrderId_thenDeleteOrder_ShouldDeleteOrder() {
        String orderId = "aee3b186-9699-42d8-ad47-5fd3b2c63b7a";

        Order order = buildOrder2();

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(order);
        doNothing().when(ordersRepository).delete(order);

        orderService.deleteOrder(orderId);

        verify(ordersRepository, times(1)).findByOrderIdentifier_OrderId(orderId);
        verify(ordersRepository, times(1)).delete(order);
    }

    @Test
    void whenValidOrderIdAndClientId_thenDeleteOrderByIdAndClientId_ShouldDeleteOrder() {
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";
        String orderId = "aee3b186-9699-42d8-ad47-5fd3b2c63b7a";

        Order order = buildOrder2();

        when(ordersRepository.findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId)).thenReturn(order);
        doNothing().when(ordersRepository).delete(order);

        orderService.deleteOrderByIdAndClientId(clientId, orderId);

        verify(ordersRepository, times(1)).findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId);
        verify(ordersRepository, times(1)).delete(order);
    }

    @Test
    void whenValidOrderId_thenUpdateClientOrder_ShouldSucceed() {
        // Arrange
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";
        String orderId = "aee3b186-9699-42d8-ad47-5fd3b2c63b7a";
        String diverId = "bfec8718-92f3-410f-9343-d4dc6b763f10";
        String restaurantId = "055b4a20-d29d-46ce-bb46-2c15b8ed6526";
        String menuId = "8fb3d5f0-2ceb-4921-a978-736bb4d278b7";

        Items one = new Items("Burger", "Grilled hamburger", 6.99);
        Items two = new Items("French Fries", "Grilled hamburger", 3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one, two));

        RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel(restaurantId,"testName","testCountry", "test street", "test city", "test province", "test postcode");


        // when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(restaurantMenuResponseModel);

        RestaurantMenuResponseModel restaurantMenuResponseModel = new RestaurantMenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "SavoryStreet", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", "Appetizers");

        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "dgalletley0", "b33p3d4", "20", "jkunzelmann0@live.com", "514-123-1234", "canada", "test street", "test city", "test province", "test postcode");

        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel("bfec8718-92f3-410f-9343-d4dc6b763f10", "Osborne", "Ivanyukov", "testDateOfBirth", "testDescription", "testEmployeeSince", "canada", "test street", "test city", "test province", "test postcode");

        MenuResponseModel menuResponseModel = new MenuResponseModel("055b4a20-d29d-46ce-bb46-2c15b8ed6526", "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", "Appetizers", items, 20.0);



        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "055b4a20-d29d-46ce-bb46-2c15b8ed6526", // restaurantId
                "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", // menuId
                30.0, // total price
                "bfec8718-92f3-410f-9343-d4dc6b763f10", // driverId
                OrderStatus.ORDER_COMPLETED,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 10.99)), // items
                "30 minutes", // estimated delivery time
                LocalDate.of(2023,5,16) // order date
        );

        Order existingOrder = buildOrder2(); // Assuming buildOrder1() returns an existing order

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(existingOrder);
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel); // Assuming clientResponseModel is defined and valid
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(deliveryDriverResponseModel); // Assuming deliveryDriverResponseModel is defined and valid
        when(restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId())).thenReturn(restaurantMenuResponseModel); // Assuming restaurantMenuResponseModel is defined and valid
        when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(restaurantMenuResponseModel); // Assuming restaurantMenuResponseModel is defined and valid
        when(ordersRepository.save(any(Order.class))).thenReturn(existingOrder);

        // Act
        OrderResponseModel orderResponseModel = orderService.updateClientOrder(clientId, orderId, orderRequestModel);

        // Assert
        assertNotNull(orderResponseModel);
        assertEquals(orderId, orderResponseModel.getOrderId());
        assertEquals(orderRequestModel.getRestaurantId(), orderResponseModel.getRestaurantId());
        assertEquals(orderRequestModel.getMenuId(), orderResponseModel.getMenuId());
        assertEquals(clientId, orderResponseModel.getClientId());
        assertEquals(orderRequestModel.getDeliveryDriverId(), orderResponseModel.getDeliveryDriverId());
        assertEquals(deliveryDriverResponseModel.getFirstName(), orderResponseModel.getDriverFirstName());
        assertEquals(deliveryDriverResponseModel.getLastName(), orderResponseModel.getDriverLastName());
        assertEquals(clientResponseModel.getUserName(), orderResponseModel.getClientUsername());
        assertEquals(clientResponseModel.getEmailAddress(), orderResponseModel.getClientEmail());
        assertEquals(orderRequestModel.getItems().size(), orderResponseModel.getItems().size());
        assertEquals(orderRequestModel.getRestaurantId(), orderResponseModel.getRestaurantId());
        assertEquals(orderResponseModel.getTypeOfMenu(), orderResponseModel.getTypeOfMenu());
        assertEquals(orderRequestModel.getOrderStatus(), orderResponseModel.getOrderStatus());
        assertEquals(orderRequestModel.getTotalPrice(), orderResponseModel.getFinalPrice());
        assertEquals(orderRequestModel.getEstimatedDeliveryTime(), orderResponseModel.getEstimatedDeliveryTime());
        assertEquals(orderRequestModel.getOrderDate(), orderResponseModel.getOrderDate());

        verify(orderResponseModelMapper, times(1)).entityToResponseModel(existingOrder);
    }

    @Test
    void whenOrderNotFound_thenThrowNotFoundException() {
        // Arrange
        String orderId = "invalid-order-id";
        String clientId = "valid-client-id";

        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "055b4a20-d29d-46ce-bb46-2c15b8ed6526", // restaurantId
                "8fb3d5f0-2ceb-4921-a978-736bb4d278b7", // menuId
                30.0, // total price
                "bfec8718-92f3-410f-9343-d4dc6b763f10", // driverId
                OrderStatus.ORDER_COMPLETED,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 10.99)), // items
                "30 minutes", // estimated delivery time
                LocalDate.of(2023,5,16) // order date
        );

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(null);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }


    @Test
    void whenClientNotFound_thenThrowNotFoundException() {
        // Arrange
        String orderId = "valid-order-id";
        String clientId = "invalid-client-id";
        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "valid-restaurant-id",
                "valid-menu-id",
                100.0,
                "valid-driver-id",
                OrderStatus.ORDER_CANCELLED,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 50.0)),
                "30 minutes",
                LocalDate.now()
        );


        Order existingOrder = buildOrder2();
        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(existingOrder);
        when(clientServiceClient.getClient(clientId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }

    @Test
    void whenDeliveryDriverNotFound_thenThrowNotFoundException() {
        // Arrange
        String orderId = "valid-order-id";
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "valid-restaurant-id",
                "valid-menu-id",
                100.0,
                "invalid-driver-id",
                OrderStatus.MAKING_ORDER,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 50.0)),
                "30 minutes",
                LocalDate.now()
        );


        Order existingOrder = buildOrder2();
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(existingOrder);
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel);
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }

    @Test
    void whenRestaurantNotFound_thenThrowNotFoundException() {
        // Arrange
        String orderId = "valid-order-id";
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "invalid-restaurant-id",
                "valid-menu-id",
                100.0,
                "valid-driver-id",
                OrderStatus.DELIVERY_COMPLETED,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 50.0)),
                "30 minutes",
                LocalDate.now()
        );


        Order existingOrder = buildOrder2();
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel();
        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(existingOrder);
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel);
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(deliveryDriverResponseModel);
        when(restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId())).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }
    @Test
    void whenMenuNotFound_thenThrowNotFoundException() {
        // Arrange
        String orderId = "valid-order-id";
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = new OrderRequestModel(
                "valid-restaurant-id",
                "invalid-menu-id",
                100.0,
                "valid-driver-id",
                OrderStatus.DELIVERY_COMPLETED,
                Arrays.asList(new Items("Pizza", "Cheese pizza", 50.0)),
                "30 minutes",
                LocalDate.now()
        );

        Order existingOrder = buildOrder2();
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        DeliveryDriverResponseModel deliveryDriverResponseModel = new DeliveryDriverResponseModel();
        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(existingOrder);
        when(clientServiceClient.getClient(clientId)).thenReturn(clientResponseModel);
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(deliveryDriverResponseModel);
        when(restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId())).thenReturn(new RestaurantMenuResponseModel());
        when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.updateClientOrder(clientId, orderId, orderRequestModel));
    }

    @Test
    void whenClientNotFound_thenThrowNotFoundException1() {
        // Arrange
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = createValidOrderRequestModel();

        when(clientServiceClient.getClient(clientId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.processClientOrders(orderRequestModel, clientId));
    }

    @Test
    void whenDeliveryDriverNotFound_thenThrowNotFoundException1() {
        // Arrange
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = createValidOrderRequestModel();

        when(clientServiceClient.getClient(clientId)).thenReturn(createValidClientResponseModel());
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.processClientOrders(orderRequestModel, clientId));
    }

    @Test
    void whenMenuNotFound_thenThrowNotFoundException1() {
        // Arrange
        String clientId = "valid-client-id";
        OrderRequestModel orderRequestModel = createValidOrderRequestModel();

        when(clientServiceClient.getClient(clientId)).thenReturn(createValidClientResponseModel());
        when(deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId())).thenReturn(createValidDeliveryDriverResponseModel());
        when(restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId())).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.processClientOrders(orderRequestModel, clientId));
    }
    @Test
    void whenInvalidOrderId_thenThrowNotFoundException() {
        String orderId = "invalid-order-id";

        when(ordersRepository.findByOrderIdentifier_OrderId(orderId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(orderId));

        verify(ordersRepository, times(1)).findByOrderIdentifier_OrderId(orderId);
        verify(ordersRepository, never()).delete(any(Order.class));
    }

    @Test
    void whenInvalidOrderIdAndClientId_thenThrowNotFoundException() {
        String clientId = "0a1491af-551b-4b40-87c4-ff268d187b9a";
        String orderId = "invalid-order-id";

        when(ordersRepository.findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderService.deleteOrderByIdAndClientId(clientId, orderId));

        verify(ordersRepository, times(1)).findOrderByClientIdentifier_ClientIdAndOrderIdentifier_OrderId(clientId, orderId);
        verify(ordersRepository, never()).delete(any(Order.class));
    }

    private Order buildOrder2() {

        Items one = new Items("Burger", "Grilled hamburger", 6.99);
        Items two = new Items("French Fries", "Grilled hamburger", 3.99);
        List<Items> items = new ArrayList<>(Arrays.asList(one, two));

        var OrderIdentifier1 = new OrderIdentifier("aee3b186-9699-42d8-ad47-5fd3b2c63b7a");
        var restaurantIdentifier1 = new RestaurantIdentifier("055b4a20-d29d-46ce-bb46-2c15b8ed6526");
        var menuIdentifier1 = new MenuIdentifier("8fb3d5f0-2ceb-4921-a978-736bb4d278b7");
        var clientIdentifier1 = new ClientIdentifier("0a1491af-551b-4b40-87c4-ff268d187b9a");
        var deliveryDriverIdentifier1 = new DeliveryDriverIdentifier("bfec8718-92f3-410f-9343-d4dc6b763f10");

        var order1 = Order.builder()
                .orderIdentifier(OrderIdentifier1)
                .restaurantIdentifier(restaurantIdentifier1)
                .menuIdentifier(menuIdentifier1)
                .clientIdentifier(clientIdentifier1)
                .deliveryDriverIdentifier(deliveryDriverIdentifier1)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .driverFirstName("Osborne")
                .driverLastName("Ivanyukov")
                .clientUsername("dgalletley0")
                .clientEmail("jkunzelmann0@live.com")
                .items(items)
                .restaurantName("Savory Street")
                .typeOfMenu("Appetizers")
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .finalPrice(20.0)
                .estimatedDeliveryTime("20 minutes")
                .orderDate(LocalDate.of(2022,12,12))
                .build();
        return order1;

    }
    private RestaurantMenuResponseModel createValidRestaurantMenuResponseModel() {
        String restaurantId = "valid-restaurant-id";
        String restaurantName = "Test Restaurant";
        String menuId = "valid-menu-id";
        String typeOfMenu = "Test Menu Type";

        return new RestaurantMenuResponseModel(restaurantId, restaurantName, menuId, typeOfMenu);
    }


    private OrderRequestModel createValidOrderRequestModel() {
        String restaurantId = "valid-restaurant-id";
        String menuId = "valid-menu-id";
        Double totalPrice = 100.0;
        String deliveryDriverId = "valid-driver-id";
        OrderStatus orderStatus = OrderStatus.DELIVERY_COMPLETED;
        List<Items> items = new ArrayList<>();
        items.add(new Items("Pizza", "Cheese pizza", 50.0));
        String estimatedDeliveryTime = "30 minutes";
        LocalDate orderDate = LocalDate.now();

        return OrderRequestModel.builder()
                .restaurantId(restaurantId)
                .menuId(menuId)
                .totalPrice(totalPrice)
                .deliveryDriverId(deliveryDriverId)
                .orderStatus(orderStatus)
                .items(items)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .orderDate(orderDate)
                .build();
    }

    private ClientResponseModel createValidClientResponseModel() {
        String clientId = "valid-client-id";
        String userName = "testUser";
        String password = "testPassword";
        String age = "30";
        String emailAddress = "test@example.com";
        String phoneNumber = "123-456-7890";
        String countryName = "Test Country";
        String streetName = "Test Street";
        String cityName = "Test City";
        String provinceName = "Test Province";
        String postalCode = "12345";

        return new ClientResponseModel(clientId, userName, password, age, emailAddress, phoneNumber, countryName, streetName, cityName, provinceName, postalCode);
    }

    private DeliveryDriverResponseModel createValidDeliveryDriverResponseModel() {
        String deliveryDriverId = "valid-driver-id";
        String firstName = "John";
        String lastName = "Doe";
        String dateOfBirth = "1990-01-01";
        String description = "Experienced driver";
        String employeeSince = "2015-01-01";
        String countryName = "Test Country";
        String streetName = "Test Street";
        String cityName = "Test City";
        String provinceName = "Test Province";
        String postalCode = "12345";

        return new DeliveryDriverResponseModel(deliveryDriverId, firstName, lastName, dateOfBirth, description, employeeSince, countryName, streetName, cityName, provinceName, postalCode);
    }





}