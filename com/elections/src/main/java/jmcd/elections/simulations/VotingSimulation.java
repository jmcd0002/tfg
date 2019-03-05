package jmcd.elections.simulations;

import java.util.Map;

/**
 * Interfaz que debe ser implementada por toda clase que simula una votación
 */
@FunctionalInterface
public interface VotingSimulation {

    /**
     * Obtener la simulación
     * @return una simulación de votación
     */
    Map<String,Integer> getVotes();
}
