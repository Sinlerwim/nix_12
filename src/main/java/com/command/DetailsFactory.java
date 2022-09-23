package com.command;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class DetailsFactory implements Command {
    @Override
    public void execute() {
        AtomicInteger fuel = new AtomicInteger(0);

        Object fuelLock = new Object();

        Thread fuelMiner = new Thread(new FuelMiner(fuel, fuelLock));
        fuelMiner.setDaemon(true);
        Thread builder = new Thread(new Builder(fuel, fuelLock));
        Thread programmer = new Thread(new Programmer(builder));
        BaseConstructor.setProgrammer(programmer);
        Thread firstBaseConstructor = new Thread(new BaseConstructor());
        Thread secondBaseConstructor = new Thread(new BaseConstructor());


        fuelMiner.start();
        firstBaseConstructor.start();
        secondBaseConstructor.start();
    }
}


class FuelMiner implements Runnable {

    AtomicInteger fuel;

    Object fuelLock;

    public FuelMiner(AtomicInteger fuel, Object fuelLock) {
        this.fuel = fuel;
        this.fuelLock = fuelLock;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Miner started");
        while (true) {
            int minedFuel = new Random().nextInt(500, 1000);
            System.out.println("Mined " + minedFuel + " fuel. Waiting 3 seconds...");
            System.out.println("Total fuel: " + (fuel.get() + minedFuel));
            Thread.sleep(3000);
            if (fuel.getAndAdd(minedFuel) < 700) {
                synchronized (fuelLock) {
                    System.out.println("Notifying all threads about adding fuel");
                    fuelLock.notifyAll();
                }

            }
        }
    }
}

class BaseConstructor implements Runnable {

    private static Thread programmer;

    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(3);

    private static final Random RANDOM = new Random();

    private static AtomicInteger baseConstructionProgress = new AtomicInteger(0);

    public static void setProgrammer(Thread programmer) {
        BaseConstructor.programmer = programmer;
        Programmer.setCyclicBarrier(CYCLIC_BARRIER);
    }

    @SneakyThrows
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("Base constructor " + threadName + " started");
        if (baseConstructionProgress.get() >= 100) {
            baseConstructionProgress = new AtomicInteger(0);
        }
        int work;
        while (baseConstructionProgress.get() < 100) {
            work = RANDOM.nextInt(10, 20);
            System.out.println(threadName + " work: +" + work + ". Total: " + baseConstructionProgress.addAndGet(work));
            Thread.sleep(2000);
        }
        if(!programmer.isAlive()) {
            programmer.start();
        }
        System.out.println(threadName + " is waiting...");
        CYCLIC_BARRIER.await();
    }
}

class Programmer implements Runnable {

    private static CyclicBarrier cyclicBarrier;

    private static final Random RANDOM = new Random();

    private Thread builder;

    private static int programmingProgress = 0;

    public Programmer(Thread builder) {
        this.builder = builder;
    }

    public static void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        Programmer.cyclicBarrier = cyclicBarrier;
    }

    @SneakyThrows
    @Override
    public void run() {
        cyclicBarrier.await();
        System.out.println("Programmer started");
        if (programmingProgress >= 100) {
            programmingProgress = 0;
        }
        int work;
        while (programmingProgress < 100) {
            work = RANDOM.nextInt(25, 35);
            programmingProgress += work;
            Thread.sleep(1000);
            System.out.println("Programmer done " + work + "% of work. Total progress - " + programmingProgress);
            if(RANDOM.nextInt(100) < 30) {
                programmingProgress = 0;
                System.out.println("Oops, scheme is destroyed. Starting from 0");
            }
        }
        builder.start();
    }
}

class Builder implements Runnable {

    private static final int STEP = 10;
    private static final Random RANDOM = new Random();
    private static int amountOfDetails = 0;

    AtomicInteger fuel;

    Object fuelLock;

    public Builder(AtomicInteger fuel, Object fuelLock) {
        this.fuel = fuel;
        this.fuelLock = fuelLock;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Builder(5) start");
        int buildProgress = 0;
        int fuelConsumption = RANDOM.nextInt(350, 700);
        while (buildProgress < 100) {
            while (fuel.get() - fuelConsumption < 0) {
                synchronized (fuelLock) {
                    System.out.println("Fuel is too low (" + fuel.get() + "). Need " + fuelConsumption +
                            ". Waiting for adding...");
                    fuelLock.wait();
                }
            }
            buildProgress += STEP;
            fuel.addAndGet(-fuelConsumption);
            System.out.println(STEP + "% of work is done. Total progress: " + buildProgress + "\n" +
                    "Fuel consumption: " + fuelConsumption);
            Thread.sleep(1000);
        }
        System.out.println("Detail is created. Amount of details - " + ++amountOfDetails);
    }
}