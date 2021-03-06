package AlgoEvolutionnaire;

import java.util.Comparator;

import Problem.Solution;

public class ComparatorSolution implements Comparator<Solution> {

	@Override
	public int compare(Solution o1, Solution o2) {
		
		return (int) (o1.getFitness() - o2.getFitness());
	}

}
