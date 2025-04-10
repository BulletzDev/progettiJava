package it.proietto;

import java.util.Random;

public class Horse implements Runnable {

    private int index;
    private boolean running = true; 

    public Horse(int index) {
        this.index = index;
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            if (MainRace.raceStopped) {
                return;  
            }

            try {
                Thread.sleep((int) (Math.random() * 100));  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            int i = moveHorse();  
            if (MainRace.moving.get(i) >= Statics.TOTAL_DISTANCE) {
                MainRace.won(i);  
            }
        }
    }

    private int moveHorse() {
        int mMoved = new Random().nextInt(5); 
        synchronized (MainRace.moving) {
            int newPos = MainRace.moving.get(index) + mMoved;
            MainRace.moving.set(index, newPos);
            MainRace.controller.moveHorse(index, newPos);  
        }
        return index;
    }
}
