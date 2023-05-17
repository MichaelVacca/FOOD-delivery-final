package com.fooddelivery.restaurantservice.Datalayer;

import com.fooddelivery.restaurantservice.businesslayer.Restaurant.RestaurantService;
import com.fooddelivery.restaurantservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestaurantPersistenceTest {


    Restaurant preSavedRestaurant;



    @Autowired
    RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setup(){
        restaurantRepository.deleteAll();
        preSavedRestaurant = restaurantRepository.save(new Restaurant("jakes cafe"));
    }

    //Save
    @Test
    public void saveNewRestaurant(){
        //arange
        String expectedName = "Mcdonalds";
        Restaurant restaurant = new Restaurant(expectedName);


        //act
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);


        //assert

        assertNotNull(savedRestaurant);
        assertNotNull(savedRestaurant.getRestaurantIdentifier().getRestaurantId());
        assertNotNull(savedRestaurant.getId()); //db id
        assertNotNull(savedRestaurant.getRestaurantName());

    }
    @Test
    public void updateRestaurant_ShouldSucceed(){
        //arrange
        String updatedName = "BurgerKing";
        preSavedRestaurant.setRestaurantName(updatedName);

        //act
        Restaurant updatedRestaurant = restaurantRepository.save(preSavedRestaurant);


        //assert
        assertThat(updatedRestaurant,samePropertyValuesAs(preSavedRestaurant)); //assert that updatedResto has the same values as preSavedResto
        assertNotEquals("jakes cafe", updatedRestaurant.getRestaurantName()); //assert that the name is  the new one

    }

    @Test
    public void deleteRestaurant_shouldSucceed(){
        //act
        restaurantRepository.delete(preSavedRestaurant); //deleting presavedResto
        //mamke boolean to store the outcome of the restoRepo exists by id method
        Boolean found = restaurantRepository.existsByRestaurantIdentifier_RestaurantId(
                preSavedRestaurant.getRestaurantIdentifier().getRestaurantId());

        //assert that the output is false for the bool
        assertFalse(found);
    }

    @Test
    public void findByRestaurantIdentifier_RestaurantId_shouldSucceed(){
        //act
        Restaurant found = restaurantRepository.findByRestaurantIdentifier_RestaurantId(
                preSavedRestaurant.getRestaurantIdentifier().getRestaurantId());

        //assert

        assertNotNull(found);//assert that it was found
        assertThat(preSavedRestaurant, samePropertyValuesAs(found)); //make sure they're the same
    }

    @Test
    public void findByInvalidRestaurantIdentifier_RestaurantId_shouldReturnNull(){
        //act
        Restaurant found = restaurantRepository.findByRestaurantIdentifier_RestaurantId(
                preSavedRestaurant.getRestaurantIdentifier().getRestaurantId() + 1);

        //assert

        assertNull(found);//assert that it was found

    }

    @Test
    public void existsByInvalidRestaurantIdentifier_RestaurantId_shouldReturnTrue(){

        boolean found = restaurantRepository.existsByRestaurantIdentifier_RestaurantId(
                preSavedRestaurant.getRestaurantIdentifier().getRestaurantId());

        assertTrue(found);
    }

    @Test
    public void existsByRestaurantIdentifier_RestaurantId_shouldReturnFalse(){
        boolean found = restaurantRepository.existsByRestaurantIdentifier_RestaurantId(
                preSavedRestaurant.getRestaurantIdentifier().getRestaurantId() + 1);

        assertFalse(found);
    }

}