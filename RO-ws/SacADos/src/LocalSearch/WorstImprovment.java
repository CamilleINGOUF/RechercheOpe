package LocalSearch;
import Problem.Problem;
import Problem.Solution;

public class WorstImprovment extends LocalSearch {

	public WorstImprovment(Problem problem) {
		super(problem);
	}
	
	public WorstImprovment(Problem problem, int numberEvalMax, double fitnessMax) {
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
		double worstNeighbor = 0;
		int iBest = -1;
		
		do {
			iBest = -1;
			
			for(int i = 0;i < problem.size; i++)
			{
				solutionBuffer.flip(i);
				numberEval++;
				problem.eval(solutionBuffer);
				double neighborEval = problem.eval;
				
				if (fitness < neighborEval && neighborEval < worstNeighbor || i == 0) {
					iBest = i;
					worstNeighbor = neighborEval;
					break;
				}
				
				if(numberEval >= numberEvalMax || fitness >= fitnessMax) {
					fitness = worstNeighbor;
					flag = false;
					break;
				}
				
				solutionBuffer.flip(i);
			}
			
			if(iBest >= 0 && fitness < worstNeighbor )
			{
				solutionBuffer.flip(iBest);
				fitness = worstNeighbor;
			}
			
			else {
				flag = false;
			}
			
		} while (flag);
		
		bestFitness = fitness;
		bestSolution = new Solution(solutionBuffer);
	}

}
