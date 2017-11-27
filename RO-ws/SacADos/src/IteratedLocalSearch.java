
public class IteratedLocalSearch extends LocalSearch {

	private int perturbationN;
	
	public IteratedLocalSearch(Problem problem) {
		super(problem);
		perturbationN = 10;
	}
	
	public IteratedLocalSearch(Problem problem, int numberEvalMax, double fitnessMax) {
		super(problem);
		this.numberEvalMax = numberEvalMax;
		this.fitnessMax = fitnessMax;
		this.perturbationN = 10;
	}
	
	public void setPertubationN(int n) {
		this.perturbationN = n;
	}
	
	private Solution pertubation(Solution s) {
		
		Solution buffer = new Solution(s);
		float n = problem.size;
		for(int i = 0; i < n; i++)
		{
			double u = Math.random();
			
			if(u < ((float)perturbationN/n))
			{
				buffer.flip(i);
			}
		}
		
		return buffer;
	}

	@Override
	public void run(Solution s) {
		Solution solutionBuffer = new Solution(s);
		
		FirstImprovment fi = new FirstImprovment(problem, numberEvalMax, fitnessMax);
		fi.run(solutionBuffer);
		
		
		double fitness = fi.bestFitness;
		
		boolean flag = true;
		
		double nextFitness = 0;
		
		do {
			
			Solution nextS = pertubation(solutionBuffer);
			fi.resetNumberEval();
			fi.run(nextS);
			
			//System.exit(0);
			
			nextS = new Solution(fi.bestSolution);
			
			nextFitness = fi.bestFitness;
			numberEval += fi.getNumberEval();
			
			if(fitness < nextFitness)
			{
				fitness = nextFitness;
				solutionBuffer = new Solution(nextS);
			}
			
			if(numberEval >= numberEvalMax || fitness >= fitnessMax)
			{
				fitness = nextFitness;
				flag = false;
				bestSolution = new Solution(solutionBuffer);
			}
			//System.out.println(fitness+" "+solutionBuffer);
			
		} while (flag);
		
		bestFitness = fitness;
	}

}
