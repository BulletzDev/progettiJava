package it.proietto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> l =  GenerateArray.intArray(100000);
        int total = ParallelSum.parallelSum(l,100);
        System.out.println("The total is: "+total);
    }
}