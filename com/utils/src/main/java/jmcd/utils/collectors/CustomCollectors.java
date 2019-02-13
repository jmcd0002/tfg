package jmcd.utils.collectors;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

public final class CustomCollectors {

    private CustomCollectors() {
    }

    /**
     * Collects to a Map following the functions keyFunc and valueFunc. Make sure
     * that keyFunc returns values not in the map already.
     *
     * Not recommended for parallel streams. It is safe, but costly.
     *
     * @param keyFunc
     * @param valueFunc
     * @param <S>
     * @param <R>
     * @param <T>
     * @return
     */
    public static <S, R, T> Collector<S, Map<R, T>, Map<R, T>> toMap(BiFunction<S, Map<R, T>, R> keyFunc, BiFunction<S, Map<R, T>, T> valueFunc) {

        return new MapKeyValueCheckingCollector<>(keyFunc, valueFunc);
    }

    public static <S, R> Collector<S, Map<R, Integer>, Map<R, Integer>> toCountingMap(Function<S, R> function) {
        return new CountingCollector<>(function);
    }

    public static <R, S> Collector<Entry<R, S>, Map<R, Integer>, Map<R, Integer>> toKeyCountingMap() {
        return toCountingMap(Entry::getKey);
    }

    public static <R, S> Collector<Entry<R, S>, Map<S, Integer>, Map<S, Integer>> toValueCountingMap() {
        return toCountingMap(Entry::getValue);
    }
}
