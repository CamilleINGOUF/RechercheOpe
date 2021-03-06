package LocalSearch;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Problem.KnapSack;
import Problem.Problem;
import Problem.Solution;

public class Main 
{	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		int[] nbEvals = {100000/*,200000,400000*/};
		int[] maxFitnesses = {/*30000,35000,40000,45000,*/50000};
		int nbExec = 10;
	
		Problem problem = new KnapSack("ks_1000.dat");
		Solution solution = new Solution(problem.size);
		solution.randomSolution();
		
		LocalSearch ls;
		
		AlgoLocalSearch algo = AlgoLocalSearch.RecuitSimule;
		
		String fileName;
		
		switch (algo) {
		case RandomSearch:
			ls = new RandomSearch(problem);
			fileName = new String("rs.csv");
			break;
		case RandomWalk:
			ls = new RandomWalk(problem);
			fileName = new String("rw.csv");
			break;
		case BestImprovment:
			ls = new BestImprovment(problem);
			fileName = new String("bi.csv");
			break;
		case FirstImprovment:
			ls = new FirstImprovment(problem);
			fileName = new String("fi.csv");
			break;
		case WorstImprovment:
			ls = new WorstImprovment(problem);
			fileName = new String("wi.csv");
			break;
		case RecuitSimule:
			ls = new RecuitSimule(problem);
			fileName = new String("sa.csv");
			break;
		case IteratedLocalSearch:
			ls = new IteratedLocalSearch(problem);
			fileName = new String("ils.csv");
			break;
		default:
			ls = null;
			fileName = new String("error.txt");
			break;
		}
		
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println("nbeval fitness fitmax");
		
		for (int k = 0; k < nbEvals.length; k++) {
			ls.setNumberEvalMax(nbEvals[k]);

			for(int j = 0; j < maxFitnesses.length; j++) {
				for (int i = 0; i < nbExec; i++) {
					solution.randomSolution();
					ls.resetNumberEval();
					ls.setFitnessMax(maxFitnesses[j]);
					ls.run(solution, nbExec == 1);
					writer.println(nbEvals[k] + " " + ls.bestFitness + " " + ls.fitnessMax);
					System.out.println(nbEvals[k] + " " + ls.bestFitness);
				}
			}
		}
		
		writer.close();
		
		System.out.println("Done.");
	}
}
