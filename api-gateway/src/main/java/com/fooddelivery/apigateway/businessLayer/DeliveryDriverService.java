package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverRequestModel;
import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverResponseModel;

public interface DeliveryDriverService {

    DeliveryDriverResponseModel[] getAllDeliveryDrivers();
    DeliveryDriverResponseModel getDeliveryDriverAggregate(String deliveryDriverId);

    DeliveryDriverResponseModel addDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel);
    void updateDeliveryDriver(String deliveryDriverId, DeliveryDriverRequestModel deliveryDriverRequestModel);
    void deleteDeliveryDriver(String deliveryDriverId);


}
