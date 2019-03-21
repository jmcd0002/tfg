package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotacionCRUD;
import jmcd.tfg.persistencia.excepcion.PartidoExiste;
import jmcd.tfg.persistencia.excepcion.PartidoNoExiste;
import jmcd.tfg.persistencia.model.Votacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VotacionDAO {

    @Autowired
    private VotacionCRUD votacionCRUD;
    @Autowired
    private UsuarioCRUD usuarioCRUD;

    /**
     * Devuelve
     *
     * @param usuario
     * @return lista con los votos que ha introducido un usuario
     */
    public List<String> getListaResultados(String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario, "v")
                .stream()
                .map(Votacion::getNombre_votacion)
                .collect(Collectors.toList());
    }

    /**
     * @param nombre  : nombre de la votacion
     * @param usuario : usuario de la votacion
     * @return id de la votacion
     */
    private String getIdDesdeNombreUsuario(String nombre, String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario + " and v.nombre = " + nombre, "v")
                .stream()
                .findFirst()
                .map(Votacion::getId_votacion)
                .orElseGet(String::new);
    }

    /**
     * @param nombre  : nombre de la votacion
     * @param usuario : usuario de la votacion
     * @return mapa de votos de la votacion
     */
    public Map<String, Integer> getResutados(String nombre, String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario + " and v.nombre = " + nombre, "v")
                .stream()
                .findFirst()
                .map(Votacion::getMapPartidosVotos)
                .orElseGet(HashMap::new);
    }

    public void crearVotacion(String nombre, String usuario) {
        Votacion nuevo = new Votacion();
        nuevo.setNombre_votacion(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setMapPartidosVotos(new HashMap<>());
        votacionCRUD.create(nuevo);
    }

    public void borrarVotacion(String nombre, String usuario) {
        votacionCRUD.borrarEntidad(getIdDesdeNombreUsuario(nombre, usuario));
    }

    public void anadirPartidoVotos(String nombre, String usuario, String partido, int votos) {
        Map<String, Integer> map = getResutados(nombre, usuario);
        Integer anterior = map.putIfAbsent(partido, votos);
        if (anterior != null) {
            throw new PartidoExiste(partido);
        }
        Votacion nuevo = new Votacion();
        nuevo.setMapPartidosVotos(map);
        nuevo.setNombre_votacion(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId_votacion(getIdDesdeNombreUsuario(nombre, usuario));
        votacionCRUD.update(nuevo);
    }

    public void borrarPartidoVotos(String nombre, String usuario, String partido) {
        Map<String, Integer> map = getResutados(nombre, usuario);
        Integer valor = map.remove(partido);
        if (valor == null) {
            throw new PartidoNoExiste(partido);
        }
        Votacion nuevo = new Votacion();
        nuevo.setMapPartidosVotos(map);
        nuevo.setNombre_votacion(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId_votacion(getIdDesdeNombreUsuario(nombre, usuario));
        votacionCRUD.update(nuevo);
    }

    public void modificarPartidoVotos(String nombre, String usuario, String partido, int votos) {
        Map<String, Integer> map = getResutados(nombre, usuario);
        if (map.containsKey(partido)) {
            map.put(partido, votos);
        } else {
            throw new IllegalArgumentException("El partido " + partido + " no está en la lista");
        }
        Votacion nuevo = new Votacion();
        nuevo.setMapPartidosVotos(map);
        nuevo.setNombre_votacion(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId_votacion(getIdDesdeNombreUsuario(nombre, usuario));
        votacionCRUD.update(nuevo);
    }

}
