package com.repository;

import com.model.Vehicle;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


public class Garage<T extends Vehicle> {

    private int size = 0;

    public GarageNode<T> first;

    public GarageNode<T> last;

    public int getSize() { return size; }

    private static class GarageNode<T> {
        T item;
        Date date;
        int restyling;
        Garage.GarageNode<T> next;
        Garage.GarageNode<T> prev;

        GarageNode(Garage.GarageNode<T> prev, T element, int restyling, Garage.GarageNode<T> next) {
            this.item = element;
            this.restyling = restyling;
            date = new Date();
            this.restyling = restyling;
            this.next = next;
            this.prev = prev;
        }
    }

    public void add(T element, int restyling) {
        final GarageNode<T> previousFirst = first;
        final  GarageNode<T> newGarageNode = new GarageNode<>(null, element, restyling, first);
        first = newGarageNode;
        if (previousFirst == null)
            last = newGarageNode;
        else
            previousFirst.prev = first;
        size++;
    }

    public Optional<T> search(int restyling) {
        GarageNode<T> currentNode = first;
        do {
            if (currentNode.restyling == restyling)
                return Optional.of(currentNode.item);
            currentNode = currentNode.next;
        }
        while (currentNode.next != null);
        return Optional.empty();
    }

    public boolean remove(int restyling) {

        GarageNode<T> currentNode = first;
        GarageNode<T> previousNode;
        GarageNode<T> nextNode;
        do {
            if (currentNode.restyling == restyling) {
                size--;
                if (currentNode == first) {
                    currentNode.next.prev = null;
                    return true;
                } else if (currentNode == last) {
                    currentNode.prev.next = null;
                    return true;
                } else {
                    previousNode = currentNode.prev;
                    nextNode = currentNode.next;
                    previousNode.next = nextNode;
                    nextNode.prev = previousNode;
                    currentNode = currentNode.prev;
                }
                currentNode.item = null;
//                currentNode.restyling = Integer.parseInt(null);
//                currentNode.date = null;
                ;
            }
            currentNode = currentNode.next;
        }
        while (currentNode.next != null);
        return false;
    }

    public boolean replace(int restyling, T vehicle, int newRestyling) {
        GarageNode<T> currentNode = first;
        do {
            if (currentNode.restyling == restyling) {
                currentNode.item = vehicle;
                currentNode.date = new Date();
                currentNode.restyling = newRestyling;
                return true;
            }
        }
        while (currentNode.next != null);
        return false;
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        GarageNode<T> currentNode = first;
        do {
            action.accept(currentNode.item);
        }
        while (currentNode.next != null);
    }

    public Optional<Date> getFirstDate() {
        return Optional.of(first.date);
    }

    public Optional<Date> getLastDate() {
        return Optional.of(last.date);
    }

}
