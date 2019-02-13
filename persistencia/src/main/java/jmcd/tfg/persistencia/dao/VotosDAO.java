package jmcd.tfg.persistencia.dao;

import jmcd.tfg.persistencia.crud.UsuarioCRUD;
import jmcd.tfg.persistencia.crud.VotosCRUD;
import jmcd.tfg.persistencia.excepcion.PartidoExiste;
import jmcd.tfg.persistencia.excepcion.PartidoNoExiste;
import jmcd.tfg.persistencia.model.Votos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VotosDAO {

    @Autowired
    private VotosCRUD votosCRUD;
    @Autowired
    private UsuarioCRUD usuarioCRUD;

    public List<String> getListaResultados(String usuario){
        return votosCRUD.executeSelectSQL("v.usuario.nombre = "+usuario,"v")
                .stream()
                .map(Votos::getNombre)
                .collect(Collectors.toList());
    }

    private String getIdDesdeNombreUsuario(String nombre, String usuario){
        return votosCRUD.executeSelectSQL("v.usuario.nombre = "+usuario+" and v.nombre = "+nombre,"v")
                .stream()
                .findFirst()
                .map(Votos::getId)
                .orElseGet(String::new);
    }

    public Map<String,Integer> getResutados (String nombre, String usuario){
        return votosCRUD.executeSelectSQL("v.usuario.nombre = "+usuario+" and v.nombre = "+nombre,"v")
                .stream()
                .findFirst()
                .map(Votos::getMapVotos)
                .orElseGet(HashMap::new);
    }

    public void crearVotacion(String nombre, String usuario){
        Votos nuevo=new Votos();
        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setMapVotos(new HashMap<>());
        votosCRUD.create(nuevo);
    }

    public void borrarVotacion(String nombre, String usuario){
        votosCRUD.borrarEntidad(getIdDesdeNombreUsuario(nombre,usuario));
    }

    public void anadirPartidoVotos(String nombre, String usuario, String partido, int votos){
        Map<String,Integer> map=getResutados(nombre,usuario);
        Integer anterior=map.putIfAbsent(partido,votos);
        if (anterior!=null){
            throw new PartidoExiste(partido);
        }
        Votos nuevo=new Votos();
        nuevo.setMapVotos(map);
        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId(getIdDesdeNombreUsuario(nombre,usuario));
        votosCRUD.update(nuevo);
    }

    public void borrarPartidoVotos(String nombre,String usuario,String partido){
        Map<String,Integer> map=getResutados(nombre,usuario);
        Integer valor=map.remove(partido);
        if (valor==null){
            throw new PartidoNoExiste(partido);
        }
        Votos nuevo=new Votos();
        nuevo.setMapVotos(map);
        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId(getIdDesdeNombreUsuario(nombre,usuario));
        votosCRUD.update(nuevo);
    }

    public void modificarPartidoVotos(String nombre, String usuario,String partido, int votos){
        Map<String,Integer> map=getResutados(nombre,usuario);
        if (map.containsKey(partido)){
            map.put(partido,votos);
        }
        else{
            throw new IllegalArgumentException("El partido "+partido+" no está en la lista");
        }
        Votos nuevo=new Votos();
        nuevo.setMapVotos(map);
        nuevo.setNombre(nombre);
        nuevo.setUsuario(usuarioCRUD.getEntidadPorId(usuario));
        nuevo.setId(getIdDesdeNombreUsuario(nombre,usuario));
        votosCRUD.update(nuevo);
    }

}
