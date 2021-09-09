package com.example.simpleinterestmicroservice.domain;

import java.util.ArrayList;
import java.util.List;

public class PaymentsCalculator {

    public List<Payment> computePaymentsFor(Credit credit) {
        ArrayList<Payment> payments = new ArrayList<>();

        double weeklyPayment = computeWeeklyPayment(credit);
        for (int weekOffset = 0; weekOffset < credit.getTerms(); weekOffset++) {
            Payment payment = new Payment()
                    .setAmount(weeklyPayment)
                    .setDateDue(credit.getCreationDate().plusWeeks(weekOffset));
            payments.add(payment);
        }

        return payments;
    }

    private double computeWeeklyPayment(Credit credit) {
        double interest = credit.getAmount() * credit.getRate();
        return (credit.getAmount() + interest) / credit.getTerms();
    }
}
