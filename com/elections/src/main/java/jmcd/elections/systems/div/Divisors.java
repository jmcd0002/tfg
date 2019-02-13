package jmcd.elections.systems.div;

import jmcd.elections.simulations.PartyListPair;
import jmcd.elections.systems.Test;
import jmcd.utils.collectors.CustomCollectors;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class is a library of divisor methods of apportionment. There is a list of divisor functions, which start their names with 'divisors'
 * and a function that implements the divisor apportionment.
 * 
 * @author daconcep
 *
 */
public final class Divisors {

	private Divisors(){
	}
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @param div: method that represent the list of divisors; i.e. a strictly growing function from int to double.
	 * @return a partition of esc among the parties involved with respect to the votes.
	 */
	public static Map<String,Integer> methodDivisor(Map<String,Integer> votes, int esc, Divisor div){
		return votes.entrySet().stream()
				.flatMap(p->IntStream.range(0,esc)
						.mapToObj(n->(new PartyListPair<>(p.getKey(),p.getValue()/div.apply(n)))))
				.sorted(PartyListPair::compareTo)
				.limit(esc)
				.collect(CustomCollectors.toKeyCountingMap());
	}
	
	/**
	 * @param n step
	 * @return Sqrt(n*(n+1))
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsHill(int n){
		if (n==0){
			return 0.001;
		}
		return Math.sqrt(n*(n+1.0));
	}
	
	/**
	 * @param n step
	 * @return 2/(1/n+1/(n+1))
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDean(int n){
		if (n==0){
			return 0.001;
		}
		return n*(n+1)/(n+0.5);
	}
	
	/**
	 * @param n step
	 * @return n+1/2
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsSaint(int n){
		return n+1.0/2;
	}
	
	/**
	 * @param n step
	 * @return n+1/2
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsSaintMod(int n){
		if (n==0){
			return 0.7;
		}
		return n+1.0/2;
	}
	
	/**
	 * @param n step
	 * @return n+2
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsImperialii(int n){
		return 2.0+n;
	}
	
	/**
	 * @param n step
	 * @return 2^n
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsMacau(int n){
		return Math.pow(2, n+1.0);
	}
	
	/**
	 * @param n step
	 * @return n^0.9
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsEstonia(int n){
		if (n==0){
			return 1;
		}
		return Math.pow(n+1.0, 0.9);
	}
	
	/**
	 * @param n step
	 * @return n+1
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDHont(int n){
		return n+1.0;
	}
	
	/**
	 * @param n step
	 * @return n+1/3
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDin(int n){
		return n+1.0/3;
	}

}
