package Problem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class KnapSack extends Problem{
	
	public Vector<Integer> profits;
	public Vector<Integer> weights;
	
	public int c;
	public float beta;
	
	
	public KnapSack(String datFile) throws FileNotFoundException {
		profits = new Vector<Integer>();
		weights = new Vector<Integer>();
		
		openDat(datFile);
		
		for(int i = 0; i < size; i++)
		{
			float buffer = profits.get(i) / (float)weights.get(i); 
			if(buffer > beta)
				beta = buffer;
		}
		
		eval = 0;
	}
	
	private void openDat(String datFile) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(datFile));

		size = sc.nextInt();
		
		for(int i = 0; i < size; i++)
			profits.add(sc.nextInt());

		for(int i = 0; i < size; i++)
			weights.add(sc.nextInt());
		
		c = sc.nextInt();
		
		sc.close();
	}
	
	@Override
	public void eval(Solution s)
	{
		double finalValue = 0;
		double totalWeight = 0;
		
		for(int i = 0; i < s.bits.size(); i++)
		{
			if(s.get(i) > 1)
				System.exit(0);
		}

		for(int i = 0; i < size; i++)
		{
			int bit = s.get(i);
			finalValue += bit * profits.get(i);
			totalWeight += bit * weights.get(i);
		}

		if (totalWeight > c) 
			finalValue -= beta * (totalWeight - c);
		
		eval = finalValue;
		s.setFitness(finalValue);
	}
	
	@Override
	public String toString() {
		String str = new String();
		str += ("Number of objects : "+size+"\n");
		for(int i : profits)
			str += (i+" ");
		str += "\n";
		for(int i : weights)
			str += (i+" ");
		str += ("\nC : "+c);
		str += ("\nBeta : "+beta);
		return str;
	}
}
