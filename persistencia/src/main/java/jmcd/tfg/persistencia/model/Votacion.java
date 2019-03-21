package jmcd.tfg.persistencia.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre_votacion", "usuario_nombre"}))
public class Votacion {
    @Id
    private String id_votacion;
    private String nombre_votacion;
    @ManyToOne
    private Usuario usuario;

    @ElementCollection
    @CollectionTable(name = "Partidos_Votos",
            joinColumns = @JoinColumn(name = "Id_Partidos_Votos"))
    @MapKeyColumn(name = "Partido")
    @Column(name = "Votos")
    private Map<String, Integer> mapPartidosVotos;

    public String getId_votacion() {
        return id_votacion;
    }

    public void setId_votacion(String id) {
        this.id_votacion = id;
    }

    public Map<String, Integer> getMapPartidosVotos() {
        return mapPartidosVotos;
    }

    public void setMapPartidosVotos(Map<String, Integer> votos) {
        this.mapPartidosVotos = votos;
    }

    public String getNombre_votacion() {
        return nombre_votacion;
    }

    public void setNombre_votacion(String nombre) {
        this.nombre_votacion = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
