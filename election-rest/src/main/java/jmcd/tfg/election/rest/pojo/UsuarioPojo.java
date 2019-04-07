package jmcd.tfg.election.rest.pojo;


public class UsuarioPojo {
    private String nombre;
    private String clave;

    public UsuarioPojo() {
    }

    public UsuarioPojo(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

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
