package jmcd.tfg.election.rest.servicelayer;

import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServicio {
    public UsuariosServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    private UsuarioDAO usuarioDAO;

    protected static final Logger LOG = LoggerFactory.getLogger(UsuariosServicio.class);

    public void registrar(UsuarioPojo usuarioPojo) {
        usuarioDAO.crearUsuario(usuarioPojo.getNombre(), usuarioPojo.getClave());
    }

    public UsuarioPojo login(String nombreUsuario, UsuarioPojo usuarioPojo) {
        if (nombreUsuario.equals(usuarioPojo.getNombre())) {
            UsuarioPojo solucion = (usuarioDAO.existe(usuarioPojo.getNombre(), usuarioPojo.getClave()))
//        return (usuarioPojo.getClave().equals(usuarioDAO.getUsuario(usuarioPojo.getNombre()).getClave()))
                    ? usuarioDAO.getUsuario(nombreUsuario)
                    : new UsuarioPojo();
            solucion.setClave(null);
            return solucion;
        }
        return new UsuarioPojo();
    }

    public boolean existe(String nombreUsuario, String clave) {
        return usuarioDAO.existe(nombreUsuario, clave);
    }

    public void cambiarClave(String nombreUsuario, String clave) {
        usuarioDAO.cambiarClave(nombreUsuario, clave);
    }

    public void borrar(String nombreUsuario) {
        usuarioDAO.borrarUsuario(nombreUsuario);
    }

}
