package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.example.mapper")
@ServletComponentScan(basePackages = "com.example.entity")
public class CloudDisksApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDisksApplication.class, args);
    }

}
