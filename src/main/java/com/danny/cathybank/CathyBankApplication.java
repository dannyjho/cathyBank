package com.danny.cathybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CathyBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(CathyBankApplication.class, args);
    }

}
