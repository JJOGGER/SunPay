package com.sunmi.print.internals

import java.util.concurrent.ThreadPoolExecutor

interface IPolicy {
    val executor: ThreadPoolExecutor?
    val corePollSize: Int
    val keepAliveTime: Int

    fun canExecuteAtOnce(): Boolean
}