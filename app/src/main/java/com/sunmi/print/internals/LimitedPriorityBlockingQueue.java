package com.sunmi.print.internals;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class LimitedPriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int CAPACITY_INVALID = -1;

    private int maxCapacity = CAPACITY_INVALID;

    public LimitedPriorityBlockingQueue(Comparator<? super E> comparator) {
        super(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    public LimitedPriorityBlockingQueue(int maxCapacity, Comparator<? super E> comparator) {
        super(DEFAULT_INITIAL_CAPACITY, comparator);
        if (maxCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean offer(E e) {
        return (maxCapacity == CAPACITY_INVALID || this.size() < maxCapacity) && super.offer(e);
    }
}
