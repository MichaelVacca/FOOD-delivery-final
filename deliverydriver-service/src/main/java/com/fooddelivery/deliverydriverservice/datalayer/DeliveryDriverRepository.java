package com.fooddelivery.deliverydriverservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, Integer> {

    DeliveryDriver findByDeliveryDriverIdentifier_DeliveryDriverId(String deliveryDriverId);
}
