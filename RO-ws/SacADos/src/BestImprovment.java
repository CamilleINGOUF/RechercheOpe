
public class BestImprovment extends LocalSearch {

	public BestImprovment(Problem problem) {
		super(problem);
	}
	
	public BestImprovment(Problem problem, int numberEvalMax, double fitnessMax) {
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
				numberEval += 1;
				
				if (neighborEval > bestNeighbor) {
					iBest = i;
					bestNeighbor = neighborEval;
				}
				
				if(numberEval >= numberEvalMax || fitness >= fitnessMax) {
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
		//TODO buggé
		bestFitness = fitness;
	}

}
