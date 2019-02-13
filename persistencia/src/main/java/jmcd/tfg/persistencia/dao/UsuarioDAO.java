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

    private static final Logger LOG= LoggerFactory.getLogger(UsuarioDAO.class);

    @Autowired
    private UsuarioCRUD usuarioCRUD;

    public boolean existe(String nombre, String clave){
        if (usuarioCRUD.exists(nombre)){
            LOG.info(nombre+" existe");
            Usuario usuarioBd=usuarioCRUD.getEntidadPorId(nombre);
            try {
                return encriptacionMD5(clave).equals(usuarioBd.getClave());
                //TODO
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        else{
            LOG.info(nombre+" no existe");
        }
        return false;
    }

    public void cambiarClave(String nombre,String clave){
        try{
            String hashtext = encriptacionMD5(clave);
            Usuario nuevo=new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setClave(hashtext);
            usuarioCRUD.update(nuevo);
            //TODO - especializa los try-catch de TODO EL PROYECTO, Josema :)
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public void crearUsuario(String nombre, String clave){
        try{
            String claveEncriptada=encriptacionMD5(clave);
            Usuario usuario=new Usuario();
            usuario.setNombre(nombre);
            usuario.setClave(claveEncriptada);
            LOG.info("Nombre usuario: " + usuario.getNombre() + "Clave" + usuario.getClave());
            usuarioCRUD.create(usuario);
            LOG.info("usuario insertado");
            //TODO
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }

    public void borrarUsuario(String nombre){
        usuarioCRUD.borrarEntidad(nombre);
    }

    private String encriptacionMD5(String clave) throws NoSuchAlgorithmException {
        byte[] bytesOfMessage = clave.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        BigInteger bigInt = new BigInteger(1,thedigest);
        return bigInt.toString(16);
    }
}
