package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotacionCRUD;
import jmcd.tfg.persistencia.excepcion.PartidoExiste;
import jmcd.tfg.persistencia.excepcion.PartidoNoExiste;
import jmcd.tfg.persistencia.model.Votacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    /**
     * Metodo que crea una votacion
     *
     * @param nombreVotacion nombre de la votacion
     * @param usuario        nombre del usuario al que pertenece la votacion
     */
    public void crearVotacion(String nombreVotacion, String usuario) {
        Votacion nuevo = new Votacion();
        nuevo.setNombreVotacion(nombreVotacion);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setMapPartidosVotos(new HashMap<>());
        votacionCRUD.create(nuevo);
        LOG.info("Creada Votacion: " + nuevo.getIdVotacion() + ", " + nuevo.getNombreVotacion() + ", " + nuevo.getUsuario());
    }

    /**
     * Metodo que obtiene todas las votaciones que tiene un usuario
     *
     * @param usuario nombre del usuario
     * @return lista con las votaciones que ha introducido un usuario
     */
    public List<String> getListaVotaciones(String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario, "v")
                .stream()
                .map(Votacion::getNombreVotacion)
                .collect(Collectors.toList());
    }

    /**
     * Metodo que obtiene una votacion de un usuario
     *
     * @param nombre  nombre de la votacion
     * @param usuario nombre del usuario al que pertenece la votacion
     */
    public Votacion getVotacion(String nombre, String usuario) {
        return votacionCRUD.getEntidadPorId(getIdDesdeNombreUsuario(nombre, usuario));
    }

    /**
     * Metodo que borra una votacion
     *
     * @param nombre  nombre de la votacion
     * @param usuario nombre del usuario al que pertenece la votacion
     */
    public void borrarVotacion(String nombre, String usuario) {
        votacionCRUD.borrarEntidad(getIdDesdeNombreUsuario(nombre, usuario));
    }

    /**
     * Metodo que añade un partido y sus respectivos votos
     *
     * @param nombre  nombre de la votacion
     * @param usuario nombre del usuario al que pertenece la votacion
     * @param partido nombre del partido
     * @param votos   numero de votos
     */
    public void anadirPartidoVotos(String nombre, String usuario, String partido, int votos) {
        Map<String, Integer> mapPartidosVotos = getPartidosVotos(nombre, usuario);
        Integer anterior = mapPartidosVotos.putIfAbsent(partido, votos);
        if (anterior != null) {
            throw new PartidoExiste(partido);
        }
        Votacion nuevo = new Votacion(
                getIdDesdeNombreUsuario(nombre, usuario),
                nombre,
                usuarioCRUD.getEntidadPorId(usuario),
                mapPartidosVotos);
        votacionCRUD.update(nuevo);
    }

    /**
     * Metodo que obtiene todos los partidos y sus respectivos votos
     *
     * @param nombreVotacion  nombre de la votacion
     * @param usuario nombre del usuario de la votacion
     * @return mapa de votos de la votacion
     */
    public Map<String, Integer> getPartidosVotos(String nombreVotacion, String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario + " and v.nombre = " + nombreVotacion, "v")
                .stream()
                .findFirst()
                .map(Votacion::getMapPartidosVotos)
                .orElseGet(HashMap::new);
    }

    /**
     * Metodo que modifica un partido y sus respectivos votos
     *
     * @param nombreVotacion  nombre de la votacion
     * @param usuario nombre del usuario al que pertenece la votacion
     * @param partido nombre del partido
     * @param votos   numero de votos
     */
    public void modificarPartidoVotos(String nombreVotacion, String usuario, String partido, int votos) {
        Map<String, Integer> mapPartidosVotos = getPartidosVotos(nombreVotacion, usuario);
        if (mapPartidosVotos.containsKey(partido)) {
            mapPartidosVotos.put(partido, votos);
        } else {
            throw new IllegalArgumentException("El partido " + partido + " no está en la lista");
        }
        Votacion nuevo = new Votacion(
                getIdDesdeNombreUsuario(nombreVotacion, usuario),
                nombreVotacion,
                usuarioCRUD.getEntidadPorId(usuario),
                mapPartidosVotos);
        votacionCRUD.update(nuevo);
    }

    /**
     * Metodo que borra un partido y sus respectivos votos
     *
     * @param nombreVotacion  nombre de la votacion
     * @param usuario nombre del usuario al que pertenece la votacion
     * @param partido nombre del partido
     */
    public void borrarPartidoVotos(String nombreVotacion, String usuario, String partido) {
        Map<String, Integer> mapPartidosVotos = getPartidosVotos(nombreVotacion, usuario);
        Integer valor = mapPartidosVotos.remove(partido);
        if (valor == null) {
            throw new PartidoNoExiste(partido);
        }
        Votacion nuevo = new Votacion(
                getIdDesdeNombreUsuario(nombreVotacion, usuario),
                nombreVotacion,
                usuarioCRUD.getEntidadPorId(usuario),
                mapPartidosVotos);
        votacionCRUD.update(nuevo);
    }

    /**
     * Devueve el id de la votacion dado su nombre y el nombre del usuario al que pertenece
     *
     * @param nombreVotacion  nombre de la votacion
     * @param usuario nombre del usuario de la votacion
     * @return id de la votacion
     */
    private String getIdDesdeNombreUsuario(String nombreVotacion, String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = " + usuario + " and v.nombre = " + nombreVotacion, "v")
                .stream()
                .findFirst()
                .map(Votacion::getIdVotacion)
                .orElseGet(String::new);
    }

}
