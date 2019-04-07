package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.model.Usuario;
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
     * Comprueba si un usuario existe
     *
     * @param nombre

     * @return
     */
    public Usuario obtenerUsuario(String nombre) {
            return usuarioCRUD.getEntidadPorId(nombre);

    }
    /**
     * Actualiza la clave de un usuario
     *
     * @param nombre
     * @param clave
     */
    public void cambiarClave(String nombre, String clave) {
        try {
            String hashtext = encriptacionMD5(clave);
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setClave(hashtext);
            usuarioCRUD.update(nuevo);
            LOG.info("usuario " + nombre + " actuaizado");
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
