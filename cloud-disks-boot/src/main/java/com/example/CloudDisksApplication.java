package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 *
 * @author Ocean
 * @version 1.0.0
 */
@MapperScan("com.example.mapper")
@SpringBootApplication
public class CloudDisksApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDisksApplication.class, args);
    }

}
