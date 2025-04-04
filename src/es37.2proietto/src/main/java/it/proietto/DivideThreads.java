package it.proietto;

import java.util.ArrayList;

public class DivideThreads {

    public static ArrayList<Integer> allPrimesThreads(int endSearch, int numThread) throws InterruptedException {
        ArrayList<Integer> allPrimes = new ArrayList<>();

        FindPrime[] tasks =new FindPrime[numThread];
        Thread[] threads = new Thread[numThread];
        int chunkSize = (int) Math.ceil((double) endSearch / numThread);

        for (int i = 0; i < numThread; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, endSearch);
            tasks[i] = new FindPrime(start, end);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < numThread; i++) {
            threads[i].join();
            allPrimes.addAll(tasks[i].getPrimes());
        }

        return allPrimes;

    }
}
