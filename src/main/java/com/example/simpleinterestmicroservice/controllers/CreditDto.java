package com.example.simpleinterestmicroservice.controllers;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class CreditDto {
    @Positive
    private double amount;
    @Positive
    private int terms;
    @PositiveOrZero
    private double rate;

    public double getAmount() {
        return amount;
    }

    public CreditDto setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public int getTerms() {
        return terms;
    }

    public CreditDto setTerms(int terms) {
        this.terms = terms;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public CreditDto setRate(double rate) {
        this.rate = rate;
        return this;
    }
}
