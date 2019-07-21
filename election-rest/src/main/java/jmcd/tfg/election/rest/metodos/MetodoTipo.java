package jmcd.tfg.election.rest.metodos;

/**
 * Classe que contiene los tipos de métodos disponibles en nuestro sistema.
 */
public enum MetodoTipo{

    QUOTA("quota"),
    DIVISOR("divisor");

    private String nombre;

    MetodoTipo(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return nombre;
    }

}
