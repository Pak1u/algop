package sortvisualiser.algorithms;

import sortvisualiser.SortArray;


 public class GnomeSort implements ISortAlgorithm {

    private long stepDelay = 2;
   
    @Override
    public void runSort(SortArray array) {
        int index = 0;
        while (index < array.arraySize()) {
            if (index == 0) {
                index++;
            }
            if (array.getValue(index) >= array.getValue(index - 1)) {
                index++;
            } else {
                array.swap(index, index - 1, getDelay(), true);
                index--;
            }
        }
    }

    @Override
    public String getName() {
        return "Gnome sort";
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
