package jmcd.elections.systems.div;

import jmcd.elections.ElectionMethodsUtils;
import jmcd.elections.simulations.PartyListVotingSimulation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This test is for divisor methods
 */
public class DivisorsTest {

    private static final Random random = new Random();
    private static final int NUMBER_OF_REPETITIONS = 10;
    private static final Logger LOG = Logger.getLogger("TEST DIVISORS");
    private List<Divisor> methods;


    /**
     * List all the methods to be tested
     */
    @Before
    public void init() {
        methods = ElectionMethodsUtils.getDivisorMethods(List.of(Divisors.class));

    }

    /**
     * Procedure followed:
     * 1. Random number of seats from 10 to 100
     * 2. Random number of parties from 2 to numberSeats/5
     * 3. Random number of votes to each party up to numberOfMaxVotes
     * 4. Apportionment by a method
     * 5. Assert apportionment result
     * 6. All repeated numberOfMethods, for each method
     */
    @Test
    public void methodDivisorAllTest() {
        for (Divisor divisorUsed : methods) {
            LOG.log(Level.INFO, "Divisor method := 1->{0}", divisorUsed.apply(1));
            for (int j = 0; j < NUMBER_OF_REPETITIONS; j++) {
                oneRandomTest(divisorUsed);
            }
        }
    }

    private void oneRandomTest(Divisor divisorUsed) {
        int numOfSeats = random.nextInt(90) + 10;
        PartyListVotingSimulation votSim = new PartyListVotingSimulation(2, numOfSeats / 5);

        Map<String, Integer> votes = votSim.getVotes();
        Map<String, Integer> solution = Divisors.methodDivisor(votes, numOfSeats, divisorUsed);

        LOG.log(Level.INFO, "Number of seats := {0}", numOfSeats);
        LOG.log(Level.INFO, "Number of parties := {0}", votSim.getNumOfParties());
        LOG.log(Level.INFO, "Votes := {0}", votes);
        LOG.log(Level.INFO, "Apportionment := {0}", solution);
        checkAssertions(divisorUsed, votes, solution);
    }

    private void checkAssertions(Divisor divisorUsed, Map<String, Integer> votes, Map<String, Integer> solution) {
        double modifiedDivisor = (nextDivisor(votes, solution, divisorUsed) + previousDivisor(votes, solution, divisorUsed)) / 2;
        LOG.log(Level.INFO, "Modified divisor := {0}", modifiedDivisor);
        for (Map.Entry<String, Integer> party : solution.entrySet()) {
            double partition = votes.get(party.getKey()) / modifiedDivisor;
            int i = 0;
            while (divisorUsed.apply(i) < partition && (divisorUsed.apply(i) - partition <= -0.0001)) {
                i++;
            }
            Assert.assertEquals(i, (int) party.getValue());
        }
    }

    private double nextDivisor(Map<String, Integer> votes, Map<String, Integer> solution, Divisor divisorUsed) {
        double maximum = 0;
        for (Map.Entry<String, Integer> party : votes.entrySet()) {
            double step = party.getValue() / divisorUsed.apply(solution.getOrDefault(party.getKey(), 0));

            if (maximum < step) {
                maximum = step;
            }
        }
        return maximum;
    }

    private double previousDivisor(Map<String, Integer> votes, Map<String, Integer> solution, Divisor divisorUsed) {
        double minimum = 0;
        for (Map.Entry<String, Integer> party : votes.entrySet()) {
            if (solution.containsKey(party.getKey())) {
                double step = party.getValue() / divisorUsed.apply(solution.get(party.getKey()) - 1);

                if (minimum == 0 || minimum > step) {
                    minimum = step;
                }
            }
        }
        return minimum;
    }

    @Test
    public void tieTesting() {
        for (Divisor divisorUsed : methods) {
            LOG.log(Level.INFO, "Divisor method := 1->{0}", divisorUsed.apply(1));
            Map<String, Integer> votes = new HashMap<>();
            votes.put("P0", 10000);
            votes.put("P1", 10000);

            Map<String, Integer> solution = Divisors.methodDivisor(votes, 3, divisorUsed);
            Assert.assertTrue(solution.get("P0") <= 2 && solution.get("P0") >= 1);
            Assert.assertTrue(solution.get("P1") <= 2 && solution.get("P1") >= 1);
            Assert.assertEquals(3, solution.values().stream().mapToInt(m -> m).sum());
        }
    }

}
