package sortvisualiser.algorithms;
import sortvisualiser.SortArray;


public class IterativeMergeSort implements ISortAlgorithm
{
	private long stepDelay = 5;
	

	@Override
	public void runSort(SortArray array) 
	{
		for(int exp = 1; exp < array.arraySize(); exp <<= 1)
			for(int k = 0, j = exp+exp, s = array.arraySize()-exp; k<s; k+=j)
				merge(array, k, exp);
	}
	
	private int[] getSubArray(SortArray array, int start, int end)
	{
		int size = end - start;
		int arr[] = new int[size];
		for (int i = 0; i < size; i++) 
			arr[i] = array.getValue(start + i);		
		return arr;
	}
	
	
	private void merge(SortArray arr, int start, int exp)
	{
		int s = start;
		int m = start + exp;
		int end = (arr.arraySize() < m+exp) ? arr.arraySize() : m + exp;				
		int[] leftArr = getSubArray(arr, s, m);
		int[] rightArr = getSubArray(arr, m, end);
		int i = 0, j = 0;
		while(i < leftArr.length && j < rightArr.length)
			if(leftArr[i] <= rightArr[j])
				arr.updateSingle(start++, leftArr[i++], getDelay(), true);
			else
				arr.updateSingle(start++, rightArr[j++], getDelay(), true);
		while (i < leftArr.length)
			arr.updateSingle(start++, leftArr[i++], getDelay(), true);
		while (j < leftArr.length)
			arr.updateSingle(start++, rightArr[j++], getDelay(), true);		
	}	

	@Override
	public String getName() {
		return "Iterative Merge Sort";
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
