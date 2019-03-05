package jmcd.elections.systems.quo;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Interfaz que abstrae la idea de los métodos de cuotas
 * @param <T>
 */
@FunctionalInterface
public interface Quota<T> extends BiFunction<Map<T,Integer>, Integer, Double> {

}
