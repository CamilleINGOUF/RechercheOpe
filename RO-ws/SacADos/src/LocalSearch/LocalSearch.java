package LocalSearch;
import Problem.Problem;
import Problem.Solution;

public abstract class LocalSearch {
	
	public Problem problem;
	
	public int numberEval;
	
	public int numberEvalMax;
	public double fitnessMax;
	
	public double bestFitness; 
	
	public Solution bestSolution;
	
	public LocalSearch(Problem problem)
	{
		this.problem = problem;
		
		this.numberEvalMax = 1000;
		this.numberEval = 0;
		this.fitnessMax = 50000;
	}
	
	public void setNumberEvalMax(int numberEvalMax)
	{
		this.numberEvalMax = numberEvalMax;
	}
	public void setFitnessMax(double fitnessMax)
	{
		this.fitnessMax = fitnessMax;
	}
	
	public int getNumberEval()
	{
		return numberEval;
	}
	
	public void resetNumberEval()
	{
		numberEval = 0;
	}
	
	public abstract void run(Solution s, boolean printRun);
}
