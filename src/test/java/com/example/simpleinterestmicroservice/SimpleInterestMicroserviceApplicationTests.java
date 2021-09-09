package com.example.simpleinterestmicroservice;

import com.example.simpleinterestmicroservice.controllers.CreditController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestServerConfiguration.class})
class SimpleInterestMicroserviceApplicationTests {

    @Autowired
    private CreditController creditController;

    void contextLoads() {
        assertNotNull(creditController);
    }

}
