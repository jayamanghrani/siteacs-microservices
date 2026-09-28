package com.jm.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = "com.jm.batch")
@EnableScheduling
@EnableAsync
@EntityScan(basePackages = "com.jm.common.entity")
@EnableJpaRepositories(basePackages = "com.jm.common.repository")
public class SiteAcsSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SiteAcsSchedulerApplication.class, args);
    }

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);      // Sirf 5 email EK SAATH bhejenge
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);   // Baaki queue mein wait karenge
        executor.initialize();
        return executor;
    }





}



// //bina eske, @Scheduled annotation kaм nahi karеga
//@EnableAsync  Ye annotation email-sending ko background mein, alag thread pe bhejता hai
//1. Cron trigger hota hai (raat 2 baje)
//2. StorageService se "latest file" nikaalo
//3. Excel parse karo (Apache POI se)
//4. Har row: validate karo → Id exist karta hai? update, warna insert
//5. Job-run summary track karo (inserted, updated, failed)
//6. Logger se sab kuch log karo