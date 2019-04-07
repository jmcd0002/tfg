package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Component
public class UsuarioCRUD extends EntityCRUD<Usuario> {

    protected static final Logger LOG = LoggerFactory.getLogger(UsuarioCRUD.class);

    @Override
    public void create(Usuario elemento) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(elemento);
            LOG.info("Creado usuario " + elemento.getNombre() + " con clave " + elemento.getClave());
            tx.commit();
        } catch (Exception e) {
            LOG.error("Error al crear usuario: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario getEntidadPorId(Object idUsuario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Usuario usuario = em.find(Usuario.class, idUsuario);
            LOG.info("Encontrado usuario " + usuario.getNombre() + " con clave " + usuario.getClave());
            return usuario;
        } catch (Exception e) {
            LOG.error("Error al buscar usuario: " + e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean update(Usuario elemento) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Usuario usuario = em.find(Usuario.class, elemento.getNombre());
            tx.begin();
            usuario.setClave(elemento.getClave());
            tx.commit();
            LOG.info("Usuario actualizado " + elemento.getNombre() + " con clave " + elemento.getClave());
            return true;
        } catch (Exception e) {
            LOG.error("Error al actualizar usuario: " + e.getMessage());
            tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void borrarEntidad(Object id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Usuario usuario = em.find(Usuario.class,id);
            tx.begin();
            em.remove(usuario);
            LOG.info("Borrado usuario  " + usuario.getNombre() + " con clave " + usuario.getClave());
            tx.commit();
        } catch (Exception e) {
            LOG.error("Error al borrar usuario: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
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
    public List<Usuario> executeSelectSQL(String conditions, String alias) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + alias + " from Usuario " + alias + " where " + conditions).getResultList());
    }
}
