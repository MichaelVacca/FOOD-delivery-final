package com.fooddelivery.deliverydriverservice.businessLayer;



import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverRequestModel;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverResponseModel;

import java.util.List;

public interface DeliveryDriverService {

    List<DeliveryDriverResponseModel> getAllDeliveryDrivers();

  //  DeliveryDriver getDeliveryDriversById(String deliveryDriverId);
    DeliveryDriverResponseModel getDeliveryDriversById(String deliveryDriverId);

    //DeliveryDriver addNewDeliveryDriver(DeliveryDriver newDeliveryDriver);
    DeliveryDriverResponseModel addNewDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel);

   // DeliveryDriver updateExistingDeliveryDriver(DeliveryDriver deliveryDriver, String deliveryDriverId );
  DeliveryDriverResponseModel updateExistingDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel, String deliveryDriverId);

    void deleteExistingDeliveryDriver(String deliveryDriverId);
}
