package com.fooddelivery.ordersservice.businesslayer;

import com.fooddelivery.ordersservice.dataMappingLayer.OrderResponseModelMapper;
import com.fooddelivery.ordersservice.datalayer.*;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.*;
import com.fooddelivery.ordersservice.domainClientLayer.Restsauarant.OrderStatus;
import com.fooddelivery.ordersservice.domainClientLayer.client.ClientResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.client.ClientServiceClient;
import com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver.DeliveryDriverResponseModel;
import com.fooddelivery.ordersservice.domainClientLayer.deliveryDriver.DeliveryDriverServiceClient;
import com.fooddelivery.ordersservice.presentationlayer.OrderRequestModel;
import com.fooddelivery.ordersservice.presentationlayer.OrderResponseModel;
import com.fooddelivery.ordersservice.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderResponseModelMapper orderResponseModelMapper;
    private final ClientServiceClient clientServiceClient;
    private final DeliveryDriverServiceClient deliveryDriverServiceClient;
    private final RestaurantServiceClient restaurantServiceClient;

    @Override
    public List<OrderResponseModel> getAllOrders() {
        List<Order> orders = ordersRepository.findAll();
        return orderResponseModelMapper.entityToResponseModelList(orders);
    }

    @Override
    public OrderResponseModel processClientOrders(OrderRequestModel orderRequestModel, String clientId) {
        ClientResponseModel clientResponseModel = clientServiceClient.getClient(clientId);
        if(clientResponseModel == null){
             throw new NotFoundException("Client not found with id : " + clientId);
        }

        log.info("Driver ID from orderRequestModel: " + orderRequestModel.getDeliveryDriverId());
        DeliveryDriverResponseModel deliveryDriverResponseModel = deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId());
        if(deliveryDriverResponseModel == null){
            throw new NotFoundException("Delivery driver not found with id : " + orderRequestModel.getDeliveryDriverId());
        }

        RestaurantMenuResponseModel menuResponseModel = restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId());
        if(menuResponseModel == null){
            throw new NotFoundException("Menu not found with id : " + orderRequestModel.getMenuId());
        }

        RestaurantMenuResponseModel restaurantResponseModel = restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId());

        //build
        Order order = Order.builder()
                .orderIdentifier(new OrderIdentifier())
                .restaurantIdentifier(new RestaurantIdentifier(restaurantResponseModel.getRestaurantId()))
                .menuIdentifier(new MenuIdentifier(menuResponseModel.getMenuId()))
                .clientIdentifier(new ClientIdentifier(clientResponseModel.getClientId()))
                .deliveryDriverIdentifier(new DeliveryDriverIdentifier(deliveryDriverResponseModel.getDeliveryDriverId()))
                .driverFirstName(deliveryDriverResponseModel.getFirstName())
                .driverLastName(deliveryDriverResponseModel.getLastName())
                .clientUsername(clientResponseModel.getUserName())
                .clientEmail(clientResponseModel.getEmailAddress())
                .finalPrice(orderRequestModel.getTotalPrice())
                .orderStatus(orderRequestModel.getOrderStatus())
                .items(orderRequestModel.getItems())
                .restaurantName(restaurantResponseModel.getRestaurantName())
                .typeOfMenu(menuResponseModel.getTypeOfMenu())
                .estimatedDeliveryTime(orderRequestModel.getEstimatedDeliveryTime())

                .orderDate(orderRequestModel.getOrderDate())
                .build();


        Order saved = ordersRepository.save(order);

        OrderStatus orderStatus = OrderStatus.AWAITING_PICKUP;
                switch(saved.getOrderStatus()){
                    case PROCCESSING_ORDER, MAKING_ORDER -> orderStatus = OrderStatus.MAKING;
                    case FOOD_READY -> orderStatus = OrderStatus.AWAITING_PICKUP;
                    case ORDER_COMPLETED -> orderStatus = OrderStatus.IN_TRANSIT;
                    case DELIVERY_COMPLETED -> orderStatus = OrderStatus.DELIVERED;
                    case ORDER_CANCELLED -> orderStatus = OrderStatus.REFUNDED;
                }

        MenuRequestModel menuRequestModel = MenuRequestModel.builder()
                .restaurantId(menuResponseModel.getRestaurantId())
                .menuId(menuResponseModel.getMenuId())
                .typeOfMenu(menuResponseModel.getTypeOfMenu())
                .items(orderRequestModel.getItems())
                .totalPrice(orderRequestModel.getTotalPrice())
                .build();

                restaurantServiceClient.modifyMenuInRestaurant(menuRequestModel.getRestaurantId(), menuRequestModel.getMenuId(),menuRequestModel);
                return orderResponseModelMapper.entityToResponseModel(saved);

    }
    @Override
    public OrderResponseModel getOrderById(String orderId) {
        Order order = ordersRepository.findByOrderIdentifier_OrderId(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with id : " + orderId);
        }
        return orderResponseModelMapper.entityToResponseModel(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = ordersRepository.findByOrderIdentifier_OrderId(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with id : " + orderId);
        }
        ordersRepository.delete(order);
    }

    @Override
    public OrderResponseModel updateClientOrder(String clientId, String orderId, OrderRequestModel orderRequestModel) {
        // Fetch the existing order
/*        Order order = ordersRepository.findById(OrderResponseModel)
                .orElseThrow(() -> new NotFoundException("Order not found with id : " + orderId));*/
        Order order = ordersRepository.findByOrderIdentifier_OrderId(orderId);

        // Fetch the client, driver, and menu data
        ClientResponseModel clientResponseModel = clientServiceClient.getClient(clientId);
        if (clientResponseModel == null) {
            throw new NotFoundException("Client not found with id : " + clientId);
        }

        DeliveryDriverResponseModel deliveryDriverResponseModel = deliveryDriverServiceClient.getDeliveryDrivers(orderRequestModel.getDeliveryDriverId());
        if (deliveryDriverResponseModel == null) {
            throw new NotFoundException("Delivery driver not found with id : " + orderRequestModel.getDeliveryDriverId());
        }

        RestaurantMenuResponseModel restaurantResponseModel = restaurantServiceClient.getRestaurantAggregate(orderRequestModel.getRestaurantId());
        if (restaurantResponseModel == null) {
            throw new NotFoundException("Menu not found with id : " + orderRequestModel.getMenuId());
        }
        RestaurantMenuResponseModel menuResponseModel = restaurantServiceClient.getMenuByMenuId1(orderRequestModel.getRestaurantId(), orderRequestModel.getMenuId());
        if(menuResponseModel == null){
            throw new NotFoundException("Menu not found with id : " + orderRequestModel.getMenuId());
        }

        // Update the order data
        order.setClientIdentifier(new ClientIdentifier(clientResponseModel.getClientId()));
        order.setDeliveryDriverIdentifier(new DeliveryDriverIdentifier(deliveryDriverResponseModel.getDeliveryDriverId()));
        order.setRestaurantIdentifier(new RestaurantIdentifier(restaurantResponseModel.getRestaurantId()));
        order.setMenuIdentifier(new MenuIdentifier(menuResponseModel.getMenuId()));
        order.setDriverFirstName(deliveryDriverResponseModel.getFirstName());
        order.setDriverLastName(deliveryDriverResponseModel.getLastName());
        order.setClientUsername(clientResponseModel.getUserName());
        order.setClientEmail(clientResponseModel.getEmailAddress());
        order.setFinalPrice(orderRequestModel.getTotalPrice());
        order.setOrderStatus(orderRequestModel.getOrderStatus());
        order.setItems(orderRequestModel.getItems());
        order.setRestaurantName(restaurantResponseModel.getRestaurantName());
        order.setTypeOfMenu(menuResponseModel.getTypeOfMenu());
        order.setEstimatedDeliveryTime(orderRequestModel.getEstimatedDeliveryTime());
        order.setOrderDate(orderRequestModel.getOrderDate());
        // Save the updated order
        Order updatedOrder = ordersRepository.save(order);

        // Update the menu in the restaurant
        MenuRequestModel menuRequestModel = MenuRequestModel.builder()
                .restaurantId(menuResponseModel.getRestaurantId())
                .menuId(menuResponseModel.getMenuId())
                .typeOfMenu(menuResponseModel.getTypeOfMenu())
                .items(orderRequestModel.getItems())
                .totalPrice(orderRequestModel.getTotalPrice())
                .build();

        restaurantServiceClient.modifyMenuInRestaurant(menuResponseModel.getRestaurantId(), menuRequestModel.getMenuId(),menuRequestModel);

        return orderResponseModelMapper.entityToResponseModel(updatedOrder);
    }

}
