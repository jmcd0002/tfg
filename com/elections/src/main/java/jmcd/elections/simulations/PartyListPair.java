package jmcd.elections.simulations;

import jmcd.utils.Pair;
import jmcd.utils.comparators.RandomComparator;

/**
 * Implementación de {@link jmcd.utils.Pair} donde la clave es el nombre del partido político
 * y están ordenados aleatoriamente.
 *
 * Este orden es utilizado para elegir un ganador aleatoriamente cuando en empate.
 * @param <S>
 */
public class PartyListPair<S extends Comparable<S>> extends Pair<String,S> {

    private static RandomComparator<String> randomComparator;

    /**
     * Constructor del par
     * @param st nombre del partido
     * @param d valor asociado al partido
     */
    public PartyListPair(String st, S d) {
        super(st, d,getOrCreateRandomComparator());
    }

    /**
     * Obtiene o crea un comparador aleatorio
     * @return un comparador aleatorio
     */
    private static RandomComparator<String> getOrCreateRandomComparator() {
        if (randomComparator==null){
            randomComparator = new RandomComparator<>();
        }
        return randomComparator;
    }

}
