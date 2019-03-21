package jmcd.tfg.election.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jmcd.tfg.election.rest.controller","jmcd.tfg.persistencia.dao"})
public class ElectionRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectionRestApplication.class, args);
    }
}
