package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Votacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class VotacionCRUD extends EntityCRUD<Votacion> {

    @Override
    public void create(Votacion elemento) {
        Logger.getAnonymousLogger().info("Persistiendo " + elemento.getIdVotacion() + " con nombre de votacion " + elemento.getNombreVotacion());
        crear.accept(elemento);
        Logger.getAnonymousLogger().info("Persistiendo " + elemento.getIdVotacion() + " con nombre de votacion  " + elemento.getNombreVotacion());
    }

    @Override
    public Votacion getEntidadPorId(Object id) {
        return dbConsult(entityManager -> entityManager.find(Votacion.class, id));
    }

    @Override
    public boolean update(Votacion elemento) {
        Votacion original = getEntidadPorId(elemento.getIdVotacion());
        if (!comprobarCamposCambiados(original, elemento)) {
            Logger.getAnonymousLogger().info("Actualizando " + original.getIdVotacion() + " con nombre de votacion  " + original.getNombreVotacion());
            dbTransactionalAction(((entityManager, o) -> {
                original.setNombreVotacion(elemento.getNombreVotacion());
                original.setMapPartidosVotos(elemento.getMapPartidosVotos());
            }), null);
            Logger.getAnonymousLogger().info("Actualizado " + elemento.getIdVotacion() + " con nombre de votacion  " + elemento.getNombreVotacion());
            return true;
        }
        return false;
    }

    @Override
    public boolean createOrUpdate(Votacion elemento) {
        if (exists(elemento.getIdVotacion())) {
            return update(elemento);
        } else {
            create(elemento);
            return false;
        }
    }

    @Override
    public void borrarEntidad(Object id) {
        Logger.getAnonymousLogger().info("Borrando ");
        borrar.accept(getEntidadPorId(id));
        Logger.getAnonymousLogger().info("Borrando ");
    }

    @Override
    public List<Votacion> executeSelectSQL(String conditions, String name) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + name + " from Votacion " + name + " where " + conditions).getResultList());
    }

    private boolean comprobarCamposCambiados(Votacion original, Votacion nuevo) {
        return original.getNombreVotacion().equals(nuevo.getNombreVotacion()) && original.getMapPartidosVotos().equals(nuevo.getMapPartidosVotos());
    }
}
