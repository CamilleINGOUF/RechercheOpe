
public class RandomSearch extends LocalSearch {
	
	public RandomSearch(Problem problem) {
		super(problem);
	}
	
	public RandomSearch(Problem problem, int numberEvalMax, double fitnessMax) {
		super(problem);
		this.numberEvalMax = numberEvalMax;
		this.fitnessMax = fitnessMax;
	}

	@Override
	public void run(Solution s) {
		Solution solutionBuffer = new Solution(s);
		
		boolean flag = true;
		problem.eval(solutionBuffer);
		double fitness = problem.eval;
		
		int numberEval = 0;
		
		do {
			solutionBuffer.randomSolution();
			
			problem.eval(solutionBuffer);
			double tempFitness = problem.eval;

			numberEval++;
			
			if(tempFitness > fitness)
				fitness = tempFitness;
				
			if(numberEval >= numberEvalMax || fitness >= fitnessMax)
				flag = false;
			
		} while (flag);
		
		bestFitness = fitness;
	}

}