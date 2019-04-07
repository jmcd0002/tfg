package jmcd.tfg.election.rest.pojo;
import java.util.Map;

public class VotacionPojo {

    private int idVotacion;
    private String nombreVotacion;
    private UsuarioPojo usuarioPojo;
    private Map<String, Integer> mapPartidosVotos;

    public VotacionPojo() {
    }

    public VotacionPojo(int idVotacion, String nombreVotacion, UsuarioPojo usuarioPojo, Map<String, Integer> mapPartidosVotos) {
        this.idVotacion = idVotacion;
        this.nombreVotacion = nombreVotacion;
        this.usuarioPojo = usuarioPojo;
        this.mapPartidosVotos = mapPartidosVotos;
    }

    public int getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(int idVotacion) {
        this.idVotacion = idVotacion;
    }

    public String getNombreVotacion() {
        return nombreVotacion;
    }

    public void setNombreVotacion(String nombreVotacion) {
        this.nombreVotacion = nombreVotacion;
    }

    public UsuarioPojo getUsuarioPojo() {
        return usuarioPojo;
    }

    public void setUsuarioPojo(UsuarioPojo usuarioPojo) {
        this.usuarioPojo = usuarioPojo;
    }

    public Map<String, Integer> getMapPartidosVotos() {
        return mapPartidosVotos;
    }

    public void setMapPartidosVotos(Map<String, Integer> mapPartidosVotos) {
        this.mapPartidosVotos = mapPartidosVotos;
    }
}
