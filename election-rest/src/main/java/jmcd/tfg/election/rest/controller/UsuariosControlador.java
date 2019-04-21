package jmcd.tfg.election.rest.controller;

import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
public class UsuariosControlador {

    @Autowired
    private UsuarioDAO usuarioDAO;

    protected static final Logger LOG = LoggerFactory.getLogger(UsuariosControlador.class);

    @PostMapping
    public void registrar(@RequestBody UsuarioPojo usuarioPojo) {
        usuarioDAO.crearUsuario(usuarioPojo.getNombre(), usuarioPojo.getClave());
    }

    @GetMapping("/{nombreUsuario}")
    public UsuarioPojo get(@PathVariable String nombreUsuario, @RequestParam String clave) {
        return (clave.equals(usuarioDAO.getUsuario(nombreUsuario).getClave()))
                ? usuarioDAO.getUsuario(nombreUsuario)
                : new UsuarioPojo();
    }

//    @GetMapping("/{nombreUsuario}")
//    public UsuarioPojo login(@PathVariable String nombreUsuario, @RequestParam String clave) {
//        UsuarioPojo usuarioPojo = new UsuarioPojo();
//        if (usuarioDAO.existe(nombreUsuario, clave)) {
//            usuarioPojo.setNombre(nombreUsuario);
//            usuarioPojo.setClave(clave);
//            usuarioPopulate.populate(usuarioDAO.getUsuario(nombreUsuario), usuarioPojo);
//        }
//        return usuarioPojo;
//    }

    @PutMapping("/{nombreUsuario}")
    public void cambiarClave(@PathVariable String nombreUsuario, @RequestParam String clave) {
        usuarioDAO.cambiarClave(nombreUsuario, clave);
    }

    @DeleteMapping("/{nombreUsuario}")
    public void borrar(@PathVariable String nombreUsuario) {
        usuarioDAO.borrarUsuario(nombreUsuario);
    }

}
