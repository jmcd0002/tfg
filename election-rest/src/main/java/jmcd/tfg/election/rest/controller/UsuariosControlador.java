package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.pojo.Usuario;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuariosControlador {

    @Autowired
    UsuarioDAO usuarioDAO;

    @PostMapping
    public void registrar(@RequestBody Usuario usuario){
        usuarioDAO.crearUsuario(usuario.getNombre(),usuario.getClave());
    }

    @PutMapping
    public void cambiarClave(@RequestBody Usuario usuario, @RequestParam String clave){
        usuarioDAO.cambiarClave(usuario.getNombre(),clave);
    }

    @PostMapping("/{nombre}")
    public Usuario get(@PathVariable String nombre, @RequestParam String clave){
        return usuarioDAO.existe(nombre,clave)?new Usuario(nombre,clave):null;
    }

    @DeleteMapping
    public void borrar(@RequestBody Usuario usuario){
        usuarioDAO.borrarUsuario(usuario.getNombre());
    }

}
