package com.lavesh.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lavesh.*"})
@EntityScan(basePackages = {"com.lavesh.*"})
public class NDRFApplication {

    public static void main(String[] args) {
        SpringApplication.run(NDRFApplication.class);
    }
}
