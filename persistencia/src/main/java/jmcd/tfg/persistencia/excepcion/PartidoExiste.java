package jmcd.tfg.persistencia.excepcion;

public class PartidoExiste extends IllegalArgumentException{

    public PartidoExiste(String partido){
        super("El partido "+partido+" ya ha sido introducido con unos votos");
    }
}
