package jmcd.tfg.election.rest.servicelayer;

import jmcd.elections.systems.div.Divisors;
import jmcd.elections.systems.quo.Quotas;
import jmcd.tfg.election.rest.metodos.Metodo;
import jmcd.tfg.election.rest.metodos.MetodoTipo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MetodosServicio {

    private static final Logger LOG = LoggerFactory.getLogger(MetodosServicio.class);

    public List<Metodo> getListaMetodos(String tipo) {
        if (StringUtils.isEmpty(tipo)){
            return Arrays.asList(Metodo.values());
        }
        return Arrays.stream(Metodo.values())
                .filter(m->m.getTipo().getNombre().equals(tipo))
                .collect(Collectors.toList());
    }

    public Map<String,Integer> computarSolucion(Map<String,Integer> votos, int esc, String metodo){
        Map<String,Integer> solution=new HashMap<>();
        try{
            solution=computeSolution(votos,esc,metodo);
        }
        catch (NoSuchMethodException exception){
            LOG.error("No existe ese metodo");
        }
        catch (IllegalArgumentException exception){
            LOG.error("Un error con el tipo de metodo");
        }
        return solution;
    }

    private Map<String, Integer> computeSolution(Map<String, Integer> votos, int esc, String metodo) throws NoSuchMethodException{
        Optional<Metodo> met=Arrays.stream(Metodo.values())
                .filter(m->m.getNombre().equals(metodo))
                .findFirst();
        if (met.isPresent()){
            Metodo me=met.get();
            if (me.getTipo().equals(MetodoTipo.DIVISOR)){
                return Divisors.methodDivisor(votos,esc,me.getDivisor());
            }
            else if (me.getTipo().equals(MetodoTipo.QUOTA)){
                return Quotas.methodQuota(votos,esc,me.getQuota(),me.getRemainder());
            }
        }
        return new HashMap<>();
    }
}
