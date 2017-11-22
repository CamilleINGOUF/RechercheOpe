import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main 
{	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		int[] nbEvals = {1000,5000,10000,50000,100000};
		int nbExec = 30;
	
		Problem problem = new KnapSack("ks_1000.dat");
		Solution solution = new Solution(problem.size);
		solution.randomSolution();
		
		LocalSearch ls;
		
		Algo algo = Algo.BestImprovment;
		
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
		default:
			ls = null;
			fileName = new String("error.txt");
			break;
		}
		
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println("nbeval fitness fitmax");
		
		for (int k = 0; k < nbEvals.length; k++) {
			ls.setOptions(nbEvals[k], 30000);
			
			for (int i = 0; i < nbExec; i++) {
				ls.run(solution);
				
				writer.println(nbEvals[k] + " " + ls.bestFitness + " " + ls.fitnessMax);
				System.out.println(nbEvals[k] + " " + ls.bestFitness);
			}
		}
		
		writer.close();
		
		System.out.println("Done.");
	}
}
