package jmcd.elections.systems.quo;

import jmcd.elections.simulations.PartyListPair;
import jmcd.elections.systems.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static jmcd.elections.systems.Test.Type.QUOTA;
import static jmcd.elections.systems.Test.Type.REMAINDER;

/**
 * Esta clase es un librería de métodos de reparto por cuotas. Contiene una lista de métodos de reparto de restos y una función
 * que implementa el método de reparto por cuotas. También hay una lista de funciones que computan cuotas diversas a partir
 * del número de asientos y el número de votos.
 *
 * @author daconcep
 * @author jmcd
 */
public final class Quotas {

    /**
     * Constructor privado para impedir la instanciación de la clase
     */
    private Quotas() {
    }

    /**
     * Método de reparto por cuotas
     * @param votes: representa los votos dados a cada partido.
     * @param esc:  número de escaños a dividir entre los partidos.
     * @param mm:   método que distribuye los votos de los restos.
     * @param quot:  cuota usada en el método
     * @return {@link Map} con la distribución de escaños
     */
    public static <T> Map<T, Integer> methodQuota(Map<T, Integer> votes, int esc, Quota<T> quot, Remainder<T> mm) {
        //Mapa auxiliar
        Map<T, Double> aux = new HashMap<>();
        //Mapa resultado
        Map<T, Integer> res = new HashMap<>();

        //cuota
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
     * Método de reparto de restos del resto mayor
     * @param esc:  número de escaños a repartir entre los partidos.
     * @param dob:  restos del método de cuotas.
     * @param mint: reparto por la cuota.
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
     * Método de reparto del resto mayor relativo
     * @param esc:  número de escaños a dividir entre los partidos.
     * @param dob:  restos de los partidos involucrados
     * @param mint: resultado del reparto por cuota
     */
    @Test(type = REMAINDER)
    public static void remaindersLargestRemainderRelative(int esc, Map<String, Double> dob, Map<String, Integer> mint) {
        dob = dob.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / mint.get(e.getKey())));

        remaindersLargestRemainder(esc, dob, mint);
    }


    /**
     * Método de restos el ganador se lleva los restos
     * @param esc:  número de escaños a repartir.
     * @param dob:  restos de los partidos
     * @param mint: resultado del reparto por cuota
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
     * Cuota estándar
     * @param esc:   número de escaños a dividir.
     * @param votes: representa los votos de los partidos.
     * @return cuota estándar
     */
    @Test(type = QUOTA)
    public static double quotaStandard(Map<String, Integer> votes, int esc) {
        int totalV = votes.values().stream().mapToInt(z -> z).sum();
        return totalV / ((double) esc);
    }

    /**
     * Cuota droop
     * @param esc:   número de escaños entre los partidos.
     * @param votes: representa los votos de los partidos.
     * @return cuota droop
     */
    @Test(type = QUOTA)
    public static double quotaDroop(Map<String, Integer> votes, int esc) {
        int totalV = votes.values().stream().mapToInt(z -> z).sum();
        return (totalV / ((double) esc + 1)) + 1;
    }

}
