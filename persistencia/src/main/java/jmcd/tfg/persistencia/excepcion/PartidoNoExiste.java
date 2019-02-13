package jmcd.tfg.persistencia.excepcion;

public class PartidoNoExiste extends IllegalArgumentException {

    public PartidoNoExiste(String partido){
        super("El partido "+partido+" no está en la lista");
    }
}
