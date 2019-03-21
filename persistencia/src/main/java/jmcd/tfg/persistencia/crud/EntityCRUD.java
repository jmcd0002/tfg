package jmcd.tfg.persistencia.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class EntityCRUD<T> {

    private static String persistencia = "persistencia";

    abstract void create(T elemento);

    abstract T getEntidadPorId(Object id);

    abstract boolean update(T elemento);

    abstract void borrarEntidad(Object id);

    abstract boolean createOrUpdate(T elemento);

    abstract List<T> executeSelectSQL(String conditions, String name);

    private static EntityManagerFactory entityManagerFactory;

    protected Consumer persist = elemento -> dbTransactionalAction(EntityManager::persist, elemento);
    protected Consumer borrado = id -> borrar(entityId -> dbTransactionalAction(EntityManager::remove, entityId), id);

    protected static <S> void dbTransactionalAction(BiConsumer<EntityManager, S> action, S elemento) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager, elemento);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
    }

    protected static <S> S dbConsult(Function<EntityManager, S> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        S solution = null;
        try {
            solution = action.apply(entityManager);
        } finally {
            entityManager.close();
        }
        return solution;
    }

    public static String getPersistencia() {
        return persistencia;
    }

    public static void setPersistencia(String pers) {
        persistencia = pers;
    }

    public static void initPersistencia() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistencia);
    }

    public static void closePersistencia() {
        entityManagerFactory.close();
    }

    private void borrar(Consumer<Object> accion, Object entidadId) {
        if (exists(entidadId)) {
            accion.accept(entidadId);
        }
    }

    public boolean exists(Object id) {
        return getEntidadPorId(id) != null;
    }

}
