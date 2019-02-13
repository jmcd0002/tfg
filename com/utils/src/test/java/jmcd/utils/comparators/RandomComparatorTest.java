package jmcd.utils.comparators;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomComparatorTest {

    private final static Logger LOG = Logger.getLogger("Random comparator TEST");
    private final static RandomComparator<String> randomComparator = new RandomComparator<>();

    @Test
    public void testRepeatRandom() {
        int x = randomComparator.compare("A", "B");
        for (int i = 0; i < 50; i++) {
            Assert.assertTrue(x * randomComparator.compare("A", "B") > 0);
        }
    }

    @Test
    public void checkCompareTransitive() {
        for (int i = 0; i < 50; i++) {
            int x1 = randomComparator.compare("A", "B");
            int x2 = randomComparator.compare("C", "B");
            int x3 = randomComparator.compare("C", "D");
            if (x1 * x2 < 0) {
                Assert.assertTrue(randomComparator.compare("A", "C") * x1 > 0);
            } else if (x2 * x3 < 0) {
                Assert.assertTrue(randomComparator.compare("B", "D") * x2 < 0);
            }
            randomComparator.destroyOrder();
        }
    }

    @Test
    public void compareEquals(){
        Assert.assertEquals(0, randomComparator.compare("A", "A"));
    }

    @Test
    public void eraseOrder() {
        Map<String, Integer> map = new HashMap<>();
        map.put("neg", 0);
        map.put("pos", 0);
        for (int i = 0; i < 100000; i++) {
            int x = randomComparator.compare("A", "B");
            randomComparator.destroyOrder();
            if (x > 0) {
                map.put("pos", map.get("pos") + 1);
            } else {
                map.put("neg", map.get("neg") + 1);
            }
        }
        Assert.assertTrue(map.get("pos") <= 60000);
        Assert.assertTrue(map.get("pos") >= 40000);
        Assert.assertTrue(map.get("neg") <= 60000);
        Assert.assertTrue(map.get("neg") >= 40000);
        LOG.log(Level.INFO, map.toString());
    }
}
