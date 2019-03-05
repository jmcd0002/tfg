package jmcd.elections.systems.div;

import java.util.function.Function;

/**
 * Interfaz que abstrae la idea del método de divisores. Pensado para usar lambdas.
 */
@FunctionalInterface
public interface Divisor extends Function<Integer, Double>{}
