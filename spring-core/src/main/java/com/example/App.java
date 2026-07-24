package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // @Autowired and @Qualifier demonstration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DocumentsService documentsService = context.getBean(DocumentsService.class);
        documentsService.processDocument();

        // @Autowired and @Component demonstration with constructor injection
        Car car = context.getBean(Car.class);
        car.drive();

        // Message bean demonstration
        Message message = context.getBean(Message.class);
        System.out.println(message.display());

        ((AnnotationConfigApplicationContext) context).close();

    }
}
