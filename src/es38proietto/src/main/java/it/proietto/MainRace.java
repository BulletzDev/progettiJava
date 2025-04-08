package it.proietto;

import java.util.ArrayList;

public class MainRace {

    static ArrayList<Thread> race = new ArrayList<>();
    static ArrayList<Integer> moving = new ArrayList<>();

    public void startRace(){
        for (int i = 0; i <Statics.H_AMOUNT ; i++) {
            Thread t = new Thread(new Horse());
            race.add(t);
        }
        for (int i = 0; i <Statics.H_AMOUNT ; i++) {
            race.get(i).start();
        }
    }

    public static void won(int i){

    }

}
