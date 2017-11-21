import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main 
{	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		SacADos sac = SacADos.openDat("ks_1000.dat");
//		SacADos sac = SacADos.openDat("ks_5.dat");
		System.out.println(sac);
		
		int[] nbEvals = {1000,5000,10000,50000,100000};
		int nbExec = 30;
		
		sac.outputResults(nbEvals,nbExec,Algo.BI);
		sac.outputResults(nbEvals,nbExec,Algo.FI);
		sac.outputResults(nbEvals,nbExec,Algo.WI);
		
		System.out.println("Done.");
	}
}
