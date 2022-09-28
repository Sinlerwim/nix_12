package com.command;

import lombok.SneakyThrows;

public class ShowThread implements Command {

    @SneakyThrows
    @Override
    public void execute() {
        CountThread[] threads = new CountThread[50];
        for(int i = 0; i<50; i++) {
            threads[i] = new CountThread();
            threads[i].start();
            threads[i].join();
        }
    }
}

class CountThread extends Thread {
    public static int numberOfThread = 49;
    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(50);
        System.out.println("Hello from thread " + numberOfThread--);
    }
}
