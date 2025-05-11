package com.tarefas;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada Spring Boot.
 * Anotação @SpringBootApplication combina:
 *  – @Configuration (beans)
 *  – @ComponentScan (procura @Service/@Component no pacote filho)
 *  – @EnableAutoConfiguration (liga JPA, H2, etc.)
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}