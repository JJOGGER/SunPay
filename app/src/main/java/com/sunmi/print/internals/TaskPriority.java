package com.sunmi.print.internals;

/**
 * 任务优先级策略类，提供了一些为任务计算优先级的策略方法，目前只有{@link #computeByLifeLevel}
 *
 * @author lxp 2018.6.20
 */
public class TaskPriority {

    public static final int INVALID = -1;
    /**
     * 最高优先级，一般进入队列就不会被其他任务抢占
     */
    public static final int TOP = 0;

    /**
     * 用户手动操作优先级，能较快反馈给用户，如手动触发下载场景
     */
    public static final int USER = 15;

    /**
     * 调用 with(fragment)创建的异步任务默认优先级
     */
    public static final int HIGH = 25;

    /**
     * 调用 with(activity)创建的异步任务默认优先级
     */
    public static final int MEDIUM = 50;

    /**
     * 所有异步任务默认优先级
     */
    public static final int NORMAL = 75;

    /**
     * 最低优先级
     */
    public static final int LOW = 100;

    public static final int LEVEL_APP = 3;
    public static final int LEVEL_ACTIVITY = 2;
    public static final int LEVEL_FRAGMENT = 1;

    public static int checkPriority(int priority) {
        if (priority < TOP) {
            return TOP;
        }
        if (priority > LOW) {
            return LOW;
        }
        return priority;
    }

    public static int computeByLifeLevel(int level) {
        if (level == LEVEL_ACTIVITY) {
            return MEDIUM;
        } else if (level == LEVEL_FRAGMENT) {
            return HIGH;
        }
        return NORMAL;
    }

}
