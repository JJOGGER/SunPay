package com.sunmi.print.internals;

import java.util.Comparator;

public class RunnableComparator implements Comparator<Runnable> {

    private volatile static RunnableComparator sInstance;

    private RunnableComparator() {
    }

    public static RunnableComparator getInstance() {
        if (null == sInstance) {
            synchronized (RunnableComparator.class) {
                if (sInstance == null) {
                    sInstance = new RunnableComparator();
                }
            }
        }
        return sInstance;
    }

    @Override
    public int compare(Runnable o1, Runnable o2) {
        if (o1 instanceof IPriorityRunnable && o2 instanceof IPriorityRunnable) {
            return ((IPriorityRunnable) o1).getPriority() - ((IPriorityRunnable) o2).getPriority();
        } else if (o1 instanceof IPriorityRunnable) {
            return ((IPriorityRunnable) o1).getPriority() - TaskPriority.NORMAL;
        } else if (o2 instanceof IPriorityRunnable) {
            return TaskPriority.NORMAL - ((IPriorityRunnable) o2).getPriority();
        } else {
            return 0;
        }
    }
}
