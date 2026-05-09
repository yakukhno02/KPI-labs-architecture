package ua.kpi.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankLab4Application {

    public static void main(String[] args) {
        SpringApplication.run(BankLab4Application.class, args);
    }

}
