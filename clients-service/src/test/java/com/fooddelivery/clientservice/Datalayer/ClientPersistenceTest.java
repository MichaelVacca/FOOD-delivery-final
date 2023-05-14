package com.fooddelivery.clientservice.Datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientPersistenceTest {

    private Client preSavedClient;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    public void setUpTestDb(){
        clientRepository.deleteAll();
        preSavedClient  = clientRepository.save(new Client("testUserName", "testPaa", "21", "test@gmail.com", "123-123-1234", new Address("Canada", "1st Street", "Montreal", "Quebec", "S4A 1L1")));
    }

    @Test
    public void addNewClient_ShouldSucceed(){
        String expectedUserName = "testingName1";
        String expectedPassword = "testingPassword1";
        String expectedAge = "11";
        String expectedEmailAddress = "test@outlook.com";
        String expectedPhoneNumber = "123-123-6789";
        Address expectedAddress = new Address("Canada", "21st Street", "Montreal", "Quebec", "J1D 9U3");

        Client newClient = new Client(expectedUserName, expectedPassword, expectedAge, expectedEmailAddress, expectedPhoneNumber, expectedAddress);

        Client savedClient = clientRepository.save(newClient);

        //assert
        assertNotNull(savedClient);
        assertNotNull(savedClient.getId());
/*        assertNotNull(savedClient.getClientIdentifier().getClientId());*/
        assertEquals(expectedUserName, savedClient.getUserName());
        assertEquals(expectedPassword, savedClient.getPassword());
        assertEquals(expectedAge, savedClient.getAge());
        assertEquals(expectedEmailAddress, savedClient.getEmailAddress());
        assertEquals(expectedPhoneNumber, savedClient.getPhoneNumber());
        assertEquals(expectedAddress, savedClient.getAddress());
    }

    @Test
    public void updateClient_ShouldSucceed(){
        String expectedUserName = "testingName2";
        String expectedPassword = "testingPassword2";
        String expectedAge = "22";
        String expectedEmailAddress = "tes2@outlook.com";
        String expectedPhoneNumber = "456-123-6789";
        Address expectedAddress = new Address("Canada", "12th Street", "Montreal", "Quebec", "testP");

        Client savedClient = clientRepository.save(preSavedClient);

        assertNotNull(savedClient);
        assertThat(savedClient, samePropertyValuesAs(preSavedClient));
    }

    @Test
    public void findClientByClientIdentifier_ClientId_Successful()  {
        Client found = clientRepository.findByClientIdentifier_ClientId(preSavedClient.getClientIdentifier().getClientId());

        assertNotNull(found);
        assertThat(preSavedClient, samePropertyValuesAs(found));

    }
    
    @Test
    public void findClientByClientIdentifier_InvalidClientId_Failed(){
        Client found = clientRepository.findByClientIdentifier_ClientId(preSavedClient.
                getClientIdentifier().getClientId() + 1);
        assertNull(found);
    }



}