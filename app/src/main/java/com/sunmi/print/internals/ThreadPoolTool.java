package com.sunmi.print.internals;

import androidx.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

public class ThreadPoolTool {

    private static final String THREAD_NAME_BUSINESS = "[bus]-";
    private static final String THREAD_NAME_DOWNLOAD = "[dow]-";
    private static final String THREAD_NAME_NETWORK = "[net]-";
    private static final String THREAD_NAME_SINGLE = "[sin]-";

    public static final int BUSINESS_PRIORITY = 4;
    public static final int BACKGROUND_PRIORITY = 2;

    public static ThreadFactory getBusinessFactory() {
        return new DefaultThreadFactory(THREAD_NAME_BUSINESS, BUSINESS_PRIORITY);
    }

    public static ThreadFactory getDownloadFactory() {
        return new DefaultThreadFactory(THREAD_NAME_DOWNLOAD, BACKGROUND_PRIORITY);
    }

    public static ThreadFactory getNetworkFactory() {
        return new DefaultThreadFactory(THREAD_NAME_NETWORK, BACKGROUND_PRIORITY);
    }

    public static ThreadFactory getSingleFactory() {
        return new DefaultThreadFactory(THREAD_NAME_SINGLE, BACKGROUND_PRIORITY);
    }

    public static void setThreadSubName(String name) {
        String oldName = Thread.currentThread().getName();
        if (oldName != null && oldName.length() >= 6) {
            String prefix = oldName.substring(0, 6);
            if (prefix.equals(THREAD_NAME_BUSINESS) || prefix.equals(THREAD_NAME_DOWNLOAD) ||
                    prefix.equals(THREAD_NAME_NETWORK) || prefix.equals(THREAD_NAME_SINGLE)) {
                Thread.currentThread().setName(prefix + name);
                return;
            }
        }
        Thread.currentThread().setName(name);
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final String namePrefix;
        private final int mPriority;

        DefaultThreadFactory(@NonNull String prefix, int priority) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = prefix;
            mPriority = priority;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(group, r, namePrefix, 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(mPriority);
            return t;
        }
    }

}


