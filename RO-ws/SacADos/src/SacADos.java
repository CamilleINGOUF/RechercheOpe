import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class SacADos 
{
	private int nbObjects;

	private int[] profits;
	private int[] weights;

	private int maxWeight;

	private float beta = 0f;

	public SacADos(int nbObjects, int[] profits, int[] weights, int maxWeight) 
	{
		this.nbObjects = nbObjects;
		this.profits = profits;
		this.weights = weights;
		this.maxWeight = maxWeight;

		for(int i = 0; i < nbObjects; i++)
		{
			float buffer = profits[i] / (float)weights[i]; 
			if(buffer > beta)
				beta = buffer;
		}
	}

	public float eval(int[] sac)
	{
		float finalValue = 0;
		float totalWeight = 0;

		for(int i : sac)
		{
			if(i > 1)
				System.exit(0);
		}

		for(int i = 0; i < nbObjects; i++)
		{
			finalValue += sac[i] * profits[i];
			totalWeight += sac[i] * weights[i];
		}

		if (totalWeight > maxWeight) 
			finalValue -= beta * (totalWeight - maxWeight);

		return finalValue;
	}

	//random search
	public double rs(int[] sacTemp, int maxTry) 
	{
		int[] sacBuffer = new int[nbObjects];
		int[] bestSac = new int[nbObjects];

		System.arraycopy( sacTemp, 0, sacBuffer, 0, sacTemp.length );
		System.arraycopy( sacTemp, 0, bestSac, 0, sacTemp.length );

		for(int i = 0; i < maxTry; i++)
		{
			for(int j = 0; j < nbObjects; j++)
				sacBuffer[j] = Math.random()<0.5?0:1;

			if(this.eval(sacBuffer) > this.eval(bestSac))
				System.arraycopy( sacBuffer, 0, bestSac, 0, sacBuffer.length );
		}

		return eval(bestSac);
	}

	//random walk
	public double rw(int[] sacTemp, int maxTry) 
	{
		int[] sacBuffer = new int[nbObjects];
		int[] bestSac = new int[nbObjects];

		System.arraycopy( sacTemp, 0, sacBuffer, 0, sacTemp.length );
		System.arraycopy( sacTemp, 0, bestSac, 0, sacTemp.length );

		for(int i = 0; i < maxTry; i++)
		{
			int randomIndex = (int)(Math.random()*nbObjects);
			flipI(sacBuffer, randomIndex);

			if(this.eval(sacBuffer) > this.eval(bestSac))
				System.arraycopy( sacBuffer, 0, bestSac, 0, sacBuffer.length );
		}

		return eval(bestSac);
	}

	public double bestImprovment(int[] sacTemp, int maxTry, double fitmax, int NbEvalMax)
	{
		int[] sacBuffer = new int[nbObjects];

		System.arraycopy( sacTemp, 0, sacBuffer, 0, sacTemp.length );

		boolean flag = true;
		
		int index = 0;
		double neighborEval;
		double bestNeighbor = 0;
		int iBest;
		
		int nbEval = 0;

		double fitness = eval(sacBuffer);
		do{
			iBest = -1;
			
			for(int i = 0;i < sacBuffer.length; i++)
			{
				flipI(sacBuffer, i);
				neighborEval = eval(sacBuffer);
				nbEval += 1;
				
				if (neighborEval > bestNeighbor) {
					iBest = i;
					bestNeighbor = neighborEval;
				}
				
				if(nbEval >= NbEvalMax) {
					break;
				}
				
				flipI(sacBuffer, i);
			}
			
			if(iBest >= 0 && fitness < bestNeighbor )
			{
				flipI(sacBuffer, iBest);
				fitness = bestNeighbor;
			}
			
			else
				flag = false;
			
			if(bestNeighbor >= fitmax || index == maxTry)
			{
				fitness = bestNeighbor;
				flag = false;
			}
			
			index++;
		}while(flag);

		return fitness;
	}

	public double firstImprovment(int[] sacTemp, int maxTry, double fitmax, int NbEvalMax) 
	{
		int[] sacBuffer = new int[nbObjects];

		System.arraycopy( sacTemp, 0, sacBuffer, 0, sacTemp.length );
		
		boolean flag = true;
		
		int index = 0;
		double neighborEval;
		double bestNeighbor = 0;
		int iBest;

		double fitness = eval(sacBuffer);
		
		int nbEval = 0;
		
		do
		{
			iBest = -1;
			
			for(int i = 0;i < sacBuffer.length; i++)
			{
				flipI(sacBuffer, i);
				neighborEval = eval(sacBuffer);
				nbEval += 1;
				
				
				if (neighborEval > bestNeighbor) {
					iBest = i;
					bestNeighbor = neighborEval;
					break;
				}
				
				if(nbEval >= NbEvalMax) {
					break;
				}
				
				flipI(sacBuffer, i);
			}
			
			if(iBest >= 0 && fitness < bestNeighbor )
			{
				fitness = bestNeighbor;
			}
			
			else
				flag = false;
			
			if(bestNeighbor >= fitmax || index == maxTry)
			{
				fitness = bestNeighbor;
				flag = false;
			}
			
		}while(flag);
		
		return fitness;
	}
	
	public double worstImprovment(int[] sacTemp, int maxTry, double fitmax, int NbEvalMax) {
		int[] sacBuffer = new int[nbObjects];

		System.arraycopy( sacTemp, 0, sacBuffer, 0, sacTemp.length );
		
		boolean flag = true;
		
		int index = 0;
		double neighborEval;
		double worstNeighbor = 0;
		int iBest;

		double fitness = eval(sacBuffer);
		
		int nbEval = 0;
		
		do
		{
			iBest = -1;
			
			for(int i = 0;i < sacBuffer.length; i++)
			{
				flipI(sacBuffer, i);
				neighborEval = eval(sacBuffer);
				nbEval += 1;
				
				if (fitness < neighborEval && neighborEval < worstNeighbor || i == 0) {
					iBest = i;
					worstNeighbor = neighborEval;
					break;
				}
				
				if(nbEval >= NbEvalMax) {
					break;
				}
				
				flipI(sacBuffer, i);
				
				
			}
			
			if(iBest >= 0 && fitness < worstNeighbor )
			{
				fitness = worstNeighbor;
			}
			else
				flag = false;
			
			if(worstNeighbor >= fitmax || index == maxTry)
			{
				fitness = worstNeighbor;
				flag = false;
			}
			
		}while(flag);
		
		return fitness;
	}

	private void flipI(int[] sacBuffer, int i) {
		sacBuffer[i] = sacBuffer[i] == 0 ? 1 : 0;
	}

	public void outputResults(int[] nbEvals, int nbExec, Algo algo) throws FileNotFoundException, UnsupportedEncodingException
	{
		int[] sacTemp = new int[nbObjects];
		
		double fitmax = 30000;

		String fileName = new String();
		double bestEval = 0;

		if(algo == Algo.RS)
			fileName = "rs.csv";
		else if(algo == Algo.RW)
			fileName = "rw.csv";
		else if(algo == Algo.BI)
			fileName = "bi.csv";
		else if(algo == Algo.FI)
			fileName = "fi.csv";
		else if(algo == Algo.WI)
			fileName = "wi.csv";

		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println("nbeval fitness fitmax nbevalmax");

		for(int i = 0; i < nbEvals.length; i++)
		{
			for(int j = 0; j < nbExec; j++)
			{
				for(int k = 0; k < sacTemp.length; k++)
					sacTemp[k] = Math.random()<0.5?0:1;
				
				int nbEvalMax = 2000;
				
				if(algo == Algo.RS)
					bestEval = rs(sacTemp, nbEvals[i]);
				else if(algo == Algo.RW)
					bestEval = rw(sacTemp, nbEvals[i]);
				else if(algo == Algo.BI)
					bestEval = bestImprovment(sacTemp, nbEvals[i],fitmax, nbEvalMax);
				else if(algo == Algo.FI) 
					bestEval = firstImprovment(sacTemp, nbEvals[i],fitmax, nbEvalMax);
				else if(algo == Algo.WI) 
					bestEval = worstImprovment(sacTemp, nbEvals[i],fitmax, nbEvalMax);
				
				
				writer.println(nbEvals[i] + " " + bestEval + " " + fitmax + " " + nbEvalMax);
				System.out.println(nbEvals[i] + " " + bestEval);
			}
		}

		writer.close();
	}

	public String toString()
	{
		String str = new String();
		str += ("Number of objects : "+nbObjects+"\n");
		for(int i : profits)
			str += (i+" ");
		str += "\n";
		for(int i : weights)
			str += (i+" ");
		str += ("\nC : "+maxWeight);
		str += ("\nBeta : "+beta);
		return str;
	}

	@SuppressWarnings("resource")
	public static SacADos openDat(String datFile) throws FileNotFoundException
	{
		int nbObjects;

		int[] profits;
		int[] weights;

		int maxWeight;

		Scanner sc = new Scanner(new File(datFile));
		nbObjects = sc.nextInt();

		profits = new int[nbObjects];
		weights = new int[nbObjects];

		for(int i = 0; i < nbObjects; i++)
			profits[i] = sc.nextInt();

		for(int i = 0; i < nbObjects; i++)
			weights[i] = sc.nextInt();

		maxWeight = sc.nextInt();

		return new SacADos(nbObjects, profits, weights, maxWeight);
	}
}

