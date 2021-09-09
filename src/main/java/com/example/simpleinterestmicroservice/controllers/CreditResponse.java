package com.example.simpleinterestmicroservice.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class CreditResponse {
    @JsonProperty("credit_id")
    private String creditId;
    private double amount;
    private int terms;
    private double rate;
    private List<PaymentDto> payments;

    public String getCreditId() {
        return creditId;
    }

    public CreditResponse setCreditId(String creditId) {
        this.creditId = creditId;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public CreditResponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public int getTerms() {
        return terms;
    }

    public CreditResponse setTerms(int terms) {
        this.terms = terms;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public CreditResponse setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }

    public CreditResponse setPayments(List<PaymentDto> payments) {
        this.payments = payments;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditResponse that = (CreditResponse) o;
        return creditId.equals(that.creditId) && payments.equals(that.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditId, payments);
    }
}
