package jmcd.tfg.election.rest.Utils;

import jmcd.tfg.election.rest.pojo.UsuarioPojo;
import jmcd.tfg.persistencia.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPopulate {

    public void populate(Usuario model, UsuarioPojo pojo) {
        pojo.setNombre(model.getNombre());
        pojo.setClave(model.getClave());
    }
}
