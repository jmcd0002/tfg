package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class UsuarioCRUD extends EntityCRUD<Usuario> {

    @Override
    public void create(Usuario elemento) {
        Logger.getAnonymousLogger().info("Persistiendo " + elemento.getNombre() + " con clave " + elemento.getClave());
        crear.accept(elemento);
        Logger.getAnonymousLogger().info("Persistido " + elemento.getNombre() + " con clave " + elemento.getClave());
    }

    @Override
    public Usuario getEntidadPorId(Object id) {
        return dbConsult(entityManager -> entityManager.find(Usuario.class, id));
    }

    @Override
    public boolean update(Usuario elemento) {
        Usuario original = getEntidadPorId(elemento.getNombre());
        if (!original.getClave().equals(elemento.getClave())) {
            Logger.getAnonymousLogger().info("Actualizando " + original.getNombre() + " con clave " + original.getClave());

            dbTransactionalAction(((entityManager, o) -> original.setClave(elemento.getClave())), null);

            Logger.getAnonymousLogger().info("Actuaizado " + original.getNombre() + " con clave " + original.getClave());
            return true;
        }
        return false;
    }

    @Override
    public void borrarEntidad(Object id) {
        Logger.getAnonymousLogger().info("Borrando ");
        borrar.accept(id);
        Logger.getAnonymousLogger().info("Borrado ");
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

    @Override
    public List<Usuario> executeSelectSQL(String conditions, String name) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + name + " from Usuario " + name + " where " + conditions).getResultList());
    }
}
