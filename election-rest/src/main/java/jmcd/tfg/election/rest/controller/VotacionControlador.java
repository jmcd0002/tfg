package jmcd.tfg.election.rest.controller;

import jmcd.tfg.persistencia.dao.VotacionDAO;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/votacion")
public class VotacionControlador {

    public VotacionControlador(VotacionDAO votacionDAO){
        this.votacionDAO=votacionDAO;
    }

    private VotacionDAO votacionDAO;

    @PostMapping("/{nombreVotacion}/usuario/{usuario}")
    public void crearVotacion(@PathVariable String nombreVotacion, @PathVariable String usuario) {
        votacionDAO.crearVotacion(nombreVotacion, usuario);
    }

    @GetMapping("/{idVotacion}")
    public VotacionPojo getVotacion(@PathVariable int idVotacion) {
        return votacionDAO.getVotacion(idVotacion);
    }

    @GetMapping("usuario/{usuario}")
    public List<VotacionPojo> getListaVotaciones(@PathVariable String usuario) {
        return votacionDAO.getListaVotaciones(usuario);
    }

    @DeleteMapping("/{idVotacion}")
    public void borrarVotacion(@PathVariable int idVotacion) {
        votacionDAO.borrarVotacion(idVotacion);
    }

    @PostMapping("/{idVotacion}/partido")
    public void anadirPartidoVotos(@PathVariable int idVotacion, @RequestBody Map<String, Integer> partidoVotos) {
        partidoVotos.forEach((k, v) -> votacionDAO.anadirPartidoVotos(idVotacion, k, v));
    }

    @GetMapping("/{idVotacion}/partido")
    public Map<String, Integer> getPartidosVotos(@PathVariable int idVotacion) {
        return votacionDAO.getPartidosVotos(idVotacion);
    }

    @PutMapping("/{idVotacion}/partido/{partido}")
    public void modificarPartidoVotos(@PathVariable int idVotacion, @PathVariable String partido, @RequestParam int votos) {
        votacionDAO.modificarPartidoVotos(idVotacion, partido, votos);
    }

    @DeleteMapping("/{idVotacion}/partido/{partido}")
    public void borrarPartidoVotos(@PathVariable int idVotacion, @PathVariable String partido) {
        votacionDAO.borrarPartidoVotos(idVotacion, partido);
    }


}
