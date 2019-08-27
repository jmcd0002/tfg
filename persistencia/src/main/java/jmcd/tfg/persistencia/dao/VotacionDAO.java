package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotacionCRUD;
import jmcd.tfg.persistencia.excepcion.PartidoExiste;
import jmcd.tfg.persistencia.excepcion.PartidoNoExiste;
import jmcd.tfg.persistencia.excepcion.VotacionNoExiste;
import jmcd.tfg.persistencia.model.Votacion;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import jmcd.tfg.persistencia.utils.VotacionPopulate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VotacionDAO {

    public VotacionDAO(VotacionCRUD votacionCRUD, UsuarioCRUD usuarioCRUD, VotacionPopulate votacionPopulate) {
        this.votacionPopulate = votacionPopulate;
        this.votacionCRUD = votacionCRUD;
        this.usuarioCRUD = usuarioCRUD;
    }

    private VotacionCRUD votacionCRUD;

    private UsuarioCRUD usuarioCRUD;

    private VotacionPopulate votacionPopulate;

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    /**
     * Metodo que crea una votacion
     *
     * @param nombreVotacion nombre de la votacion
     * @param usuario        nombre del usuario al que pertenece la votacion
     */
    public VotacionPojo crearVotacion(String nombreVotacion, String usuario) {
        Votacion nuevo = new Votacion();
        nuevo.setNombreVotacion(nombreVotacion);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setMapPartidosVotos(new HashMap<>());
        votacionCRUD.create(nuevo);
        String mensaje = String.format("Creada Votacion: %s, %s, %s", nuevo.getIdVotacion(), nuevo.getNombreVotacion(), nuevo.getUsuario());
        LOG.info(mensaje);
        return votacionPopulate.populate(nuevo);
    }

//    /**
//     * Metodo que modifica una votacion de un usuario
//     *
//     * @param idVotacion id de la votacion
//     * @return
//     */
//    public VotacionPojo modificarVotacion(int idVotacion, String nombreVotacion) {
//        Votacion votacion = votacionCRUD.getEntidadPorId(idVotacion);
//        if (!nombreVotacion.equals(votacion.getNombreVotacion())) {
//            votacion.setNombreVotacion(nombreVotacion);
//        }
//        votacionCRUD.update(votacion);
//        LOG.info("modificado nombre votacion: " + votacion.getNombreVotacion() + " con: " + nombreVotacion);
//        return votacionPopulate.populate(votacion);
//    }

    /**
     * Metodo que obtiene todas las votaciones que tiene un usuario
     *
     * @param usuario nombre del usuario
     * @return lista con las votaciones que ha introducido un usuario
     */
    public List<VotacionPojo> getListaVotaciones(String usuario) {
        return votacionCRUD.executeSelectSQL("v.usuario.nombre = '" + usuario + "'", "v")
                .stream()
                .map(votacionPopulate::populate)
                .collect(Collectors.toList());
    }

    /**
     * Metodo que obtiene una votacion de un usuario
     *
     * @param idVotacion id de la votacion
     * @return
     */
    public VotacionPojo getVotacion(int idVotacion) {
        return votacionPopulate.populate(votacionCRUD.getEntidadPorId(idVotacion));
//        LOG.info("El id de la votacion " + v.getNombreVotacion() + " es: " + v.getIdVotacion());
    }

    /**
     * Metodo que borra una votacion
     *
     * @param idVotacion nombre de la votacion
     */
    public void borrarVotacion(int idVotacion) {
        votacionCRUD.borrarEntidad(idVotacion);
        LOG.info("Votacion borrada");
    }

    /**
     * Metodo que añade un partido y sus respectivos votos
     *
     * @param idVotacion id de la votacion
     * @param partido    nombre del partido
     * @param votos      numero de votos
     */
    public void anadirPartidoVotos(int idVotacion, String partido, int votos) {
        Votacion votacion = votacionCRUD.getEntidadPorId(idVotacion);
        Map<String, Integer> mapPartidosVotos = votacion.getMapPartidosVotos();
        Integer existe = mapPartidosVotos.putIfAbsent(partido, votos);
        if (existe != null) {
            throw new PartidoExiste(partido);
        }
        votacion.setMapPartidosVotos(mapPartidosVotos);
        votacionCRUD.update(votacion);
        LOG.info("Añadido partido: " + partido + " con votos: " + votos);
    }

    /**
     * Metodo que obtiene todos los partidos y sus respectivos votos
     *
     * @param idVotacion nombre de la votacion
     * @return mapa de votos de la votacion
     */
    public Map<String, Integer> getPartidosVotos(int idVotacion) {
        String mensaje = String.format("Obteniendo todos los partidos de la votacion con id: %s", idVotacion);
        LOG.info(mensaje);
        return getVotacion(idVotacion).getMapPartidosVotos();
    }

    /**
     * Metodo que modifica un partido y sus respectivos votos
     *
     * @param idVotacion id de la votacion
     * @param partido    nombre del partido
     * @param votos      numero de votos
     */
    public void modificarPartidoVotos(int idVotacion, String partido, int votos) {
        Votacion votacion = votacionCRUD.getEntidadPorId(idVotacion);
        Map<String, Integer> mapPartidosVotos = votacion.getMapPartidosVotos();
        if (mapPartidosVotos.containsKey(partido)) {
            mapPartidosVotos.put(partido, votos);
        } else {
            throw new IllegalArgumentException("El partido " + partido + " no está en la lista");
        }
        votacion.setMapPartidosVotos(mapPartidosVotos);
        votacionCRUD.update(votacion);
        LOG.info("Modificado el partido: " + partido + " con los nuevso votos: " + votos);
    }

    /**
     * Metodo que borra un partido y sus respectivos votos
     *
     * @param idVotacion nombre de la votacion
     * @param partido    nombre del partido
     */
    public void borrarPartidoVotos(int idVotacion, String partido) {
        Votacion votacion = votacionCRUD.getEntidadPorId(idVotacion);
        Map<String, Integer> mapPartidosVotos = votacion.getMapPartidosVotos();
        Integer valor = mapPartidosVotos.remove(partido);
        if (valor == null) {
            throw new PartidoNoExiste(partido);
        }
        votacion.setMapPartidosVotos(mapPartidosVotos);
        votacionCRUD.update(votacion);
        LOG.info("Borrado partido: " + partido);
    }

    /**
     * Devueve el id de la votacion dado su nombre y el nombre del usuario al que pertenece
     *
     * @param nombreVotacion nombre de la votacion
     * @param usuario        nombre del usuario de la votacion
     * @return id de la votacion
     */
    public Integer getIdDesdeNombreUsuario(String nombreVotacion, String usuario) {
        return votacionCRUD.executeSelectSQL("v.nombreVotacion = '" + nombreVotacion + "' and v.usuario.nombre = '" + usuario + "'", "v")
                .stream()
                .findFirst()
                .map(Votacion::getIdVotacion)
                .orElseThrow(() -> new VotacionNoExiste(nombreVotacion));

// SEGUNDA VERSION
//        List<Votacion> votaciones = getListaVotaciones(usuario);
//        for (Votacion v : votaciones) {
//            if (v.getNombreVotacion().equals(nombreVotacion)) {
//                LOG.info("El id de la votacion " + v.getNombreVotacion() + " es: " + v.getIdVotacion());
//                return v.getIdVotacion();
//            } else {
//                throw new VotacionNoExiste(nombreVotacion);
//            }
//        }
//        return null;

// TERCERA VERSION (FUNCIONA)
//        LOG.info("Voy a llamar a executeSelectSQL");
//        List<Votacion> resutado = votacionCRUD.executeSelectSQL("v.nombreVotacion = '" + nombreVotacion + "' and v.usuario.nombre = '" + usuario + "'", "v");
//        if (resutado.size() == 1) {
//            LOG.info("El id de la votacion tarara" + resutado.get(0).getNombreVotacion() + " es: " + resutado.get(0).getIdVotacion());
//            return resutado.get(0).getIdVotacion();
//        } else {
//            LOG.info("Error en getIdDesdeNombreUsuario");
////            throw new VotacionNoExiste(nombreVotacion);
//        }
//        return null;
    }
}
