package jmcd.tfg.persistencia.crud;

import jmcd.tfg.persistencia.model.Votacion;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Component
public class VotacionCRUD extends EntityCRUD<Votacion> {

    @Override
    public void create(Votacion elemento) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(elemento);
            tx.commit();
            LOG.info("Creada votacion " + elemento.getNombreVotacion() + " con id " + elemento.getIdVotacion());
        } catch (Exception e) {
            LOG.error("Error al crear votacion: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Votacion getEntidadPorId(Object idVotacion) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Votacion votacion = em.find(Votacion.class, idVotacion);
            LOG.info("Encontrada votacion " + votacion.getNombreVotacion() + " con id " + votacion.getIdVotacion());
            return votacion;
        } catch (Exception e) {
            LOG.error("Error al buscar votacion: " + e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public boolean update(Votacion elemento) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Votacion votacion = em.find(Votacion.class, elemento.getIdVotacion());
            tx.begin();
            votacion.setNombreVotacion(elemento.getNombreVotacion());
            votacion.setMapPartidosVotos(elemento.getMapPartidosVotos());
            tx.commit();
            LOG.info("Votacion actualizada " + elemento.getNombreVotacion() + " con id " + elemento.getIdVotacion());
            return true;
        } catch (Exception e) {
            LOG.error("Error al actualizar votacion: " + e.getMessage());
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
            Votacion votacion = em.find(Votacion.class, id);
            tx.begin();
            em.remove(votacion);
            tx.commit();
            LOG.info("Borrada votacion" + votacion.getNombreVotacion() + " con clave " + votacion.getIdVotacion());
        } catch (Exception e) {
            LOG.error("Error al borrar votacion: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }
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

//    private boolean comprobarCamposCambiados(Votacion original, Votacion nuevo) {
//        return original.getNombreVotacion().equals(nuevo.getNombreVotacion()) && original.getMapPartidosVotos().equals(nuevo.getMapPartidosVotos());
//    }

    @Override
    public List<Votacion> executeSelectSQL(String condiciones, String alias) {
        return dbConsult(entityManager -> entityManager.createQuery("Select " + alias + " from Votacion " + alias + " where " + condiciones).getResultList());
    }

}
