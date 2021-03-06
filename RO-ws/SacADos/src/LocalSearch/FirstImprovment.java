package LocalSearch;
import Problem.Problem;
import Problem.Solution;

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
	public void run(Solution s, boolean printRun) {
		Solution solutionBuffer = new Solution(s);
		
		boolean flag = true;
		problem.eval(solutionBuffer);
		double fitness = problem.eval;
		double bestNeighbor = 0;
		int iBest = -1;
		
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
				
				solutionBuffer.flip(i);
				
				if(numberEval >= numberEvalMax || fitness >= fitnessMax) {
					fitness = bestNeighbor;
					flag = false;
					break;
				}
			}
			
			if(iBest >= 0 && fitness < bestNeighbor )
			{
				fitness = bestNeighbor;
			}
			
			else {
				flag = false;
			}
			
		} while (flag);
		
		bestFitness = fitness;
		bestSolution = new Solution(solutionBuffer);
	}

}
