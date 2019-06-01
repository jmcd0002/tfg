package jmcd.tfg.election.rest.controller;

import jmcd.tfg.election.rest.metodos.Metodo;
import jmcd.tfg.election.rest.servicelayer.MetodosServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compute")
public class ElectoralControlador {

    public ElectoralControlador(MetodosServicio metodosServicio){
        this.metodosServicio=metodosServicio;
    }

    private MetodosServicio metodosServicio;

    @GetMapping("/metodos/{tipo}")
    public List<String> getListaMetodos(@PathVariable String tipo){
        return metodosServicio.getListaMetodos(tipo).stream()
                .map(Metodo::getNombre)
                .collect(Collectors.toList());
    }

    @PostMapping("/metodo/{metodo}")
    public Map<String,Integer> computarReparto(@RequestBody Map<String,Integer> votos,@RequestParam int esc, @PathVariable String metodo){
        return metodosServicio.computarSolucion(votos,esc,metodo);
    }

}
