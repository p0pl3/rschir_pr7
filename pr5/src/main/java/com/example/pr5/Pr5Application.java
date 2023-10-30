package com.example.pr5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.example.pr5*")
public class Pr5Application {

    public static void main(String[] args) {
        SpringApplication.run(Pr5Application.class, args);
    }

}
