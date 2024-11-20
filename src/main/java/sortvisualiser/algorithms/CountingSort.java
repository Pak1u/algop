package sortvisualiser.algorithms;
import sortvisualiser.SortArray;


public class CountingSort implements ISortAlgorithm
{
	private long stepDelay = 5;
	
	@Override
	public String getName() 
	{
		return "Counting Sort";
	}

	@Override
	public long getDelay()
	{
		return stepDelay;
	}

	@Override
	public void setDelay(long delay) 
	{		
		stepDelay = delay;
	}

	@Override
	
	
	public void runSort(SortArray array)
	{
		int[] result = new int[array.arraySize()];
		int[] count = new int[array.getMaxValue()+1];		
		for(int i = 0; i < result.length; ++i)
		{	
			array.updateSingle(i, result[i] = array.getValue(i), getDelay(), false);		
			++count[array.getValue(i)];
		}
		for(int i = 1; i < count.length; ++i)
			count[i] += count[i-1];
		for(int i = result.length-1; i > -1; --i)
			array.updateSingle(--count[result[i]], result[i], getDelay(), true);		
	}

}
