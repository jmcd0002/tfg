package jmcd.elections.simulations;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PartyListVotingSimulation implements VotingSimulation {

    private static final int MAX_VOTES=5000000;
    private static final Random RANDOM=new Random();

    private int numOfParties;

    public PartyListVotingSimulation(int numOfParties){
        this.numOfParties=numOfParties;
    }

    public PartyListVotingSimulation(int minParties,int maxParties){
        setNumOfParties(minParties,maxParties);
    }

    public void setNumOfParties(int minParties,int maxParties){
        this.numOfParties=RANDOM.nextInt(maxParties-minParties+1)+minParties;
    }

    public void setNumOfParties(int numParties){
        this.numOfParties=numParties;
    }

    public int getNumOfParties(){
        return this.numOfParties;
    }

    private Map<String, Integer> getVotes(int numOfParties) {

        return IntStream.range(0, numOfParties)
                .mapToObj(i -> "Party " + i)
                .collect(Collectors.toMap(Function.identity(),i->getRandomNumberOfVotes()));
    }

    private int getRandomNumberOfVotes() {
        return RANDOM.nextInt(MAX_VOTES);
    }

    @Override
    public Map<String, Integer> getVotes() {
        return getVotes(numOfParties);
    }
}
