package jmcd.elections.systems.div;

import jmcd.elections.simulations.PartyListPair;
import jmcd.elections.systems.Test;
import jmcd.utils.collectors.CustomCollectors;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Esta clase es una librería de métodos de reparto de divisores. Hay una lista de funciones de divisores.
 * También una función que implementa el método de reparto de divisores.
 * 
 * @author daconcep
 * @author jmcd
 *
 */
public final class Divisors {

	/**
	 * Constructor privado para impedir su instanciación
	 */
	private Divisors(){
	}
	
	/**
	 * @param votes: representa los votos dados a cada partido.
	 * @param esc: númro de escaños a dividir entre los partidos
	 * @param div: método que representa la lsita de divisores; matemáticamente, una función estrictamente creciente
	 *          de enteros a reales.
	 * @return una partición de esc entre los partidos con respecto a los votos y el método de divisor.
	 */
	public static Map<String,Integer> methodDivisor(Map<String,Integer> votes, int esc, Divisor div){
		return votes.entrySet().stream()
				//convertimos cada Map.Entry en un stream con la puntuación dada por el sistema del divisore
				.flatMap(p-> getPartyListPairStream(esc, div, p))
				//Ordenamos por puntuación los Map.Entry
				.sorted(PartyListPair::compareTo)
				//Nos quedamos con los ${esc} primeros partidos
				.limit(esc)
				//Devolvemos un Map con el conteo de claves
				.collect(CustomCollectors.toKeyCountingMap());
	}

	/**
	 * Constructor de un stream con las puntuaciones de los partidos según el método
	 * @param esc: númro de escaños a dividir entre los partidos
	 * @param div: método que representa la lsita de divisores; matemáticamente, una función estrictamente creciente
	 * @param p: entry <Partido,Votos>
	 * @return stream con las puntuaciones para p
	 */
	private static Stream<PartyListPair<Double>> getPartyListPairStream(int esc, Divisor div, Map.Entry<String, Integer> p) {
		return IntStream.range(0,esc)
				.mapToObj(n->(new PartyListPair<>(p.getKey(),p.getValue()/div.apply(n))));
	}

	/**
	 * @param n paso
	 * @return Sqrt(n*(n+1)); si n=0, devolvemos 0.001 para no tener problemas de ceros
	 */
	@Test(type= Test.Type.DIVISOR,toBeTested = false)
	public static double divisorsHill(int n){
		if (n==0){
			return 0.001;
		}
		return Math.sqrt(n*(n+1.0));
	}
	
	/**
	 * @param n paso
	 * @return 2/(1/n+1/(n+1)); si n=0, devolvemos 0.001 para no tener problemas de ceros
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDean(int n){
		if (n==0){
			return 0.001;
		}
		return n*(n+1)/(n+0.5);
	}
	
	/**
	 * @param n paso
	 * @return n+1/2
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsSaint(int n){
		return n+1.0/2;
	}
	
	/**
	 * @param n paso
	 * @return n+1/2, salvo 0->0.7
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsSaintMod(int n){
		if (n==0){
			return 0.7;
		}
		return n+1.0/2;
	}
	
	/**
	 * @param n paso
	 * @return n+2
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsImperialii(int n){
		return 2.0+n;
	}
	
	/**
	 * @param n paso
	 * @return 2^(n+1)
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsMacau(int n){
		return Math.pow(2, n+1.0);
	}
	
	/**
	 * @param n paso
	 * @return (n+1)^0.9
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsEstonia(int n){
		if (n==0){
			return 1;
		}
		return Math.pow(n+1.0, 0.9);
	}
	
	/**
	 * @param n paso
	 * @return n+1
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDHont(int n){
		return n+1.0;
	}
	
	/**
	 * @param n paso
	 * @return n+1/3
	 */
	@Test(type= Test.Type.DIVISOR)
	public static double divisorsDin(int n){
		return n+1.0/3;
	}

}
