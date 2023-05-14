package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.domainClientLayer.DeliveryDriverServiceClient;
import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverRequestModel;
import com.fooddelivery.apigateway.presentationLayer.DeliveryDriverResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryDriverServiceImpl implements DeliveryDriverService{

    private DeliveryDriverServiceClient deliveryDriverServiceClient;

    public DeliveryDriverServiceImpl(DeliveryDriverServiceClient deliveryDriverServiceClient) {
        this.deliveryDriverServiceClient = deliveryDriverServiceClient;
    }


    @Override
    public DeliveryDriverResponseModel[] getAllDeliveryDrivers() {
        return deliveryDriverServiceClient.getAllDeliveryDriversAggregate();
    }

    @Override
    public DeliveryDriverResponseModel getDeliveryDriverAggregate(String deliveryDriverId) {
        return deliveryDriverServiceClient.getDeliveryDrivers(deliveryDriverId);
    }

    @Override
    public DeliveryDriverResponseModel addDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel) {
        return deliveryDriverServiceClient.addDeliveryDriver(deliveryDriverRequestModel);
    }

    @Override
    public void updateDeliveryDriver(String deliveryDriverId, DeliveryDriverRequestModel deliveryDriverRequestModel) {
            deliveryDriverServiceClient.updateDeliveryDriver(deliveryDriverId, deliveryDriverRequestModel);
    }

    @Override
    public void deleteDeliveryDriver(String deliveryDriverId) {
        deliveryDriverServiceClient.deleteDeliveryDriver(deliveryDriverId);
    }
}
