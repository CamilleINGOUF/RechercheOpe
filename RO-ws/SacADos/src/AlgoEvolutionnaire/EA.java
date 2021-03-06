package AlgoEvolutionnaire;

import java.util.Collections;

import Problem.*;

public class EA {

	private Problem problem;

	private int mu;
	private int lambda;

	private Population parents;
	private Population genitors;
	private Population children;

	private ComparatorSolution comp;
	
	private double bestFitness = 0.0;

	public EA(Problem problem) {
		this.problem = problem;
		int size = problem.size;
		if(size > 10)
			mu = (int) (problem.size / 10f);
		else
			mu = 1;

		lambda = mu * 2;

		parents = new Population(mu);
		genitors = new Population(lambda);
		children = new Population(lambda);

		comp = new ComparatorSolution();

		init();
	}
	
	public double getBestFitness()
	{
		return bestFitness;
	}

	public void init() {
		for(int i = 0; i < mu; i++) {
			Solution s = new Solution(problem.size);
			s.randomSolution();
			parents.add(s);
		}
	}

	public void evalPop() {
		for(Solution s : parents)
			problem.eval(s);

		for(Solution s : children)
			problem.eval(s);
	}

	public void selections() {
		for(int i = 0; i < lambda; i++) {
			int s1 = (int) (Math.random() * mu);
			int s2 = (int) (Math.random() * mu);
			Solution winner = parents.get(s1).getFitness() > parents.get(s2).getFitness() ? parents.get(s1): parents.get(s2);
			genitors.add(winner.clone());
		}
	}

	public void croisementUniforme() {
		for(int i = 0; i < lambda; i += 2) {
			for(int j = 0; j < problem.size; j ++) {
				double pu = Math.random(); 
				if(pu > 0.5) {
					int tempBit = genitors.get(i).get(j);
					genitors.get(i).set(j, genitors.get(i+1).get(j));
					genitors.get(i+1).set(j, tempBit);
				}
			}
		}
	}

	public void mutation() {
		double pn = 1 / (float)problem.size;
		for(Solution s : genitors) {
			for(int i = 0; i < problem.size; i++) {
				double u = Math.random();
				if(u < pn) {
					s.flip(i);
				}
			}
			children.add(s.clone());
		}
	}

	public void remplacement() {
		Population everyone = parents.clone();
		everyone.addAll(children.getSolutions());
		Collections.sort(everyone.getSolutions(),comp);
		Collections.reverse(everyone.getSolutions());
		for(int i = 0; i < mu; i ++) {
			parents.set(i, everyone.get(i));
		}
	}

	public void run() {
		evalPop();
		for(int i = 0; i < 1000; i++) {
			selections();

			croisementUniforme();
			mutation();

			evalPop();

			remplacement();
			System.out.println(i);


			children.getSolutions().clear();
			genitors.getSolutions().clear();

		}
		System.out.println(parents.getSolutions().get(0).getFitness());
		bestFitness = parents.getSolutions().get(0).getFitness();
		//47218.0
	}

	private void debug() {
		int parentsSize = 0;
		int genitorsSize = 0;
		int childrenSize = 0;

		for(Solution s : parents)
			parentsSize++;
		for(Solution s : genitors)
			genitorsSize++;
		for(Solution s : children)
			childrenSize++;

		System.out.println("Parents : "+parentsSize);
		System.out.println("Genitors : "+genitorsSize);
		System.out.println("Children : "+childrenSize);
	}
}
