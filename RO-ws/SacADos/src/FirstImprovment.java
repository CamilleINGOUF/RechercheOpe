public class FirstImprovment extends LocalSearch {

	public FirstImprovment(Problem problem) {
		super(problem);
	}
	
	public FirstImprovment(Problem problem, int numberEvalMax, double fitnessMax) {
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
		double bestNeighbor = 0;
		int iBest = -1;
		
		int numberEval = 0;
		
		do {
			iBest = -1;
			
			for(int i = 0;i < problem.size; i++)
			{
				solutionBuffer.flip(i);
				numberEval++;
				problem.eval(solutionBuffer);
				double neighborEval = problem.eval;
				
				if (neighborEval > bestNeighbor) {
					iBest = i;
					bestNeighbor = neighborEval;
					break;
				}
				
				if(numberEval >= numberEvalMax || fitness >= fitnessMax) {
					fitness = bestNeighbor;
					flag = false;
					break;
				}
				
				solutionBuffer.flip(i);
			}
			
			if(iBest >= 0 && fitness < bestNeighbor )
			{
				solutionBuffer.flip(iBest);
				fitness = bestNeighbor;
			}
			
			else
				flag = false;
			
		} while (flag);
		
		bestFitness = fitness;
	}

}
