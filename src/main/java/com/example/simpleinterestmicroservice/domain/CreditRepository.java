package com.example.simpleinterestmicroservice.domain;

import java.util.Optional;

public interface CreditRepository {
    void save(Credit credit);

    Optional<Credit> getBy(String id);
}
