package com.kalllx.exception;

import java.util.Timer;
import java.util.TimerTask;

public class Hi {
    public static void main(String args[]) throws java.io.IOException {
        TimerTask task = new TimerTask() {
            public void run() {
        	System.out.println(Thread.currentThread().getName());
                System.out.println("Hi");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 500, 500);
        System.out.println("Press ENTER to stop");
        System.in.read(new byte[5]);
        System.out.println(Thread.currentThread().getName());
        timer.cancel();
    }
}