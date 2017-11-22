import java.util.Vector;

public class Solution {
	public Vector<Integer> bits;
	
	public Solution(int size) {
		bits = new Vector<Integer>();
		for(int i = 0; i < size; i++)
			bits.add(i,0);
	}
	
	public Solution(Solution that) {
		this.bits = new Vector<>();
		for(int i = 0; i < that.bits.size(); i++)
			this.bits.add(new Integer(that.bits.get(i)));
	}
	
	public void resize(int size)
	{
		bits = new Vector<Integer>(size);
	}
	
	public void randomSolution() {
		for(int k = 0; k < bits.size(); k++)
			bits.set(k,Math.random()<0.5?0:1);
	}
	
	public int get(int i)
	{
		return bits.get(i);
	}
	
	public void set(int i, int v)
	{
		bits.set(i, v);
	}
	
	public void flip(int i)
	{
		int bit = bits.get(i) == 0 ? 1 : 0;
		bits.set(i, bit);
	}
}