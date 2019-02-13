package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class UsuarioCRUD extends EntityCRUD<Usuario> {

    @Override
    public Usuario getEntidadPorId(Object id) {
        return dbConsult(entityManager -> entityManager.find(Usuario.class, id));
    }

    @Override
    public void borrarEntidad(Object id) {
        borrado.accept(id);
    }

    @Override
    public List<Usuario> executeSelectSQL(String conditions, String name) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + name + " from Usuario " + name + " where " + conditions).getResultList());
    }

    @Override
    public boolean update(Usuario elemento) {
        Usuario original = getEntidadPorId(elemento.getNombre());
        if (!original.getClave().equals(elemento.getClave())) {
            dbTransactionalAction(((entityManager, o) -> original.setClave(elemento.getClave())), null);
            return true;
        }
        return false;
    }

    @Override
    public void create(Usuario elemento) {
        Logger.getAnonymousLogger().info("Persisting " + elemento.getNombre() + " con clave " + elemento.getClave());
        persist.accept(elemento);
        Logger.getAnonymousLogger().info("Persisted " + elemento.getNombre() + " con clave " + elemento.getClave());
    }

    @Override
    public boolean createOrUpdate(Usuario elemento) {
        if (exists(elemento.getNombre())) {
            return update(elemento);
        } else {
            create(elemento);
            return false;
        }
    }
}
