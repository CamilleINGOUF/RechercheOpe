
public class RecuitSimule extends LocalSearch {

	private float temperature;
	private float alpha;
	private int temperatureUpdate;

	public RecuitSimule(Problem problem) {
		super(problem);
		temperature = 10;
		alpha = .95f;
		temperatureUpdate = 50;
	}

	public RecuitSimule(Problem problem, int numberEvalMax, double fitnessMax) {
		super(problem);
		this.numberEvalMax = numberEvalMax;
		this.fitnessMax = fitnessMax;
		temperature = 10;
		alpha = .95f;
		temperatureUpdate = 50;
	}

	@Override
	public void run(Solution s) {
		Solution solutionBuffer = new Solution(s);
		
		problem.eval(s);
		double fitness = problem.eval; 

		boolean flag = true;
		int numberEval = 0;
		int iterations = 0;

		do {

			int randomIndex = (int)(Math.random()*s.bits.size());
			solutionBuffer.flip(randomIndex);
			
			numberEval++;
			problem.eval(solutionBuffer);
			double neighborEval = problem.eval;
			double delta = neighborEval - fitness;

			if(delta > 0) {
				fitness = neighborEval;
			} else {
				float u = (float) Math.random();
				double exp = Math.exp(delta / temperature);
				if(u < exp) {
					fitness = neighborEval;
				} else {
					solutionBuffer.flip(randomIndex);
				}
			}

			if(iterations % temperatureUpdate == 0) {
				temperature *= alpha;
			}

			if(numberEval >= numberEvalMax || fitness >= fitnessMax)
				flag = false;
			iterations ++;
		} while (flag);

		bestFitness = fitness;
	}

}