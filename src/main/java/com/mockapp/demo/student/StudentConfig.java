package com.mockapp.demo.student;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student jamal = new Student(
                    "Jamal",
                    "jamal.murray@gmail.com",
                    LocalDate.of(1997, Month.FEBRUARY, 23)
            );

            Student joel = new Student(
                    "Joel",
                    "joel.embiid@outlook.com",
                    LocalDate.of(1994, Month.MARCH, 16)
            );

            repository.saveAll(
                    List.of(jamal, joel)
            );
        };
    }
}
