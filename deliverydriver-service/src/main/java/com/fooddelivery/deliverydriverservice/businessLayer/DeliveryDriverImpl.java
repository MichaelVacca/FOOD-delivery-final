package com.fooddelivery.deliverydriverservice.businessLayer;


import com.fooddelivery.deliverydriverservice.dataMapperlayer.DeliveryDriverRequestMapper;
import com.fooddelivery.deliverydriverservice.dataMapperlayer.DeliveryDriverResponseMapper;
import com.fooddelivery.deliverydriverservice.datalayer.Address;
import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriverRepository;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverRequestModel;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverResponseModel;
import com.fooddelivery.deliverydriverservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryDriverImpl implements DeliveryDriverService{

    private DeliveryDriverRepository deliveryDriverRepository;
    private DeliveryDriverResponseMapper deliveryDriverResponseMapper;
    private DeliveryDriverRequestMapper deliveryDriverRequestMapper;

    public DeliveryDriverImpl(DeliveryDriverRepository deliveryDriverRepository, DeliveryDriverResponseMapper deliveryDriverResponseMapper, DeliveryDriverRequestMapper
            deliveryDriverRequestMapper) {
        this.deliveryDriverRepository = deliveryDriverRepository;
        this.deliveryDriverResponseMapper = deliveryDriverResponseMapper;
        this.deliveryDriverRequestMapper = deliveryDriverRequestMapper;
    }

    @Override
    public List<DeliveryDriverResponseModel> getAllDeliveryDrivers() {
        return deliveryDriverResponseMapper.entityListToResponseModelList(deliveryDriverRepository.findAll());
    }


    @Override
    public DeliveryDriverResponseModel getDeliveryDriversById(String deliveryDriverId) {
        return deliveryDriverResponseMapper.entityToResponseModel(deliveryDriverRepository.findByDeliveryDriverIdentifier_DeliveryDriverId(deliveryDriverId));
    }

    @Override
    public DeliveryDriverResponseModel addNewDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel) {
        DeliveryDriver deliveryDriver = deliveryDriverRequestMapper.entityToRequestModel(deliveryDriverRequestModel);
        Address address = new Address(deliveryDriverRequestModel.getCountryName(), deliveryDriverRequestModel.getStreetName(),deliveryDriverRequestModel.getCityName(),deliveryDriverRequestModel.getProvinceName(),deliveryDriverRequestModel.getPostalCode());

        deliveryDriver.setAddress(address);
        DeliveryDriver newDeliveryDriver = deliveryDriverRepository.save(deliveryDriver);
        DeliveryDriverResponseModel deliveryDriverResponseModel = deliveryDriverResponseMapper.entityToResponseModel(newDeliveryDriver);
        return deliveryDriverResponseModel;
    }

    @Override
    public DeliveryDriverResponseModel updateExistingDeliveryDriver(DeliveryDriverRequestModel deliveryDriverRequestModel, String deliveryDriverId) {
        DeliveryDriver deliveryDriver = deliveryDriverRequestMapper.entityToRequestModel(deliveryDriverRequestModel);
        DeliveryDriver existingDeliveryDriver = deliveryDriverRepository.findByDeliveryDriverIdentifier_DeliveryDriverId(deliveryDriverId);

        if(existingDeliveryDriver == null){
            throw new NotFoundException("Deliver driver with id: " + deliveryDriverId +" not found");
        }

        deliveryDriver.setId(existingDeliveryDriver.getId());
        deliveryDriver.setDeliveryDriverIdentifier(existingDeliveryDriver.getDeliveryDriverIdentifier());

        Address address = new Address(deliveryDriverRequestModel.getCountryName(), deliveryDriverRequestModel.getStreetName(),deliveryDriverRequestModel.getCityName(),deliveryDriverRequestModel.getProvinceName(),deliveryDriverRequestModel.getPostalCode());
        deliveryDriver.setAddress(address);
        DeliveryDriver updatedDeliveryDriver = deliveryDriverRepository.save(deliveryDriver);
        DeliveryDriverResponseModel deliveryDriverResponseModel = deliveryDriverResponseMapper.entityToResponseModel(updatedDeliveryDriver);
        return deliveryDriverResponseModel;


    }

    @Override
    public void deleteExistingDeliveryDriver(String deliveryDriverId) {

        DeliveryDriver existingDeliveryDriver = deliveryDriverRepository.findByDeliveryDriverIdentifier_DeliveryDriverId(deliveryDriverId);

        if(existingDeliveryDriver == null){
            throw new NotFoundException("Deliver driver with id: " + deliveryDriverId +" not found");
        }
        deliveryDriverRepository.delete(existingDeliveryDriver);
    }


}
