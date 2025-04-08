package it.proietto;

import com.sun.tools.javac.Main;

import java.util.Random;

public class Horse implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random()*1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int i = getI();
        //update ui
        if(MainRace.moving.get(i)>=Statics.TOTAL_DISTANCE){
            MainRace.won(i);
        }
    }

    private static int getI() {
        int mMoved = new Random().nextInt(5);
        int i =MainRace.race.indexOf(Thread.currentThread());
        MainRace.moving.set(i, MainRace.moving.get(i)+mMoved);
        return i;
    }
}
