package jmcd.tfg.persistencia.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {
    @Id
    private String nombre;
    private String clave;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


}
