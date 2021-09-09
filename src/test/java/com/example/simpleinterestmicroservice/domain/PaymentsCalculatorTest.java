package com.example.simpleinterestmicroservice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentsCalculatorTest {
    private PaymentsCalculator paymentsCalculator;
    private LocalDate SOME_DATE = Instant.parse("2021-09-08T14:21:50Z").atOffset(ZoneOffset.UTC).toLocalDate();

    @BeforeEach
    public void setup() {
        paymentsCalculator = new PaymentsCalculator();
    }

    @Test
    public void noInterestSinglePayment() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0)
                .setTerms(1)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        assertEquals(1, payments.size());
        assertEquals(10.0, payments.get(0).getAmount());
    }

    @Test
    public void noInterestMultiplePayments() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0)
                .setTerms(2)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        Double totalAmount = payments.stream()
                .map(Payment::getAmount)
                .reduce(Double::sum)
                .orElseThrow(IllegalStateException::new);

        assertEquals(2, payments.size());
        assertEquals(10.0, totalAmount);
        assertTrue(payments.stream().allMatch(payment -> payment.getAmount() == 5.0));
    }

    @Test
    public void singlePaymentDueDate() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0)
                .setTerms(1)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        assertEquals(1, payments.size());
        assertEquals(LocalDate.of(2021, 9, 8), payments.get(0).getDateDue());
    }

    @Test
    public void multiplePaymentsDueDate() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0)
                .setTerms(2)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        assertEquals(LocalDate.of(2021, 9, 8), payments.get(0).getDateDue());
        assertEquals(LocalDate.of(2021, 9, 15), payments.get(1).getDateDue());
    }

    @Test
    public void singlePaymentWithInterest() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0.1)
                .setTerms(1)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        assertEquals(11.0, payments.get(0).getAmount());
    }

    @Test
    public void multiplePaymentsWithInterest() {
        Credit credit = new Credit()
                .setAmount(10.0)
                .setRate(0.1)
                .setTerms(2)
                .setCreationDate(SOME_DATE);

        List<Payment> payments = paymentsCalculator.computePaymentsFor(credit);
        assertTrue(payments.stream().allMatch(payment -> payment.getAmount() == 5.5));
    }
}
