package ru.ermolaayyyyyyy.leschats.ownersMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.ermolaayyyyyyy"})
@EntityScan("ru.ermolaayyyyyyy.leschats.entities")
@EnableJpaRepositories("ru.ermolaayyyyyyy.leschats.ownersMicroservice")
public class OwnersApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OwnersApp.class, args);
    }
}
