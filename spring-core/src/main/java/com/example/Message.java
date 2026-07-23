package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Message{

    @Bean
    public String display(){
        return "Hello, World!";
    }
}

