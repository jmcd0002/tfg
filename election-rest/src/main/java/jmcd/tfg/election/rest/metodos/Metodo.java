package jmcd.tfg.election.rest.metodos;

import jmcd.elections.systems.div.Divisor;
import jmcd.elections.systems.div.Divisors;
import jmcd.elections.systems.quo.Quota;
import jmcd.elections.systems.quo.Quotas;
import jmcd.elections.systems.quo.Remainder;

/**
 * Classe que contiene los métodos disponibles en nuestro sistema.
 */
public enum Metodo {

    DHONT(MetodoTipo.DIVISOR,"D'Hont", Divisors::divisorsDHont,null,null),
    WEBBER(MetodoTipo.DIVISOR,"Webber", Divisors::divisorsSaint,null,null),
    DANES(MetodoTipo.DIVISOR,"Danes",Divisors::divisorsDin,null,null),
    HAMILTON(MetodoTipo.QUOTA,"Hamilton",null,Quotas::quotaStandard,Quotas::remaindersLargestRemainder),
    HAMILTON_RELATIVO(MetodoTipo.QUOTA,"Hamilton Relativo",null,Quotas::quotaStandard,Quotas::remaindersLargestRemainderRelative);

    private MetodoTipo tipo;
    private String nombre;
    private Divisor div;
    private Quota quot;
    private Remainder rem;

    Metodo(MetodoTipo tip,String nom,Divisor div,Quota<String> quot,Remainder rem){
        this.tipo=tip;
        this.nombre=nom;
        this.div=div;
        this.quot=quot;
        this.rem=rem;
    }

    public MetodoTipo getTipo(){
        return tipo;
    }

    public String getNombre(){
        return nombre;
    }

    public Divisor getDivisor() throws NoSuchMethodException{
        if (tipo.equals(MetodoTipo.DIVISOR)){
            return div;
        }
        throw new NoSuchMethodException("No es un método de divisor");
    }

    public Quota getQuota() throws NoSuchMethodException{
        if (tipo.equals(MetodoTipo.QUOTA)){
            return quot;
        }
        throw new NoSuchMethodException("No es un método de quotas");
    }

    public Remainder getRemainder() throws NoSuchMethodException{
        if (tipo.equals(MetodoTipo.QUOTA)){
            return rem;
        }
        throw new NoSuchMethodException("No es un método de quotas");
    }

}
