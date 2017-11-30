
public class RandomWalk extends LocalSearch {

	public RandomWalk(Problem problem) {
		super(problem);
	}
	
	public RandomWalk(Problem problem, int numberEvalMax, double fitnessMax) {
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
		
		do {
			int randomIndex = (int)(Math.random()*s.bits.size());
			solutionBuffer.flip(randomIndex);
			
			numberEval++;
			problem.eval(solutionBuffer);
			double tempFitness = problem.eval;
			
			if(tempFitness > fitness)
				fitness = tempFitness;
			
			if(numberEval >= numberEvalMax || fitness >= fitnessMax)
				flag = false;
			
		} while (flag);

		bestFitness = fitness;
	}

}
