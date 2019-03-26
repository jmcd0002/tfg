package jmcd.tfg.election.rest.controller;

import jmcd.tfg.persistencia.dao.VotacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votacion")
public class VotacionControlador {

    @Autowired
    private VotacionDAO votacionDAO;

    @PostMapping("/{usuario}/{nombreVotacion}")
    public void crearVotacion(@PathVariable String nombreVotacion, @PathVariable String usuario) {
        votacionDAO.crearVotacion(nombreVotacion, usuario);
    }
//
//    @PostMapping("/{usuario}")
//    public List<String> getListaVotaciones(String usuario) {
//        return votacionDAO.getListaVotaciones(usuario);
//    }
//
//    @PostMapping
//    public Votacion getVotacion(String nombreVotacion, String usuario) {
//        votacionDAO.getVotacion(nombreVotacion, usuario);
//        Votacion votacion = null;
//        return votacion;
//    }
//
//    @PostMapping("/{usuario}/{nombreVotacion}")
//    public void borrarVotacion(String nombreVotacion, String usuario) {
//        votacionDAO.borrarVotacion(nombreVotacion, usuario);
//    }
//
//    @PostMapping
//    public void anadirPartidoVotos(String nombreVotacion, String usuario, String partido, int votos) {
//        votacionDAO.anadirPartidoVotos(nombreVotacion, usuario, partido, votos);
//    }
//
//    @PostMapping
//    public Map<String, Integer> getPartidosVotos(String nombreVotacion, String usuario) {
//        return votacionDAO.getPartidosVotos(nombreVotacion, usuario);
//    }
//
//    @PostMapping
//    public void modificarPartidoVotos(String nombreVotacion, String usuario, String partido, int votos) {
//        votacionDAO.modificarPartidoVotos(nombreVotacion, usuario, partido, votos);
//    }
//
//    @PostMapping
//    public void borrarPartidoVotos(String nombreVotacion, String usuario, String partido) {
//        votacionDAO.borrarPartidoVotos(nombreVotacion, usuario, partido);
//    }


}
