package com.example.simpleinterestmicroservice.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class PaymentDto {
    @JsonProperty("payment_number")
    private int paymentNumber;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    public int getPaymentNumber() {
        return paymentNumber;
    }

    public PaymentDto setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentDto setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentDto setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return paymentNumber == that.paymentNumber && Double.compare(that.amount, amount) == 0 && Objects.equals(paymentDate, that.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentNumber, amount, paymentDate);
    }
}
