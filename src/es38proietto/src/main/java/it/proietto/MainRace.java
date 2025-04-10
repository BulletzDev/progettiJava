package it.proietto;

import java.util.ArrayList;

public class MainRace {

    public static PrimaryController controller;

    static ArrayList<Thread> race = new ArrayList<>();
    static ArrayList<Horse> horses = new ArrayList<>();
    static ArrayList<Integer> moving = new ArrayList<>();

    static boolean raceStopped = false;

    public static void startRace() {
        if (raceStopped) {
            raceStopped = false;
            System.out.println("Starting new race...");
        }

        race.clear();
        horses.clear();
        moving.clear();


        for (int i = 0; i < Statics.H_AMOUNT; i++) {
            moving.add(0);
            Horse h = new Horse(i);
            horses.add(h);
            Thread t = new Thread(h);
            race.add(t);
        }


        for (Thread t : race) {
            t.start();
        }
    }

    public synchronized static void won(int index) {
        if (!raceStopped) {
            raceStopped = true;  
            System.out.println("Horse " + index + " won!");
            stopRace();
        }  
    }

    public static void stopRace() {
        if (raceStopped) {
            System.out.println("Race is already stopped.");
            return;
        }

        for (Horse h : horses) {
            h.stopRunning();
        
        }


        for (Thread t : race) {
            try {
                t.join();  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        moving.clear();
    }
}
