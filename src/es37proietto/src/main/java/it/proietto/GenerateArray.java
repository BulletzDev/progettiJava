package it.proietto;

import java.util.ArrayList;
import java.util.List;

public class GenerateArray {
    private int length;

    public static ArrayList<Integer> intArray(int length){
        ArrayList<Integer> l = new ArrayList<>();
        for (int i = 0; i <length ; i++) {
            int random = (int) (Math.random() * 100);
            l.add(random);
            System.out.println("New value: "+ random);
        }
        return l;
    }

    public static List<Double> doubleArray(int length){
        List<Double> l = new ArrayList<>();
        for (int i = 0; i <length ; i++) {
            double random =(Math.random() * 100)+Math.random();
            l.add(random);
        }
        return l;
    }

}
