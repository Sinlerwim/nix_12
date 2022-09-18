package com.command;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ListThread implements Command {
    @SneakyThrows
    @Override
    public void execute() {
        List<Integer> numbers = new ArrayList<>();
        List<Integer> simpleNumbers = new ArrayList<>();
        int amountOfPrimes = 0;
        for(int i = 0; i<100; i++) {
            numbers.add(i);
            if(isPrime(numbers.get(i))) {
                amountOfPrimes++;
            }
        }
        System.out.println("Amount in for - " + amountOfPrimes);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> firstTask = executor.submit((Callable<Integer>) new CalculatorThread(numbers));
        Future<Integer> secondTask = executor.submit((Callable<Integer>) new CalculatorThread(numbers));
        System.out.println("Amount in threads - " + (firstTask.get() + secondTask.get()));
    }

    private boolean isPrime(int number) {
        if (number == 0 || number == 1) {
            return false;
        }
        for (int i=2; i<=number/2; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}

class CalculatorThread extends Thread implements Callable<Integer> {

    private static boolean isAlreadyExist = false;

    private static List<Integer> numbers;

    private static int listSize;

    public CalculatorThread(List<Integer> list) {
        if(numbers == null) {
            numbers = list;
            listSize = list.size();
        }
    }

    @Override
    public Integer call() throws Exception {
        AtomicInteger amountOfPrimes = new AtomicInteger(0);
        if(!isAlreadyExist) {
            isAlreadyExist = true;
            for(int i = 0; i<listSize/2; i++) {
                if(isPrime(numbers.get(i))) {
                    amountOfPrimes.incrementAndGet();
                }
            }
        } else {
            for(int i = listSize/2; i<listSize; i++) {
                if(isPrime(numbers.get(i))) {
                    amountOfPrimes.incrementAndGet();
                }
            }
        }
        return amountOfPrimes.get();
    }

    private boolean isPrime(int number) {
        if (number == 0 || number == 1) {
            return false;
        }
        for (int i=2; i<=number/2; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
