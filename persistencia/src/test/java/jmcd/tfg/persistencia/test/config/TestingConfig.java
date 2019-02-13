package jmcd.tfg.persistencia.test.config;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotosCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.dao.VotosDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class TestingConfig {

    @Bean
    public UsuarioDAO usuarioDAO(){
        return new UsuarioDAO();
    }

    @Bean
    public UsuarioCRUD usuarioCRUD(){
        return new UsuarioCRUD();
    }

    @Bean
    public VotosDAO votosDAO(){
        return new VotosDAO();
    }

    @Bean
    public VotosCRUD votosCRUD(){
        return new VotosCRUD();
    }
}
