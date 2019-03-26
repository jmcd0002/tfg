package jmcd.tfg.election.rest.pojo;

import java.util.Map;

public class Votacion {

    private String id_votacion;
    private String nombre_votacion;
    private Usuario usuario;
    private Map<String, Integer> mapPartidosVotos;


    public String getId_votacion() {
        return id_votacion;
    }

    public void setId_votacion(String id_votacion) {
        this.id_votacion = id_votacion;
    }

    public String getNombre_votacion() {
        return nombre_votacion;
    }

    public void setNombre_votacion(String nombre_votacion) {
        this.nombre_votacion = nombre_votacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Map<String, Integer> getMapPartidosVotos() {
        return mapPartidosVotos;
    }

    public void setMapPartidosVotos(Map<String, Integer> mapPartidosVotos) {
        this.mapPartidosVotos = mapPartidosVotos;
    }
}
