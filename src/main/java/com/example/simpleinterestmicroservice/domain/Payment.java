package com.example.simpleinterestmicroservice.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {
    private double amount;
    private LocalDate dateDue;

    public double getAmount() {
        return amount;
    }

    public Payment setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public Payment setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.amount, amount) == 0 && dateDue.equals(payment.dateDue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, dateDue);
    }
}
