package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Rozwiązuje problem z auto inkrementacją ID w bazie danych
    // (aktualizuje aktualne Id, wczesniej zawsze liczyło od początku)
    @Override
    public void run(String... args) throws Exception {
        // Zaktualizowanie sekwencji na podstawie największego id w tabeli 'product'
        jdbcTemplate.execute("SELECT setval('product_id_seq', (SELECT MAX(id) FROM product) + 1)");
    }
}