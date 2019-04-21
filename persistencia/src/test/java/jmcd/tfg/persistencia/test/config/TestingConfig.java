package jmcd.tfg.persistencia.test.config;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotacionCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.dao.VotacionDAO;
import jmcd.tfg.persistencia.utils.UsuarioPopulate;
import jmcd.tfg.persistencia.utils.VotacionPopulate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestingConfig {

    @Bean
    public UsuarioDAO usuarioDAO() {
        return new UsuarioDAO();
    }

    @Bean
    public UsuarioCRUD usuarioCRUD() {
        return new UsuarioCRUD();
    }

    @Bean
    public VotacionDAO votacionDAO() {
        return new VotacionDAO();
    }

    @Bean
    public VotacionCRUD votacionCRUD() {
        return new VotacionCRUD();
    }

    @Bean
    public UsuarioPopulate usuarioPopulate() {
        return new UsuarioPopulate();
    }

    @Bean
    public VotacionPopulate votacionPopulate() {
        return new VotacionPopulate();
    }

}
