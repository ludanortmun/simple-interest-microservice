package com.example.simpleinterestmicroservice.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Credit {
    private String id;
    private double amount;
    private int terms;
    private double rate;
    private LocalDate creationDate;
    private List<Payment> payments;

    public String getId() {
        return id;
    }

    public Credit setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Credit setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public Credit setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Credit setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public int getTerms() {
        return terms;
    }

    public Credit setTerms(int terms) {
        this.terms = terms;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Credit setRate(double rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Double.compare(credit.amount, amount) == 0 && terms == credit.terms && Double.compare(credit.rate, rate) == 0 && Objects.equals(id, credit.id) && Objects.equals(creationDate, credit.creationDate) && Objects.equals(payments, credit.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, terms, rate, creationDate, payments);
    }
}
