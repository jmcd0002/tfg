package jmcd.tfg.election.rest.servicelayer;

import jmcd.tfg.persistencia.dao.VotacionDAO;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VotacionesServicio {

    public VotacionesServicio(VotacionDAO votacionDAO) {
        this.votacionDAO = votacionDAO;
    }

    private VotacionDAO votacionDAO;

    public void crearVotacion(String nombreVotacion, String usuario) {
        votacionDAO.crearVotacion(nombreVotacion, usuario);
    }

//    public void modificarVotacion(int idVotacion, String nombreVotacion) {
//        votacionDAO.modificarVotacion(idVotacion, nombreVotacion);
//    }

    public VotacionPojo getVotacion(int idVotacion) {
        return votacionDAO.getVotacion(idVotacion);
    }

    public List<VotacionPojo> getListaVotaciones(String usuario) {
        return votacionDAO.getListaVotaciones(usuario);
    }

    public void borrarVotacion(int idVotacion) {
        votacionDAO.borrarVotacion(idVotacion);
    }

    public void anadirPartidoVotos(int idVotacion, Map<String, Integer> partidoVotos) {
        partidoVotos.forEach((k, v) -> votacionDAO.anadirPartidoVotos(idVotacion, k, v));
    }

    public Map<String, Integer> getPartidosVotos(int idVotacion) {
        return votacionDAO.getPartidosVotos(idVotacion);
    }

    public void modificarPartidoVotos(int idVotacion, String partido, int votos) {
        votacionDAO.modificarPartidoVotos(idVotacion, partido, votos);
    }

    public void borrarPartidoVotos(int idVotacion, String partido) {
        votacionDAO.borrarPartidoVotos(idVotacion, partido);
    }


}
