package jmcd.elections.systems.quo;

import jmcd.elections.simulations.PartyListPair;
import jmcd.elections.systems.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static jmcd.elections.systems.Test.Type.QUOTA;
import static jmcd.elections.systems.Test.Type.REMAINDER;

/**
 * This class is a library of quota methods of apportionment. There is a list of remainder apportionment, which start their names with 'remainders'
 * and a function that implements the quota apportionment. There is also a list of functions that compute quotas from the number of
 * seats and the number of votes.
 *
 * @author daconcep
 */
public final class Quotas {

    private Quotas() {
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc:   number of pieces to divide among the parties.
     * @param mm:    method that distributes the remainder votes.
     * @param quot:  quota function of the method
     * @return map with seats distributed
     */
    public static <T> Map<T, Integer> methodQuota(Map<T, Integer> votes, int esc, Quota<T> quot, Remainder<T> mm) {
        Map<T, Double> aux = new HashMap<>();
        Map<T, Integer> res = new HashMap<>();

        double d = quot.apply(votes, esc);
        votes.entrySet().stream()
                .map(e -> Map.entry(e.getKey(),
                        Map.entry((int) Math.floor(e.getValue() / d), e.getValue() / d - (int) Math.floor(e.getValue() / d))))
                .forEach(e -> {
                    res.put(e.getKey(), e.getValue().getKey());
                    aux.put(e.getKey(), e.getValue().getValue());
                });

        mm.apply(esc - res.values().stream().mapToInt(z -> z).sum(), aux, res);
        return res;
    }

    /**
     * @param esc:  number of pieces to divide among the parties.
     * @param dob:  remainders of the parties involved.
     * @param mint: previous apportionment.
     */
    @Test(type = REMAINDER)
    public static void remaindersLargestRemainder(int esc, Map<String, Double> dob, Map<String, Integer> mint) {

        dob.entrySet().stream()
                .map(e -> new PartyListPair<>(e.getKey(), e.getValue()))
                .sorted()
                .limit(esc)
                .forEach(e -> mint.put(e.getKey(), mint.get(e.getKey()) + 1));

    }


    /**
     * @param esc:  number of pieces to divide among the parties.
     * @param dob:  remainders of the parties involved.
     * @param mint: previous apportionment.
     */
    @Test(type = REMAINDER)
    public static void remaindersLargestRemainderRelative(int esc, Map<String, Double> dob, Map<String, Integer> mint) {
        dob = dob.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / mint.get(e.getKey())));

        remaindersLargestRemainder(esc, dob, mint);
    }


    /**
     * @param esc:  number of pieces to divide among the parties.
     * @param dob:  remainders of the parties involved.
     * @param mint: previous apportionment.
     */
    @Test(type = REMAINDER)
    public static void remaindersWinnerAll(int esc, Map<String, Double> dob, Map<String, Integer> mint) {
        dob.entrySet().stream()
                .map(e -> new PartyListPair<>(e.getKey(), e.getValue()))
                .sorted()
                .limit(1)
                .forEach(e -> mint.put(e.getKey(), mint.get(e.getKey()) + esc));
    }

    /**
     * @param esc:   number of pieces to divide among the parties.
     * @param votes: represents the votes given to each party involved.
     * @return standard quota
     */
    @Test(type = QUOTA)
    public static double quotaStandard(Map<String, Integer> votes, int esc) {
        int totalV = votes.values().stream().mapToInt(z -> z).sum();
        return totalV / ((double) esc);
    }

    /**
     * @param esc:   number of pieces to divide among the parties.
     * @param votes: represents the votes given to each party involved.
     * @return droop quota
     */
    @Test(type = QUOTA)
    public static double quotaDroop(Map<String, Integer> votes, int esc) {
        int totalV = votes.values().stream().mapToInt(z -> z).sum();
        return (totalV / ((double) esc + 1)) + 1;
    }

}
