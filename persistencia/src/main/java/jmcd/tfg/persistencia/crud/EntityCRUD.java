package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.dao.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Function;

public abstract class EntityCRUD<T> {

    protected static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    abstract void create(T elemento);

    abstract T getEntidadPorId(Object id);

    abstract boolean update(T elemento);

    abstract void borrarEntidad(Object id);

    abstract boolean createOrUpdate(T elemento);

    private static String persistencia = "persistencia";

    protected static EntityManagerFactory entityManagerFactory;

    public static void initPersistencia() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistencia);
    }

    public static String getPersistencia() {
        return persistencia;
    }

    public static void setPersistencia(String pers) {
        persistencia = pers;
    }

    public static void closePersistencia() {
        entityManagerFactory.close();
    }

    public boolean exists(Object id) {
        return getEntidadPorId(id) != null;
    }

    abstract List<T> executeSelectSQL(String condiciones, String alias);

    protected static <S> S dbConsult(Function<EntityManager, S> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        S solution = null;
        try {
            solution = action.apply(entityManager);
        } catch (Exception e) {
            LOG.error("Error en dbConsult: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return solution;
    }

//    protected Consumer crear = elemento -> dbTransactionalAction(EntityManager::persist, elemento);
//
//    protected static <S> void dbTransactionalAction(BiConsumer<EntityManager, S> action, S elemento) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction tx = entityManager.getTransaction();
//        try {
//            tx.begin();
//            action.accept(entityManager, elemento);
//            tx.commit();
//        } catch (Exception e) {
//            LOG.error("Error en dbTransactionalAction: " + e.getMessage());
//            tx.rollback();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    protected Consumer borrar = id -> borrar(entityId -> dbTransactionalAction(EntityManager::remove, entityId), id);
//
//    private void borrar(Consumer<Object> accion, Object entidadId) {
//        if (exists(entidadId)) {
//            accion.accept(entidadId);
//        }
//    }

}
