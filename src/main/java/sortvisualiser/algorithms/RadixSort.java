package sortvisualiser.algorithms;
import java.util.Arrays;
import sortvisualiser.SortArray;


public class RadixSort implements ISortAlgorithm {

	private long stepDelay = 5;
	private int radix;	
	private int[] countingArr;

	public RadixSort(int radix)
	{
		this.radix = radix;
		countingArr = new int[radix];
	}	
	
	
	public RadixSort()
	{
		this(10);
	}

		
	@Override
	public void runSort(SortArray array)
	{
		int largest = array.getMaxValue();
		int[] result = new int[array.arraySize()];
		
		for(int exp = 1; largest/exp > 0; exp *= radix)		
		{
			countingArr = countingSort(array, exp);
			
			for(int i = 0; i < result.length; ++i)
				array.updateSingle(i, result[i] = array.getValue(i), getDelay(), false);				
			
			for(int i = 1; i < radix; ++i)
				countingArr[i] += countingArr[i-1];
			
			for(int i = array.arraySize() - 1; i > -1 ; --i)
				array.updateSingle(--countingArr[(result[i]/exp)%radix], result[i], getDelay(), true);	
		}		
	}

	
	private int[] countingSort(SortArray arr, int exp)
	{
		Arrays.fill(countingArr, 0);
		for(int i = 0; i < arr.arraySize(); ++i)
			countingArr[(arr.getValue(i)/exp)%radix]++;
		return countingArr;
	}

	@Override
	public String getName() {
		return "Radix Sort (Base " + radix + ")";
	}

	@Override
	public long getDelay() {
		return stepDelay;
	}

	@Override
	public void setDelay(long delay) {
		this.stepDelay = delay;
	}
}
