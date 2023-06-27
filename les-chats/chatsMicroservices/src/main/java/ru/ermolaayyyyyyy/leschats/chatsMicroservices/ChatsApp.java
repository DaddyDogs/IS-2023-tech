package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.ermolaayyyyyyy"})
@EntityScan("ru.ermolaayyyyyyy.leschats.entities")
@EnableJpaRepositories("ru.ermolaayyyyyyy.leschats.chatsMicroservices")
public class ChatsApp {
    public static void main(String[] args){
        ApplicationContext context = SpringApplication.run(ChatsApp.class, args);
    }
}

