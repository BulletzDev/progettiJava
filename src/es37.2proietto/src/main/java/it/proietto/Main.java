package it.proietto;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> primes = DivideThreads.allPrimesThreads(17000,50);
        for (Integer prime : primes) {
            System.out.println("prime: " + prime);
        }
    }
}