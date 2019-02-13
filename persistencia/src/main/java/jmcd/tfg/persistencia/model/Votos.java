package jmcd.tfg.persistencia.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre","usuario_nombre"}))
public class Votos {
    @ElementCollection
    @CollectionTable(name = "BATCH_VOTOS",
            joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "PARTIDO")
    @Column(name = "VOTOS")
    private Map<String, Integer> mapVotos;
    private String nombre;
    @ManyToOne
    private Usuario usuario;
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getMapVotos() {
        return mapVotos;
    }

    public void setMapVotos(Map<String, Integer> votos) {
        this.mapVotos = votos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
