
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
		int iterations = 0;
		
		int consecutiveFail = 0;

		do {

			int randomIndex = (int)(Math.random()*s.bits.size());
			solutionBuffer.flip(randomIndex);
			
			numberEval++;
			problem.eval(solutionBuffer);
			double neighborEval = problem.eval;
			double delta = neighborEval - fitness;

			if(delta > 0) {
				fitness = neighborEval;
				consecutiveFail = 0;
			} else {
				float u = (float) Math.random();
				double exp = Math.exp(delta / temperature);
				if(u < exp) {
					fitness = neighborEval;
					consecutiveFail = 0;
				} else {
					solutionBuffer.flip(randomIndex);
//					consecutiveFail++;
				}
			}

			if(iterations % temperatureUpdate == 0) {
				temperature *= alpha;
			}

			if(numberEval >= numberEvalMax || fitness >= fitnessMax || consecutiveFail >= 3)
				flag = false;
			iterations ++;
		} while (flag);

		bestFitness = fitness;
	}

}
