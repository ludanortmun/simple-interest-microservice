package com.example.simpleinterestmicroservice;

import com.example.simpleinterestmicroservice.infrastructure.InMemoryCreditDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestServerConfiguration {
    @Bean
    public DataSource dataSource() {
        return InMemoryCreditDatabase.getDataSource();
    }
}
