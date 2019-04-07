package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.Utils.UsuarioPopulate;
import jmcd.tfg.election.rest.Utils.VotacionPopulate;
import jmcd.tfg.election.rest.pojo.VotacionPojo;
import jmcd.tfg.persistencia.dao.VotacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/votacion")
public class VotacionControlador {

    @Autowired
    private VotacionDAO votacionDAO;
    @Autowired
    UsuarioPopulate usuarioPopulate;
    @Autowired
    VotacionPopulate votacionPopulate;

    @PostMapping("/{usuario}/{nombreVotacion}")
    public void crearVotacion(@PathVariable String nombreVotacion, @PathVariable String usuario) {
        votacionDAO.crearVotacion(nombreVotacion, usuario);
    }

    @GetMapping("/{idVotacion}")
    public VotacionPojo getVotacion(@PathVariable int idVotacion) {
        VotacionPojo votacion = new VotacionPojo();
        votacionPopulate.populate(votacionDAO.getVotacion(idVotacion), votacion);
        return votacion;
    }

//    @PostMapping("/{usuario}")
//    public List<VotacionPojo> getListaVotaciones(@PathVariable String usuario) {
//        votacionDAO.getListaVotaciones(usuario);
//
//        return;
//    }
//
//    @PostMapping("/{usuario}/{nombreVotacion}")
//    public void borrarVotacion(@PathVariable String usuario,@PathVariable int idVotacion) {
//        votacionDAO.borrarVotacion(idVotacion);
//    }
//
//    @PostMapping
//    public void anadirPartidoVotos(int idVotacion, String partido, int votos) {
//        votacionDAO.anadirPartidoVotos(idVotacion, partido, votos);
//    }
//
//    @PostMapping
//    public Map<String, Integer> getPartidosVotos(int idVotacion) {
//        return votacionDAO.getPartidosVotos(idVotacion);
//    }
//
//    @PostMapping
//    public void modificarPartidoVotos(int idVotacion, String partido, int votos) {
//        votacionDAO.modificarPartidoVotos(idVotacion, partido, votos);
//    }
//
//    @PostMapping
//    public void borrarPartidoVotos(int idVotacion, String partido) {
//        votacionDAO.borrarPartidoVotos(idVotacion, partido);
//    }


}
