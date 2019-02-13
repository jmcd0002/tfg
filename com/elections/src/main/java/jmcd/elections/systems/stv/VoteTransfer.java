package jmcd.elections.systems.stv;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface VoteTransfer {

    Map<String, Double> transferenceOfVotes(String candidate, Map<List<String>,Integer> votes, int seats, List<String> validCandidates);

}
