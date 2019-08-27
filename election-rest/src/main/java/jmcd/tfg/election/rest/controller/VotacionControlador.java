package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.servicelayer.VotacionesServicio;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/votacion")
public class VotacionControlador {

    public VotacionControlador(VotacionesServicio votacionesServicio) {
        this.votacionesServicio = votacionesServicio;
    }

    private VotacionesServicio votacionesServicio;

    @PostMapping("/{nombreVotacion}/usuario/{usuario}")
    public void crearVotacion(@PathVariable String nombreVotacion, @PathVariable String usuario) {
        votacionesServicio.crearVotacion(nombreVotacion, usuario);
    }

//    @PutMapping("/{idVotacion}")
//    public void modificarVotacion(@PathVariable int idVotacion, @RequestParam String nombreVotacion) {
//        votacionesServicio.modificarVotacion(idVotacion, nombreVotacion);
//    }

    @GetMapping("/{idVotacion}")
    public VotacionPojo getVotacion(@PathVariable int idVotacion) {
        return votacionesServicio.getVotacion(idVotacion);
    }

    @GetMapping("usuario/{usuario}")
    public List<VotacionPojo> getListaVotaciones(@PathVariable String usuario) {
        return votacionesServicio.getListaVotaciones(usuario);
    }

    @DeleteMapping("/{idVotacion}")
    public void borrarVotacion(@PathVariable int idVotacion) {
        votacionesServicio.borrarVotacion(idVotacion);
    }

    @PostMapping("/{idVotacion}/partido")
    public void anadirPartidoVotos(@PathVariable int idVotacion, @RequestBody Map<String, Integer> partidoVotos) {
        votacionesServicio.anadirPartidoVotos(idVotacion, partidoVotos);
    }

    @GetMapping("/{idVotacion}/partido")
    public Map<String, Integer> getPartidosVotos(@PathVariable int idVotacion) {
        return votacionesServicio.getPartidosVotos(idVotacion);
    }

    @PutMapping("/{idVotacion}/partido/{partido}")
    public void modificarPartidoVotos(@PathVariable int idVotacion, @PathVariable String partido, @RequestParam int votos) {
        votacionesServicio.modificarPartidoVotos(idVotacion, partido, votos);
    }

    @DeleteMapping("/{idVotacion}/partido/{partido}")
    public void borrarPartidoVotos(@PathVariable int idVotacion, @PathVariable String partido) {
        votacionesServicio.borrarPartidoVotos(idVotacion, partido);
    }


}
