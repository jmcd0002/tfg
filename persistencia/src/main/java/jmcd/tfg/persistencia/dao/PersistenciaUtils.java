package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.EntityCRUD;

/**
 * Clase con los metodos necesarios para gestionar la persistencia desde fuera de nuestro modulo Persistencia
 */
public class PersistenciaUtils {

    public static void initPersistencia() {
        EntityCRUD.initPersistencia();
    }

    public static void closePersistencia() {
        EntityCRUD.closePersistencia();
    }

    public static String getPersistencia() {
        return EntityCRUD.getPersistencia();
    }

    public static void setPersistencia(String pers) {
        EntityCRUD.setPersistencia(pers);
    }


}