package com.example.simpleinterestmicroservice.application;

import com.example.simpleinterestmicroservice.domain.Credit;
import com.example.simpleinterestmicroservice.domain.CreditRepository;
import com.example.simpleinterestmicroservice.infrastructure.CreditSqlRepository;
import com.example.simpleinterestmicroservice.infrastructure.InMemoryCreditDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditApplicationTest {
    private Clock clock;
    private CreditRepository repository;
    private CreditApplication creditApplication;

    @BeforeEach
    public void setup() {
        clock = Clock.fixed(Instant.parse("2021-09-08T14:21:50Z"), ZoneOffset.UTC);
        repository = new CreditSqlRepository(InMemoryCreditDatabase.getDataSource());
        creditApplication = new CreditApplication(clock, repository);
    }

    @Test
    public void creditIsCreated() {
        Credit credit = creditApplication.create(10.0, 2, 0.1);
        assertEquals(10.0, credit.getAmount());
        assertEquals(2, credit.getTerms());
        assertEquals(0.1, credit.getRate());
        assertEquals(Instant.now(clock).atOffset(ZoneOffset.UTC).toLocalDate(), credit.getCreationDate());
        assertEquals(2, credit.getPayments().size());
    }

    @Test
    public void creditIsPersisted() {
        Credit credit = creditApplication.create(10.0, 2, 0.1);
        assertEquals(credit, repository.getBy(credit.getId()).get());
    }

    @Test
    public void creditIsRetrieved() {
        Credit credit = creditApplication.create(10.0, 2, 0.1);
        assertEquals(credit, creditApplication.get(credit.getId()).get());
    }

    @Test
    public void creditDoesNotExists() {
        assertTrue(creditApplication.get("MISSING CREDIT ID").isEmpty());
    }
}
