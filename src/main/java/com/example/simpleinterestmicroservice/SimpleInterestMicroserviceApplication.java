package com.example.simpleinterestmicroservice;

import com.example.simpleinterestmicroservice.application.CreditApplication;
import com.example.simpleinterestmicroservice.domain.CreditRepository;
import com.example.simpleinterestmicroservice.infrastructure.CreditSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.time.Clock;

@SpringBootApplication
public class SimpleInterestMicroserviceApplication {

    public static void main(String[] args) {

        SpringApplication.run(SimpleInterestMicroserviceApplication.class, args);
    }

    @Bean
    public CreditApplication provideCreditApplication(@Autowired DataSource dataSource) {
        CreditRepository creditRepository = new CreditSqlRepository(dataSource);
        return new CreditApplication(Clock.systemUTC(), creditRepository);
    }


}
