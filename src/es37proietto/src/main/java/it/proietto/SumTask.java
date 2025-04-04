package it.proietto;

import java.util.ArrayList;

public class SumTask implements Runnable {
    private final ArrayList<Integer> array;
    private final int start, end;
    private int partialSum;

    public SumTask(ArrayList<Integer> array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        partialSum = 0;
        for (int i = start; i < end; i++) {
            partialSum += array.get(i);
        }
    }

    public int getPartialSum() {
        return partialSum;
    }
}