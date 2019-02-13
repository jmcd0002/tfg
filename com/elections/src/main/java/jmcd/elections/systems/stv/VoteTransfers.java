package jmcd.elections.systems.stv;

import jmcd.elections.systems.Test;
import jmcd.elections.systems.quo.Quota;

import java.util.*;
import java.util.stream.Collectors;

public final class VoteTransfers {

    private static final Random random = new Random();

    private VoteTransfers() {
    }

    @Test(type = Test.Type.STV)
    public static Map<String, Double> trasnferePure(String candidate, Map<List<String>, Integer> votes, Quota<List<String>> quota, int seats, List<String> validCandidates) {

        Map<String, Double> solution = initMap(validCandidates);

        List<String> validPlusCurrent = new ArrayList<>(validCandidates);
        validPlusCurrent.add(candidate);

        double quotaDouble=quota.apply(votes,seats);

        int total = votes.entrySet().stream()
                .filter(e -> e.getKey().stream()
                        .filter(validPlusCurrent::contains)
                        .findFirst()
                        .orElse(candidate + "NOT").equals(candidate))
                .mapToInt(Map.Entry::getValue)
                .sum();

        votes.entrySet().stream()
                .filter(e -> e.getKey().stream()
                        .filter(validPlusCurrent::contains)
                        .findFirst()
                        .orElse(candidate + "NOT").equals(candidate))
                .forEach(e -> e.getKey().stream()
                        .filter(validCandidates::contains)
                        .findFirst().ifPresent(st -> solution.put(st, solution.get(st) + percentage(e.getValue(), (int)quotaDouble, total)))
                );

        return solution.entrySet().stream()
                .filter(e->e.getValue()!=0)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    @Test(type = Test.Type.STV)
    public static Map<String, Double> transfereRandom(String candidate, Map<List<String>, Integer> votes, Quota<List<String>> quota, int seats, List<String> validCandidates) {

        Map<String, Double> auxiliar = initMap(validCandidates);

        List<String> validPlusCurrent = new ArrayList<>(validCandidates);
        validPlusCurrent.add(candidate);

        int total = votes.entrySet().stream()
                .filter(e -> e.getKey().stream()
                        .filter(validPlusCurrent::contains)
                        .findFirst()
                        .orElse(candidate + "NOT").equals(candidate))
                .mapToInt(Map.Entry::getValue)
                .sum();

        int excess = (int)(total - quota.apply(votes,seats));

        votes.entrySet().stream()
                .filter(e -> e.getKey().stream()
                        .filter(validPlusCurrent::contains)
                        .findFirst()
                        .orElse(candidate + "NOT").equals(candidate))
                .forEach(e -> e.getKey().stream()
                        .filter(validCandidates::contains)
                        .findFirst().ifPresent(st -> auxiliar.put(st, auxiliar.get(st) + e.getValue()))
                );

        Map<String, Double> solution = new HashMap<>();
        for (int i = 0; i < excess; i++) {
            int count = 0;
            int rand = random.nextInt(excess);
            for (Map.Entry<String, Double> e : auxiliar.entrySet()) {
                count+=e.getValue();
                if (count>=rand){
                    auxiliar.put(e.getKey(),auxiliar.get(e.getKey())-1);
                    solution.put(e.getKey(),solution.getOrDefault(e.getKey(),0.0)+1);
                    break;
                }
            }
        }

        return solution;
    }

    private static double percentage(int value, int quota, double total) {
        return (value / total) * (total - quota);
    }

    private static Map<String, Double> initMap(List<String> validCandidates) {
        Map<String, Double> sol = new HashMap<>();
        for (String st : validCandidates) {
            sol.put(st, 0.0);
        }
        return sol;
    }
}
