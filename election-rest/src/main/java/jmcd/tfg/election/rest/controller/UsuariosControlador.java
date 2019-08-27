package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.servicelayer.UsuariosServicio;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/usuario")
public class UsuariosControlador {

    public UsuariosControlador(UsuariosServicio usuariosServicio) {
        this.usuariosServicio = usuariosServicio;
    }

    private UsuariosServicio usuariosServicio;

    protected static final Logger LOG = LoggerFactory.getLogger(UsuariosControlador.class);

    @PostMapping
    public void registrar(@RequestBody UsuarioPojo usuarioPojo) {
        usuariosServicio.registrar(usuarioPojo);
    }

    @PostMapping("/{nombreUsuario}")
    public UsuarioPojo login(@PathVariable String nombreUsuario, @RequestBody UsuarioPojo usuarioPojo) {
        return usuariosServicio.login(nombreUsuario, usuarioPojo);
    }

    @PostMapping("/existe/{nombreUsuario}")
    public boolean existe(@PathVariable String nombreUsuario, @RequestParam String clave) {
        return usuariosServicio.existe(nombreUsuario, clave);
    }

    @PutMapping("/{nombreUsuario}")
    public void cambiarClave(@PathVariable String nombreUsuario, @RequestParam String clave) {
        usuariosServicio.cambiarClave(nombreUsuario, clave);
    }

    @DeleteMapping("/{nombreUsuario}")
    public void borrar(@PathVariable String nombreUsuario) {
        usuariosServicio.borrar(nombreUsuario);
    }

}
