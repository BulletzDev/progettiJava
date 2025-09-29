package it.proietto;

import java.util.ArrayList;

public class MainRace {

    static ArrayList<Thread> race = new ArrayList<>();
    static ArrayList<Horse> horses = new ArrayList<>();
    static ArrayList<Integer> moving = new ArrayList<>();
    static ArrayList<Horse> leaderboard = new ArrayList<>();

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
            printLeadeboard();
        }
    }

    public static void printLeadeboard() {
        int i = 1;
        if (raceStopped) {
            System.out.println("Leaderboard:");
            for (Horse h : leaderboard) {
                System.out.println(i + ": " + h.getHorse());
                i++;
            }
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

    public static void addToLeaderboard(Horse h) {
        if (!leaderboard.contains(h)) {
            leaderboard.add(h);
        }
        if (leaderboard.size() == Statics.H_AMOUNT) {
            won(leaderboard.get(0).getIndex());
        }
    }

    public static ArrayList<Horse> getLeaderboard() {
        return leaderboard;
    }

}