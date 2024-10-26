package com.micro.monitring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MonitringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitringApplication.class, args);
    }

}
