package jmcd.tfg.persistencia.excepcion;

public class VotacionNoExiste extends IllegalArgumentException {

    public VotacionNoExiste(String nombreVotacion) {
        super("La votacion " + nombreVotacion + " no existe");
    }
}
