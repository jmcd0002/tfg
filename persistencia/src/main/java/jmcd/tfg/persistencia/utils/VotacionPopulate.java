package jmcd.tfg.persistencia.utils;

import jmcd.tfg.persistencia.model.Votacion;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import org.springframework.stereotype.Component;

@Component
public class VotacionPopulate {

    public VotacionPopulate(UsuarioPopulate usuarioPopulate){
        this.usuarioPopulate=usuarioPopulate;
    }

    UsuarioPopulate usuarioPopulate;

    public VotacionPojo populate(Votacion model) {
        return new VotacionPojo(model.getIdVotacion(),
                model.getNombreVotacion(),
                usuarioPopulate.populate(model.getUsuario()),
                model.getMapPartidosVotos());
    }

}
