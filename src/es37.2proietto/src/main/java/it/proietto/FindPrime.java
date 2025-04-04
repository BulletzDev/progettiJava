package it.proietto;

import java.util.ArrayList;

public class FindPrime implements Runnable {
    private final int start,end;
    private final ArrayList<Integer> primes = new ArrayList<>();

    public FindPrime(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public ArrayList<Integer> getPrimes() {
        return primes;
    }

    @Override
    public void run() {
        int number = start;
        // Repeatedly find prime numbers
        while(number <= end) {
            // Assume the number is prime
            boolean isPrime = true; // Is the current number prime?

            // Test if number is prime
            for(int divisor = 2; divisor <= (int)(Math.sqrt(number)); divisor++) {
                if(number % divisor == 0) { // If true, number is not prime
                    isPrime = false; // Set isPrime to false
                    break; // Exit the for loop
                }
            }

            if(isPrime) {// Increase the count
                primes.add(number);
            }

            // Check if the next number is prime
            number++;
        }
    }
}
