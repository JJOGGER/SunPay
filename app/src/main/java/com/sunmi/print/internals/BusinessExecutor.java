package com.sunmi.print.internals;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义业务线程池
 * 用于统一记录所有使用该线程池的异步任务情况
 *
 * @author lxp 2018.11.30
 */
public class BusinessExecutor extends ThreadPoolExecutor {
    private static final String TAG = "BusinessExecutor";

    private ConcurrentHashMap<Runnable, LocalRunnable> mRunnableLocalMap;
    // 当前正在执行的异步任务列表
    private List<Runnable> mActiveList;

    public BusinessExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        mRunnableLocalMap = new ConcurrentHashMap<>(32);
        mActiveList = Collections.synchronizedList(new ArrayList<Runnable>());
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }
        LocalRunnable localRunnable = new LocalRunnable(command);
        mRunnableLocalMap.put(command, localRunnable);
        super.execute(localRunnable);
    }

    public List<Runnable> getActiveList() {
        return mActiveList;
    }

    public final class LocalRunnable implements Runnable {

        private Runnable realRunnable;

        private LocalRunnable(@NonNull Runnable runnable) {
            this.realRunnable = runnable;
        }

        @Override
        public void run() {
            mRunnableLocalMap.remove(realRunnable);
            mActiveList.add(realRunnable);
            try {
                realRunnable.run();
            } catch (Throwable e) {
                // do nothing
            }
            mActiveList.remove(realRunnable);
        }

        @Override
        public String toString() {
            return realRunnable.toString();
        }
    }

    @Override
    public boolean remove(Runnable task) {
        boolean result = false;
        LocalRunnable localRunnable = mRunnableLocalMap.get(task);
        if (localRunnable != null) {
            result = super.remove(localRunnable);
            if (result) {
                mRunnableLocalMap.remove(task);
            }
        }
        return result;
    }
}
