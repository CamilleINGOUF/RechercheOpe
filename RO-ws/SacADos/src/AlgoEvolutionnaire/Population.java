package AlgoEvolutionnaire;

import java.util.ArrayList;
import java.util.Iterator;

import Problem.Solution;

public class Population implements Iterable<Solution>{

	private ArrayList<Solution> solutions;
	
	public Population(int size) {
		solutions = new ArrayList<>(size);
	}
	
	public Population(Population that) {
		solutions = new ArrayList<>(that.solutions.size());
		for(Solution s : that.solutions)
			solutions.add(s.clone());
	}

	public void add(Solution s)
	{
		solutions.add(s);
	}
	
	public void addAll(ArrayList<Solution> s)
	{
		solutions.addAll(s);
	}
	
	public void remove(Solution s)
	{
		solutions.remove(s);
	}
	
	public void set(int i, Solution s)
	{
		solutions.set(i, s);
	}
	
	public Solution get(int i)
	{
		return solutions.get(i);
	}
	
	public ArrayList<Solution> getSolutions()
	{
		return solutions;
	}
	
	@Override
	public Iterator<Solution> iterator() {
		return solutions.iterator();
	}
	
	public Population clone() {
		return new Population(this);
	}

}
