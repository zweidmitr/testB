package com.zwei.testb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class testApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(testApplication.class, args);
        System.out.println("http://localhost:8080/api/");
    }
}
