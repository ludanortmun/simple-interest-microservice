package com.example.simpleinterestmicroservice.controllers;

import com.example.simpleinterestmicroservice.application.CreditApplication;
import com.example.simpleinterestmicroservice.domain.Credit;
import com.example.simpleinterestmicroservice.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class CreditController {

    @Autowired
    private CreditApplication creditApplication;

    @PostMapping(value = "/credits")
    public CreditResponse createCredit(@RequestBody @Valid CreditDto creditDto) {
        Credit credit = creditApplication.create(creditDto.getAmount(), creditDto.getTerms(), creditDto.getRate());
        return mapResponse(credit);
    }

    @GetMapping(value = "/credits/{id}")
    public CreditResponse getPaymentsForCredit(@PathVariable(value = "id") @NotBlank String id) {
        return creditApplication.get(id)
                .map(this::mapResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No credit with ID %s found", id)
                        ));
    }

    private CreditResponse mapResponse(Credit credit) {
        List<PaymentDto> responsePayments = IntStream.range(0, credit.getPayments().size())
                .boxed()
                .map(i -> {
                    Payment p = credit.getPayments().get(i);
                    return new PaymentDto()
                            .setPaymentNumber(i + 1)
                            .setAmount(p.getAmount())
                            .setPaymentDate(p.getDateDue());
                })
                .collect(Collectors.toList());

        return new CreditResponse()
                .setCreditId(credit.getId())
                .setAmount(credit.getAmount())
                .setRate(credit.getRate())
                .setTerms(credit.getTerms())
                .setPayments(responsePayments);
    }
}
