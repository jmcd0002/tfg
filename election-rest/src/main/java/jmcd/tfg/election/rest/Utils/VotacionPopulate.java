package jmcd.tfg.election.rest.Utils;

import jmcd.tfg.election.rest.pojo.UsuarioPojo;
import jmcd.tfg.election.rest.pojo.VotacionPojo;
import jmcd.tfg.persistencia.model.Votacion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotacionPopulate {
    @Autowired
    UsuarioPopulate usuarioPopulate;

    public void populate(Votacion model, VotacionPojo pojo) {
        pojo.setIdVotacion(model.getIdVotacion());
        if (model.getMapPartidosVotos() != null) {
            pojo.setMapPartidosVotos(model.getMapPartidosVotos());
        }
        pojo.setNombreVotacion(StringUtils.isNotEmpty(model.getNombreVotacion()) ? model.getNombreVotacion() : "");

        UsuarioPojo usuarioPojo = new UsuarioPojo();
        usuarioPopulate.populate(model.getUsuario(), usuarioPojo);
        pojo.setUsuarioPojo(usuarioPojo);
        pojo.setMapPartidosVotos(model.getMapPartidosVotos());
    }
}
