package jmcd.utils.collectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collector;

public class MapKeyValueCheckingCollector<K,S,T> implements Collector<K, Map<S,T>,Map<S,T>> {

    private final BiFunction<K,Map<S,T>,S> keyFunc;
    private final BiFunction<K,Map<S,T>,T> valueFunc;

    private final Map<Map.Entry<S,T>,K> remember=new ConcurrentHashMap<>();

    public MapKeyValueCheckingCollector(BiFunction<K,Map<S,T>,S> keyFunc,
                                        BiFunction<K,Map<S,T>,T> valueFunc){
        this.keyFunc=keyFunc;
        this.valueFunc=valueFunc;
    }

    @Override
    public Supplier<Map<S, T>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<S, T>, K> accumulator() {
        return this::accumulate;
    }

    private void accumulate(Map<S, T> map, K s) {
        S key=keyFunc.apply(s, map);
        T value=valueFunc.apply(s,map);
        map.put(key, value);
        remember.put(Map.entry(key,value),s);
    }

    @Override
    public BinaryOperator<Map<S, T>> combiner() {
        return this::mergeMaps;
    }

    private Map<S, T> mergeMaps(Map<S, T> map1, Map<S, T> map2) {
        map2.forEach((key,value)->safeInsert(map1, key, value));
        return map1;
    }

    private void safeInsert(Map<S, T> map1, S key, T value) {
        if (map1.containsKey(key)){
            K k=remember.get(Map.entry(key,value));
            accumulate(map1,k);
        }
        else{
            map1.put(key,value);
        }
    }

    @Override
    public Function<Map<S, T>, Map<S, T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Collector.Characteristics.IDENTITY_FINISH,
                Collector.Characteristics.UNORDERED);
    }
}
