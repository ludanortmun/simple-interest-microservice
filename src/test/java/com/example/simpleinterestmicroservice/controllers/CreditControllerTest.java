package com.example.simpleinterestmicroservice.controllers;


import com.example.simpleinterestmicroservice.TestServerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestServerConfiguration.class})
public class CreditControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void amountIsNegative() {
        CreditDto payload = new CreditDto()
                .setAmount(-1)
                .setRate(0)
                .setTerms(1);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity("http://localhost:" + port + "/credits", payload, CreditResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void amountIsZero() {
        CreditDto payload = new CreditDto()
                .setAmount(0)
                .setRate(0)
                .setTerms(1);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity("http://localhost:" + port + "/credits", payload, CreditResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void rateIsNegative() {
        CreditDto payload = new CreditDto()
                .setAmount(1)
                .setRate(-1)
                .setTerms(1);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity("http://localhost:" + port + "/credits", payload, CreditResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void termsIsNegative() {
        CreditDto payload = new CreditDto()
                .setAmount(1)
                .setRate(0)
                .setTerms(-1);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity(getPostUrl(), payload, CreditResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void termsIsZero() {
        CreditDto payload = new CreditDto()
                .setAmount(1)
                .setRate(0)
                .setTerms(0);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity(getPostUrl(), payload, CreditResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void requestIsValid() {
        CreditDto payload = new CreditDto()
                .setAmount(1)
                .setRate(0)
                .setTerms(1);

        ResponseEntity<CreditResponse> response = testRestTemplate.postForEntity(getPostUrl(), payload, CreditResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void notFoundItem() {
        ResponseEntity<CreditResponse> response = testRestTemplate.getForEntity(getPaymentsUrlBy("NON EXISTING ID"), CreditResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void creditIsFound() {
        CreditDto payload = new CreditDto()
                .setAmount(1)
                .setRate(0)
                .setTerms(1);

        CreditResponse expected = testRestTemplate.postForEntity(getPostUrl(), payload, CreditResponse.class).getBody();


        ResponseEntity<CreditResponse> response = testRestTemplate.getForEntity(getPaymentsUrlBy(expected.getCreditId()), CreditResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        assertEquals(payload.getAmount(), response.getBody().getAmount());
        assertEquals(payload.getRate(), response.getBody().getRate());
        assertEquals(payload.getTerms(), response.getBody().getTerms());
    }


    private String getPostUrl() {
        return "http://localhost:" + port + "/credits";
    }

    private String getPaymentsUrlBy(String id) {
        return "http://localhost:" + port + "/credits/" + id;
    }
}
