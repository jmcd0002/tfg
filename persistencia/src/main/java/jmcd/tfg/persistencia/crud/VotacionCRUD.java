package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Votacion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VotacionCRUD extends EntityCRUD<Votacion> {

    @Override
    public void create(Votacion elemento) {
        persist.accept(elemento);
    }

    @Override
    public Votacion getEntidadPorId(Object id) {
        return dbConsult(entityManager -> entityManager.find(Votacion.class, id));
    }

    @Override
    public boolean update(Votacion elemento) {
        Votacion original = getEntidadPorId(elemento.getId_votacion());
        if (!comprobarCamposCambiados(original, elemento)) {
            dbTransactionalAction(((entityManager, o) -> {
                original.setNombre_votacion(elemento.getNombre_votacion());
                original.setMapPartidosVotos(elemento.getMapPartidosVotos());
            }), null);
            return true;
        }
        return false;
    }

    @Override
    public void borrarEntidad(Object id) {
        borrado.accept(getEntidadPorId(id));
    }

    @Override
    public boolean createOrUpdate(Votacion elemento) {
        if (exists(elemento.getId_votacion())) {
            return update(elemento);
        } else {
            create(elemento);
            return false;
        }
    }

    @Override
    public List<Votacion> executeSelectSQL(String conditions, String name) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + name + " from Votacion " + name + " where " + conditions).getResultList());
    }

    private boolean comprobarCamposCambiados(Votacion original, Votacion nuevo) {
        return original.getNombre_votacion().equals(nuevo.getNombre_votacion()) && original.getMapPartidosVotos().equals(nuevo.getMapPartidosVotos());
    }
}
