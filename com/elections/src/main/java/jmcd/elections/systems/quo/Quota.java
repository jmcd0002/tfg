package jmcd.elections.systems.quo;

import java.util.Map;
import java.util.function.BiFunction;

@FunctionalInterface
public interface Quota<T> extends BiFunction<Map<T,Integer>, Integer, Double> {

}
