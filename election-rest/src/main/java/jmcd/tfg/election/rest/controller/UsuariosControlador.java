package jmcd.tfg.election.rest.controller;

import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/usuario")
public class UsuariosControlador {

    public UsuariosControlador(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    private UsuarioDAO usuarioDAO;

    protected static final Logger LOG = LoggerFactory.getLogger(UsuariosControlador.class);

    @PostMapping
    public void registrar(@RequestBody UsuarioPojo usuarioPojo) {
        usuarioDAO.crearUsuario(usuarioPojo.getNombre(), usuarioPojo.getClave());
    }

    @PostMapping("/{nombreUsuario}")
    public UsuarioPojo login(@PathVariable String nombreUsuario, @RequestBody UsuarioPojo usuarioPojo) {
//        return (usuarioDAO.existe(usuarioPojo.getNombre(), usuarioPojo.getClave()))
        return (usuarioPojo.getClave().equals(usuarioDAO.getUsuario(usuarioPojo.getNombre()).getClave()))
                ? usuarioDAO.getUsuario(nombreUsuario)
                : new UsuarioPojo();
    }

    @PostMapping("/existe/{nombreUsuario}")
    public boolean existe(@PathVariable String nombreUsuario, @RequestParam String clave) {
        return usuarioDAO.existe(nombreUsuario, clave);
    }

    @PutMapping("/{nombreUsuario}")
    public void cambiarClave(@PathVariable String nombreUsuario, @RequestParam String clave) {
        usuarioDAO.cambiarClave(nombreUsuario, clave);
    }

    @DeleteMapping("/{nombreUsuario}")
    public void borrar(@PathVariable String nombreUsuario) {
        usuarioDAO.borrarUsuario(nombreUsuario);
    }

}
