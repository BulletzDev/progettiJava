package it.proietto;

import java.util.ArrayList;

public class ParallelSum{
    public static int parallelSum(ArrayList<Integer> array, int numThreads) throws InterruptedException {
        int length = array.size();
        SumTask[] tasks = new SumTask[numThreads];
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (int) Math.ceil((double) length / numThreads);

        //initialize the threads
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, length);
            tasks[i] = new SumTask(array, start, end);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        // waiting the threads
        int totalSum = 0;
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
            totalSum += tasks[i].getPartialSum();
        }

        return totalSum;
    }

}
