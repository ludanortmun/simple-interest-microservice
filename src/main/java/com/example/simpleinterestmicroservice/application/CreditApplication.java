package com.example.simpleinterestmicroservice.application;

import com.example.simpleinterestmicroservice.domain.Credit;
import com.example.simpleinterestmicroservice.domain.CreditRepository;
import com.example.simpleinterestmicroservice.domain.PaymentsCalculator;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

public class CreditApplication {
    private final Clock clock;
    private final CreditRepository creditRepository;

    public CreditApplication(Clock clock, CreditRepository creditRepository) {
        this.clock = clock;
        this.creditRepository = creditRepository;
    }

    public Credit create(double amount, int terms, double rate) {
        Credit credit = new Credit()
                .setId(createId())
                .setAmount(amount)
                .setTerms(terms)
                .setRate(rate)
                .setCreationDate(today());

        credit.setPayments(new PaymentsCalculator().computePaymentsFor(credit));
        creditRepository.save(credit);
        return credit;
    }

    public Optional<Credit> get(String id) {
        return creditRepository.getBy(id);
    }

    private LocalDate today() {
        return Instant.now(clock).atOffset(ZoneOffset.UTC).toLocalDate();
    }

    private String createId() {
        return UUID.randomUUID().toString();
    }
}
