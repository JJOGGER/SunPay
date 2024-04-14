package com.sunmi.print.internals;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hongcanyang
 * @email hcy@meitu.com
 * @data 2018/2/9 9:44
 * description: 业务策略：核心线程数为4，最大线程数为虚拟器可用进程 * 2 +1
 * 核心线程没有配置超时销毁处理
 * 等待队列配置容器为10，如果线程超过 BusinessPolicy.MAXIMUM_POOL_SIZE + 10,会触发{@link BusinessRejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)}的异常提示。
 */

public class BusinessPolicy implements IPolicy {

    private static final String TAG = "BusinessPolicy";

    // 最大核心线程数
    private static final int DEFAULT_CORE_THREAD_COUNT = 4;
    // cpu核数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // 最大线程数
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    // 核心线程数
    private static final int CORE_COUNT = Math.max(Math.min(DEFAULT_CORE_THREAD_COUNT, CPU_COUNT / 2), 2);//最小限制2
    // 非核心线程的空闲存活时间（单位秒）
    private static final int KEEP_ALIVE_TIME = 30;
    // 业务线程池
    private volatile static BusinessExecutor sBusinessExecutor;

    @Override
    public ThreadPoolExecutor getExecutor() {
        if (null == sBusinessExecutor) {
            synchronized (BusinessPolicy.class) {
                if (sBusinessExecutor == null) {
                    sBusinessExecutor =
                            new BusinessExecutor(CORE_COUNT, MAXIMUM_POOL_SIZE,
                                    KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LimitedPriorityBlockingQueue<>(10, RunnableComparator.getInstance()),
                                    ThreadPoolTool.getBusinessFactory(), new BusinessRejectedExecutionHandler());
                }
            }
        }
        return sBusinessExecutor;
    }

    @Override
    public int getCorePollSize() {
        return CORE_COUNT;
    }

    @Override
    public int getKeepAliveTime() {
        return KEEP_ALIVE_TIME;
    }

    @Override
    public boolean canExecuteAtOnce() {
        return getExecutor().getActiveCount() < getCorePollSize();
    }

    public static class BusinessRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            if (ApplicationConfigure.isForTester) {
//                throw new RejectedExecutionException(r.toString() + " rejected from " + e.toString());
//            } else {
//                ThreadUtils.getOptimalPolicy().getExecutor().execute(r);
//            }
        }
    }
}
