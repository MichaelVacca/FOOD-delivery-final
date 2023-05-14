package com.fooddelivery.restaurantservice.businesslayer.Restaurant;


import com.fooddelivery.restaurantservice.Datalayer.Address;
import com.fooddelivery.restaurantservice.Datalayer.Restaurant;
import com.fooddelivery.restaurantservice.Datalayer.RestaurantRepository;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantRequestModel;
import com.fooddelivery.restaurantservice.Presentationlayer.RestaurantResponseModel;
import com.fooddelivery.restaurantservice.datamapperlayer.RestaurantRequestMapper;
import com.fooddelivery.restaurantservice.datamapperlayer.RestaurantResponseMapper;
import com.fooddelivery.restaurantservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class restaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;
    private RestaurantResponseMapper restaurantResponseMapper;
    private RestaurantRequestMapper restaurantRequestMapper;

    public restaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantResponseMapper restaurantResponseMapper ,RestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantResponseMapper = restaurantResponseMapper;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    @Override
    public List<RestaurantResponseModel> getRestaurants() {
        return restaurantResponseMapper.entityToResponseModelList(restaurantRepository.findAll());
    }

    @Override
    public RestaurantResponseModel getRestaurantsById(String restaurantId) {
        Restaurant existingRestaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);
        if(existingRestaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId +" not found.");
        }

        return restaurantResponseMapper.entityToResponseModel(existingRestaurant);

    }

    @Override
    public RestaurantResponseModel addRestaurant(RestaurantRequestModel restaurantRequestModel) {
        Restaurant restaurant = restaurantRequestMapper.requestModelToEntity(restaurantRequestModel);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return restaurantResponseMapper.entityToResponseModel(savedRestaurant);
    }

/*    @Override
    public Restaurant addRestaurant(Restaurant newRestaurant) {
        return restaurantRepository.save(newRestaurant);
    }*/

/*    @Override
    public RestaurantResponseModel updateRestaurant(RestaurantRequestModel restaurantRequestModel, String restaurantId) {

        Restaurant existingRestaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);
        Restaurant restaurant = restaurantRequestMapper.requestModelToEntity(restaurantRequestModel);

        if(existingRestaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId +" not found.");
        }

        restaurant.setId(existingRestaurant.getId());
        restaurant.setRestaurantIdentifier( existingRestaurant.getRestaurantIdentifier());
        Restaurant restaurantToUpdate = restaurantRepository.save(restaurant);

        return restaurantResponseMapper.entityToResponseModel(restaurantRepository.save(restaurantToUpdate));

    }*/

    @Override
    public RestaurantResponseModel updateRestaurant(RestaurantRequestModel restaurantRequestModel, String restaurantId) {
        Restaurant existingRestaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);
        Restaurant restaurant = restaurantRequestMapper.requestModelToEntity(restaurantRequestModel);

        if (existingRestaurant == null) {
            throw new NotFoundException("Restaurant with id: " + restaurantId + " not found.");
        }

        // Update the existing restaurant's fields
        existingRestaurant.setRestaurantName(restaurant.getRestaurantName());

        existingRestaurant.setCountryName(restaurant.getCountryName());
        existingRestaurant.setStreetName(restaurant.getStreetName());
        existingRestaurant.setProvinceName(restaurant.getProvinceName());
        existingRestaurant.setCityName(restaurant.getCityName());
        existingRestaurant.setPostalCode(restaurant.getPostalCode());

        Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);

        return restaurantResponseMapper.entityToResponseModel(updatedRestaurant);
    }


/*    @Override
    public RestaurantResponseModel addRestaurant(RestaurantRequestModel restaurantRequestModel) {
        Restaurant restaurant = restaurantRequestMapper.requestModelToEntity(restaurantRequestModel);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantResponseMapper.entityToResponseModel(saved);
    }*/

/*    @Override
    public Restaurant updateRestaurant(Restaurant restaurant, String restaurantId) {
        Restaurant existingRestaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);

        if(existingRestaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId +" not found.");
        }

        restaurant.setId(existingRestaurant.getId());
        restaurant.setRestaurantIdentifier( existingRestaurant.getRestaurantIdentifier());

        return restaurantRepository.save(restaurant);

    }*/

    @Override
    public void deleteRestaurant(String restaurantId) {
        //check if the restaurant to be deleted exists
        Restaurant existingRestaurant = restaurantRepository.findByRestaurantIdentifier_RestaurantId(restaurantId);

        if(existingRestaurant == null){
            throw new NotFoundException("Restaurant with id: " + restaurantId +" not found.");
        }
        restaurantRepository.delete(existingRestaurant);

    }

}
