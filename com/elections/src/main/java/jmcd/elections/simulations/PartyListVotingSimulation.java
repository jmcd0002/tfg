package jmcd.elections.simulations;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simulación de votación a listas de partidos
 */
public class PartyListVotingSimulation implements VotingSimulation {

    private static final int MAX_VOTES=5000000;
    private static final Random RANDOM=new Random();

    private int numOfParties;

    /**
     * Constructor de la simulación
     * @param numOfParties número de partidos
     */
    public PartyListVotingSimulation(int numOfParties){
        this.numOfParties=numOfParties;
    }

    /**
     * Constructor de la simulación
     * @param minParties número mínimo de partidos
     * @param maxParties número máximo de partidos
     */
    public PartyListVotingSimulation(int minParties,int maxParties){
        setNumOfParties(minParties,maxParties);
    }

    /**
     * Colocar el número de partidos
     * @param minParties
     * @param maxParties
     */
    private void setNumOfParties(int minParties,int maxParties){
        this.numOfParties=RANDOM.nextInt(maxParties-minParties+1)+minParties;
    }

    /**
     * Setter del número de partidos
     * @param numParties número de partidos
     */
    public void setNumOfParties(int numParties){
        this.numOfParties=numParties;
    }

    /**
     * Getter del número de partidos
     * @return número de partidos
     */
    public int getNumOfParties(){
        return this.numOfParties;
    }

    /**
     * Número de votos aleatorio, menor que el MAX_VOTES
     * @return
     */
    private int getRandomNumberOfVotes() {
        return RANDOM.nextInt(MAX_VOTES);
    }

    /**
     * Implementacion de {@link VotingSimulation#getVotes()}
     * @return votación aleatorio
     */
    @Override
    public Map<String, Integer> getVotes() {
        return IntStream.range(0, numOfParties)
                .mapToObj(i -> "Party " + i)
                .collect(Collectors.toMap(Function.identity(),i->getRandomNumberOfVotes()));
    }
}
