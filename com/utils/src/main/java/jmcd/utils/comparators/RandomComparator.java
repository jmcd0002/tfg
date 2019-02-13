package jmcd.utils.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Comparator that keeps on creating a random order of elements of type.
 * Every instance of it keeps a different order randomly lazily generated,
 * but from the same instance, the comparison of the same pair of elements
 * returns the same order.
 *
 * @param <T>
 */
public class RandomComparator<T> implements Comparator<T> {

    private static final Random random = new Random();
    private final List<T> compared;

    public RandomComparator() {
        compared = new ArrayList<>();
    }

    public void destroyOrder() {
        compared.clear();
    }

    @Override
    public int compare(T o1, T o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        if (compared.isEmpty()) {
            int ran = random.nextInt(2);
            if (ran == 0) {
                compared.add(o1);
                compared.add(o2);
            } else {
                compared.add(o2);
                compared.add(o1);
            }
        } else {
            if (!compared.contains(o1)) {
                compared.add(random.nextInt(compared.size()), o1);
            }
            if (!compared.contains((o2))) {
                compared.add(random.nextInt(compared.size()), o2);
            }
        }
        return compared.indexOf(o1) - compared.indexOf(o2);
    }
}
