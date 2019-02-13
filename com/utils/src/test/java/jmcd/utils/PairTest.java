package jmcd.utils;

import jmcd.utils.comparators.RandomComparator;
import org.junit.Assert;
import org.junit.Test;

public class PairTest {

    private static final Pair<String,String> pair= new Pair<>("Key","Value",new RandomComparator<>());

    @Test
    public void pairTest(){
        Assert.assertEquals("Key",pair.getKey());
        Assert.assertEquals("Value",pair.getValue());
        pair.setValue("Value2");
        Assert.assertEquals("Value2",pair.getValue());
    }

    @Test
    public void comparePairTest(){
        Pair<String,String> pair2=new Pair<>("Key","Valua",pair.getRandomComparator());
        Pair<String,String> pair3=new Pair<>("Key","Valuz",pair.getRandomComparator());
        Assert.assertTrue(pair.compareTo(pair2)<0);
        Assert.assertTrue(pair.compareTo(pair3)>0);
        Pair<String,String> pair4=new Pair<>("Sus","Value",pair.getRandomComparator());
        int x=pair.compareTo(pair4);
        Assert.assertTrue(x*pair.compareTo(pair4)>0);
    }

    @Test
    public void toStringPairTest(){
        Assert.assertEquals("Key -> Value",pair.toString());
    }

    @Test
    public void equalsPairTest(){
        Assert.assertEquals(pair, new Pair<>(pair.getKey(),pair.getValue(),pair.getRandomComparator()));
        Assert.assertNotEquals(pair, new Pair<>("A","B",pair.getRandomComparator()));
        Assert.assertNotEquals(pair,"Testing");
        Assert.assertNotEquals(pair,new Pair<>(pair.getKey(),"B",pair.getRandomComparator()));
        Assert.assertNotEquals(pair,new Pair<>("A",pair.getValue(),pair.getRandomComparator()));
    }

    @Test
    public void hashCodePairTest(){
        Pair<String,String>  pair2=new Pair<>(pair.getKey(),pair.getValue(),pair.getRandomComparator());
        Assert.assertEquals(pair2.hashCode(),pair.hashCode());
    }
}
