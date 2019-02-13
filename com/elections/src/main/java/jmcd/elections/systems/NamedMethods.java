package jmcd.elections.systems;

import jmcd.elections.systems.div.Divisors;
import jmcd.elections.systems.quo.Quotas;

import java.util.Map;

public final class NamedMethods {

    private NamedMethods(){
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return Webster solution
     */
    public static Map<String,Integer> methodWebster(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsSaint);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return Jefferson solution
     */
    public static Map<String,Integer> methodJefferson(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsDHont);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return Danish solution
     */
    public static Map<String,Integer> methodDanes(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsDin);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return Hamilton solution
     */
    public static Map<String,Integer> methodHamilton(Map<String,Integer> votes,int esc) {

        return Quotas.methodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainder);

    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return Relative hamilton solution
     */
    public static Map<String,Integer> methodHamiltonRel(Map<String,Integer> votes, int esc) {

        return Quotas.methodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainderRelative);

    }
}
