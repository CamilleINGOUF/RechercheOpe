
public abstract class LocalSearch {
	
	public Problem problem;
	
	public int numberEvalMax;
	public double fitnessMax;
	
	public double bestFitness; 
	
	public LocalSearch(Problem problem)
	{
		this.problem = problem;
		
		this.numberEvalMax = 1000;
		this.fitnessMax = 30000;
	}
	
	public void setOptions(int numberEvalMax, double fitnessMax)
	{
		this.numberEvalMax = numberEvalMax;
		this.fitnessMax = fitnessMax;
	}
	
	public abstract void run(Solution s);
}
