package com.kopasolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication @EnableAsync
public class KopasolarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KopasolarApplication.class, args);
    }

}
