package com.jm.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.jm.common.entity")  // b/c ye dusre module me h,manually batana padega is package ko bhi scan karo."
@EnableJpaRepositories(basePackages = "com.jm.common.repository") // b/c ye dusre package me h
public class SiteAcsBackendApplication {
    public static void main(String[] args) {

        SpringApplication.run(SiteAcsBackendApplication.class, args);

    }
}

/*Conceptually:

main()
   ↓
SpringApplication.run(...)
   ↓
Create Spring Application Context
   ↓
Find @Configuration / @Component / @Service / @Repository / @Controller
   ↓
Create and configure Spring Beans
   ↓
Apply application.properties / application.yml
   ↓
Start embedded server (Tomcat, etc.)
   ↓
Application is running*/
