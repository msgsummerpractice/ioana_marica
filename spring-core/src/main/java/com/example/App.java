package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "com.example")
public class App 
{
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Message.class);
        Message message = context.getBean(Message.class);
        System.out.println(message.display());

    }
}
