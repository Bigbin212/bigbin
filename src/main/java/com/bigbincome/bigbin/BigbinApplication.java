package com.bigbincome.bigbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan
public class BigbinApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigbinApplication.class, args);
    }

}
