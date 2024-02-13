package org.example.prawo_jazdy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class PrawoJazdyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrawoJazdyApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(){
        return runner ->{
            System.out.println("xdd");
            System.out.println(new ClassPathResource("test.txt").getPath());
        };
    }
}
