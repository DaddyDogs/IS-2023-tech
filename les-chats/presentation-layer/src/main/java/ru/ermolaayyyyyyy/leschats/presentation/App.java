package ru.ermolaayyyyyyy.leschats.presentation;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.ermolaayyyyyyy"})
@EntityScan("ru.ermolaayyyyyyy.leschats.entities")
@EnableJpaRepositories("ru.ermolaayyyyyyy.leschats.dataaccesslayer.repositories")
@SecurityScheme(name = "cats-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class App {
    public static void main(String[] args){
        ApplicationContext context = SpringApplication.run(App.class, args);
    }
}
