package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Votos;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VotosCRUD extends EntityCRUD<Votos> {

    @Override
    public Votos getEntidadPorId(Object id) {
        return dbConsult(entityManager -> entityManager.find(Votos.class, id));
    }

    @Override
    public void borrarEntidad(Object id) {
        borrado.accept(getEntidadPorId(id));
    }

    @Override
    public List<Votos> executeSelectSQL(String conditions, String name) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + name + " from Votos " + name + " where " + conditions).getResultList());
    }

    @Override
    public boolean update(Votos elemento) {
        Votos original = getEntidadPorId(elemento.getId());
        if (!comprobarCamposCambiados(original, elemento)) {
            dbTransactionalAction(((entityManager, o) -> {
                original.setNombre(elemento.getNombre());
                original.setMapVotos(elemento.getMapVotos());
            }), null);
            return true;
        }
        return false;
    }

    private boolean comprobarCamposCambiados(Votos original, Votos nuevo) {
        return original.getNombre().equals(nuevo.getNombre()) && original.getMapVotos().equals(nuevo.getMapVotos());
    }

    @Override
    public void create(Votos elemento) {
        persist.accept(elemento);
    }

    @Override
    public boolean createOrUpdate(Votos elemento) {
        if (exists(elemento.getId())) {
            return update(elemento);
        } else {
            create(elemento);
            return false;
        }
    }
}
