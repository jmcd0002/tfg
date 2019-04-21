package jmcd.tfg.persistencia.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Votacion {
    @Id
    @GeneratedValue
    private int idVotacion;
    private String nombreVotacion;
    @ManyToOne
    private Usuario usuario;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "partidosVotos",
            joinColumns = @JoinColumn(name = "idPartidosVotos"))
    @MapKeyColumn(name = "partido")
    @Column(name = "votos")
    private Map<String, Integer> mapPartidosVotos;

    public Votacion() {
        this.mapPartidosVotos = new HashMap<>();
    }

    public Votacion(int idVotacion, String nombreVotacion, Usuario usuario, Map<String, Integer> mapPartidosVotos) {
        this.idVotacion = idVotacion;
        this.nombreVotacion = nombreVotacion;
        this.usuario = usuario;
        this.mapPartidosVotos = mapPartidosVotos;
    }

    public int getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(int idVotacion) {
        this.idVotacion = idVotacion;
    }

    public Map<String, Integer> getMapPartidosVotos() {
        return mapPartidosVotos;
    }

    public void setMapPartidosVotos(Map<String, Integer> votos) {
        this.mapPartidosVotos = votos;
    }

    public String getNombreVotacion() {
        return nombreVotacion;
    }

    public void setNombreVotacion(String nombreVotacion) {
        this.nombreVotacion = nombreVotacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
