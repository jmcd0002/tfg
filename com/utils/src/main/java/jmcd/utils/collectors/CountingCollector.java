package jmcd.utils.collectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class CountingCollector<S, T> implements Collector<S, Map<T, Integer>, Map<T, Integer>> {

    private final Function<S, T> fun;

    public CountingCollector(Function<S, T> function) {
        this.fun = function;
    }

    private static <R> Map<R, Integer> combine(Map<R, Integer> m1, Map<R, Integer> m2) {
        Map<R, Integer> m3 = new HashMap<>(m1);
        m2.forEach((key, value) -> updateMap(m3, key, value));
        return m3;
    }

    private static <U> void updateMap(Map<U, Integer> m3, U st, int value) {
        if (m3.containsKey(st)) {
            m3.put(st, m3.get(st) + value);
        } else {
            m3.put(st, value);
        }
    }

    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, Integer>, S> accumulator() {
        return (map,t)->updateMap(map,fun.apply(t),1);
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return CountingCollector::combine;
    }

    @Override
    public Function<Map<T, Integer>, Map<T, Integer>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

}
