package jmcd.tfg.election.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = {"jmcd.tfg.election.rest.controller", "jmcd.tfg.persistencia.dao", "jmcd.tfg.persistencia.crud", "jmcd.tfg.persistencia.utils"})
public class ElectionRestApplicationTests {

    @Test
    public void contextLoads() {
    }

}
