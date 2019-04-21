package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.model.Usuario;
import jmcd.tfg.persistencia.pojo.UsuarioPojo;
import jmcd.tfg.persistencia.utils.UsuarioPopulate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository
public class UsuarioDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    @Autowired
    private UsuarioCRUD usuarioCRUD;

    @Autowired
    private UsuarioPopulate usuarioPopulate;

    /**
     * Crea un usuario
     *
     * @param nombre
     * @param clave
     */
    public void crearUsuario(String nombre, String clave) {
        try {
            String claveEncriptada = encriptacionMD5(clave);
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setClave(claveEncriptada);
            LOG.info("Nombre usuario: " + usuario.getNombre() + "Clave" + usuario.getClave());
            usuarioCRUD.create(usuario);
            LOG.info("usuario insertado");
            //TODO
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Error al crear usuario" + e.getMessage());
        }

    }

    /**
     * Comprueba si un usuario existe
     *
     * @param nombre
     * @param clave
     * @return
     */
    public boolean existe(String nombre, String clave) {
        if (usuarioCRUD.exists(nombre)) {
            LOG.info(nombre + " existe");
            Usuario usuarioBd = usuarioCRUD.getEntidadPorId(nombre);
            try {
                return encriptacionMD5(clave).equals(usuarioBd.getClave());
                //TODO
            } catch (NoSuchAlgorithmException e) {
                LOG.error(e.getMessage());
            }
        } else {
            LOG.info(nombre + " no existe");
        }
        return false;
    }

    /**
     * Obtiene un usuario
     *
     * @param nombre
     * @return
     */
    public UsuarioPojo getUsuario(String nombre) {
        Usuario usuario = usuarioCRUD.getEntidadPorId(nombre);
        if (usuario != null) {
            LOG.info("Obteniendo usuario: " + usuario.getNombre() + " con clave: " + usuario.getClave());
        }
        return usuarioPopulate.populate(usuario);
    }

    /**
     * Actualiza la clave de un usuario
     *
     * @param nombre
     * @param clave
     */
    public void cambiarClave(String nombre, String clave) {
        try {
            Usuario usuario = usuarioCRUD.getEntidadPorId(nombre);
            String hashtext = encriptacionMD5(clave);
            usuario.setClave(hashtext);
            usuarioCRUD.update(usuario);
            LOG.info("Actualizado usuario: " + usuario.getNombre() + " con nueva clave: " + usuario.getClave());
            //TODO
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Error al cambiar la clave" + e.getMessage());
        }
    }

    /**
     * Borra un usuario dado su nombre
     *
     * @param nombre
     */
    public void borrarUsuario(String nombre) {
        usuarioCRUD.borrarEntidad(nombre);
        LOG.info("usuario " + nombre + " borrado");
    }

    /**
     * Metodo de encriptado de la clave
     *
     * @param clave
     * @return Devueve la clave encriptada
     * @throws NoSuchAlgorithmException
     */
    private String encriptacionMD5(String clave) throws NoSuchAlgorithmException {
        byte[] bytesOfMessage = clave.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        BigInteger bigInt = new BigInteger(1, thedigest);
        return bigInt.toString(16);
    }
}
