package com.fooddelivery.deliverydriverservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeliveryDriverPersistenceTest {

    private DeliveryDriver preSavedDeliveryDriver;

    @Autowired
    DeliveryDriverRepository deliveryDriverRepository;

    @BeforeEach
    public void setUpTestDb(){
        deliveryDriverRepository.deleteAll();
        preSavedDeliveryDriver = deliveryDriverRepository.save(new DeliveryDriver("testFirstName", "testLastName", "Jan 1st 2000", "testDescription", "Feb 10 2014", new Address("Canada", "1st Street", "Montreal", "Quebec", "S4A 1L1")));

    }

    @Test
    public void addNewDeliveryDriver_ShouldSucceed(){
        String expectedFirstName = "Name";
        String expectedLastName = "LastName";
        String expectedDateOfBirth = "Feb 1st 2000";
        String expectedDescription = "Description";
        String expectedEmployeeSince = "Mar 10 2014";
        Address expectedAddress = new Address("Canada", "15th Street", "Montreal", "Quebec", "S4A 1L1");

        DeliveryDriver newDeliveryDriver = new DeliveryDriver(expectedFirstName, expectedLastName, expectedDateOfBirth, expectedDescription, expectedEmployeeSince, expectedAddress);


        DeliveryDriver savedDriver = deliveryDriverRepository.save(newDeliveryDriver);

        assertNotNull(savedDriver);
        assertNotNull(savedDriver.getId());
        assertEquals(expectedFirstName, savedDriver.getFirstName());
        assertEquals(expectedLastName, savedDriver.getLastName());
        assertEquals(expectedDateOfBirth, savedDriver.getDateOfBirth());
        assertEquals(expectedDescription, savedDriver.getDescription());
        assertEquals(expectedEmployeeSince, savedDriver.getEmployeeSince());
        assertEquals(expectedAddress, savedDriver.getAddress());

    }

    @Test
    public void updateDeliveryDriver_ShouldSucceed(){
        String expectedFirstName = "updateFirstName";
        String expectedLastName = "updateLastName";
        String expectedDateOfBirth = "updateDateOfBirth";
        String expectedDescription = "updateDescription";
        String expectedEmployeeSince = "updateEmployeeSince";
        Address expectedAddress = new Address("Canada","10th Street", "Montreal", "Quebec", "S4A 1L1");

        DeliveryDriver savedDeliveryDriver = deliveryDriverRepository.save(preSavedDeliveryDriver);

        assertNotNull(savedDeliveryDriver);
        assertThat(savedDeliveryDriver, samePropertyValuesAs(preSavedDeliveryDriver));
    }

    @Test
    public void findByDeliveryDriverIdentifier_DeliveryDriverId_Successful(){
        DeliveryDriver found = deliveryDriverRepository.findByDeliveryDriverIdentifier_DeliveryDriverId(preSavedDeliveryDriver.getDeliveryDriverIdentifier().getDeliveryDriverId());

        assertNotNull(found);
        assertThat(preSavedDeliveryDriver, samePropertyValuesAs(found));
    }

    @Test
    public void findDeliveryDriverByDeliveryDriverIdentifier_InvalidDeliveryDriverId_Failed(){
        DeliveryDriver found = deliveryDriverRepository.findByDeliveryDriverIdentifier_DeliveryDriverId(
                preSavedDeliveryDriver.getDeliveryDriverIdentifier().getDeliveryDriverId() + 1);

        assertNull(found);
    }


}