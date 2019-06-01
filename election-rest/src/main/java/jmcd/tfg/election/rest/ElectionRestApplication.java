package jmcd.tfg.election.rest;

import jmcd.tfg.persistencia.dao.PersistenciaUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jmcd.tfg.election.rest.controller",
        "jmcd.tfg.persistencia.dao",
        "jmcd.tfg.persistencia.crud",
        "jmcd.tfg.election.rest.servicelayer",
        "jmcd.tfg.persistencia.utils"})
public class ElectionRestApplication {

    public static void main(String[] args) {
        PersistenciaUtils.initPersistencia();
        SpringApplication.run(ElectionRestApplication.class, args);
    }
}
