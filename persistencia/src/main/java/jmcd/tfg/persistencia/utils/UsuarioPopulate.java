package jmcd.tfg.persistencia.utils;

import jmcd.tfg.persistencia.model.Usuario;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPopulate {

    public UsuarioPojo populate(Usuario model) {
        return new UsuarioPojo(model.getNombre(), model.getClave());
    }
}
