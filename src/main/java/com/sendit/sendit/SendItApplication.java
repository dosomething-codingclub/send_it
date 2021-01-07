package com.sendit.sendit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SendItApplication {

    // TODO: KDY - 테스트입니다.
    public static void main(String[] args) {
        SpringApplication.run(SendItApplication.class, args);
    }
}
