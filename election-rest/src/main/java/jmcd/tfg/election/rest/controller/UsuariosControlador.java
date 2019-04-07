package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.Utils.UsuarioPopulate;
import jmcd.tfg.election.rest.pojo.UsuarioPojo;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
public class UsuariosControlador {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private UsuarioPopulate usuarioPopulate;
    protected static final Logger LOG = LoggerFactory.getLogger(UsuariosControlador.class);

    @PostMapping
    public void registrar(@RequestBody UsuarioPojo usuarioPojo) {
        usuarioDAO.crearUsuario(usuarioPojo.getNombre(), usuarioPojo.getClave());
    }

    @GetMapping("/{nombreUsuario}")
    public UsuarioPojo get(@PathVariable String nombreUsuario, @RequestParam String clave) {
        UsuarioPojo usuarioPojo = new UsuarioPojo();
        usuarioPopulate.populate(usuarioDAO.obtenerUsuario(nombreUsuario), usuarioPojo);
        if (clave.equals(usuarioPojo.getClave())) {
            return usuarioPojo;
        }
        return new UsuarioPojo();
    }

//    @GetMapping("/{nombreUsuario}")
//    public UsuarioPojo login(@PathVariable String nombreUsuario, @RequestParam String clave) {
//        UsuarioPojo usuarioPojo = new UsuarioPojo();
//        if (usuarioDAO.existe(nombreUsuario, clave)) {
//            usuarioPojo.setNombre(nombreUsuario);
//            usuarioPojo.setClave(clave);
//            usuarioPopulate.populate(usuarioDAO.obtenerUsuario(nombreUsuario), usuarioPojo);
//        }
//        return usuarioPojo;
//    }

    @PutMapping("/{nombre}")
    public void cambiarClave(@PathVariable String nombre, @RequestParam String clave) {
        usuarioDAO.cambiarClave(nombre, clave);
    }

    @DeleteMapping("/{usuario}")
    public void borrar(@PathVariable String usuario) {
        usuarioDAO.borrarUsuario(usuario);
    }

}
