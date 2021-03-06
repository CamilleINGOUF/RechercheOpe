package LocalSearch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Problem.Problem;
import Problem.Solution;

public class RecuitSimule extends LocalSearch {

	private float temperature;
	private float alpha;
	private int temperatureUpdate;

	public RecuitSimule(Problem problem) {
		super(problem);
		temperature = 100;
		alpha = .99f;
		temperatureUpdate = 1000;
	}

	public RecuitSimule(Problem problem, int numberEvalMax, double fitnessMax) {
		super(problem);
		this.numberEvalMax = numberEvalMax;
		this.fitnessMax = fitnessMax;
		temperature = 100;
		alpha = .99f;
		temperatureUpdate = 1000;
	}

	@Override
	public void run(Solution s, boolean printRun) {
		Solution solutionBuffer = new Solution(s);
		
		problem.eval(s);
		double fitness = problem.eval; 

		boolean flag = true;
		int iterations = 0;
		
		int consecutiveFail = 0;
		
		PrintWriter writer = null;
		if(printRun) {
			try {
				writer = new PrintWriter("sa_iterations.csv", "UTF-8");
				writer.println("iteration nbeval fitness fitmax");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		do {

			int randomIndex = (int)(Math.random()*s.bits.size());
			solutionBuffer.flip(randomIndex);
			
			numberEval++;
			problem.eval(solutionBuffer);
			double neighborEval = problem.eval;
			double delta = neighborEval - fitness;

			if(delta > 0) {
				fitness = neighborEval;
				consecutiveFail = 0;
			} else {
				double u = Math.random();
				double exp = Math.exp(delta / temperature);
				//System.out.println(iterations+" "+temperature+" "+exp+" "+delta);
				if(u < exp) {
					fitness = neighborEval;
					consecutiveFail = 0;
				} else {
					solutionBuffer.flip(randomIndex);
//					consecutiveFail++;
				}
			}

			if(iterations % temperatureUpdate == 0) {
				//System.out.println(iterations);
				temperature *= alpha;
			}

			if(numberEval >= numberEvalMax || fitness >= fitnessMax || consecutiveFail >= 3)
				flag = false;
			
			if(printRun) {
				writer.print(iterations+" "+numberEval+" "+fitness+" "+fitnessMax+"\n");
			}
			iterations ++;
		} while (flag);
		
		if(printRun) {
			writer.close();
		}

		bestFitness = fitness;
		bestSolution = new Solution(solutionBuffer);
	}

}
