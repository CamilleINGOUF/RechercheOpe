package AlgoEvolutionnaire;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Problem.KnapSack;
import Problem.Problem;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Problem problem = new KnapSack("ks_1000.dat");
		EA ea = new EA(problem);
		
		PrintWriter writer = new PrintWriter("ea.csv", "UTF-8");
		writer.println("nbeval fitness fitmax");
		
		for(int i = 0; i < 10; i++) {
			ea.run();
			writer.println("100000 "+ea.getBestFitness()+" 50000");
			ea = new EA(problem);
		}
		
		writer.close();
	}

}
