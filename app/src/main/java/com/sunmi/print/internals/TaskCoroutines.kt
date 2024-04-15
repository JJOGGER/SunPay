package com.sunmi.print.internals

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
// 业务线程池
private var sBusinessPolicy: IPolicy? = null
// 用于网络请求的线程
 val ThreadPool_BUSSINESS = getBusinessPolicy().executor?.asCoroutineDispatcher()!!
fun getBusinessPolicy(): IPolicy {
    if (null == sBusinessPolicy) {
        synchronized(Any()) {
            if (sBusinessPolicy == null) {
                sBusinessPolicy =
                    BusinessPolicy()
            }
        }
    }
    return sBusinessPolicy!!
}

/**
 * 在主线程中顺序执行，属于顶级协程函数，一般用于最外层
 *
 * 注意：该函数会阻塞代码继续执行
 */
inline fun taskBlockOnMainThread(delayTime: Long = 0, noinline job: suspend () -> Unit) = runBlocking {
    delay(delayTime)
    job()
}


/**
 * 并发执行，常用于最外层
 * 特点带返回值
 */
inline fun <T> taskAsync(context: CoroutineContext = ThreadPool_BUSSINESS, delayTime: Long = 0, noinline job: suspend () -> T) = CoroutineScope(context).async {
    delay(delayTime)
    job()
}

/**
 * 并发执行，常用于最外层
 * 特点不带返回值
 */
inline fun <T> taskLaunch(context: CoroutineContext = ThreadPool_BUSSINESS, delayTime: Long = 0, noinline job: suspend () -> T) = CoroutineScope(
        context).launch {
    try {

        delay(delayTime)
        job()
    }catch (e:Exception){
        e.printStackTrace()
    }
}

/**
 * 在Android UI线程中执行，可以用于最外层
 * 此方法用于协程上下文调度，目前主要用于切换到android UI线程
 * 参数添加CoroutineStart.UNDISPATCHED的话表示立即执行
 */
inline fun <T> taskRunOnUiThread(delayTime: Long = 0, noinline job: suspend () -> T) = CoroutineScope(Dispatchers.Main).launch {
    delay(delayTime)
    job()
}

/**
 * 顺序执行函数，不能用于最外层
 */
suspend inline fun <T> taskOrder(delayTime: Long = 0, crossinline job: () -> T) {
    delay(delayTime)
    job()
}

/**
 * 心跳执行 默认重复次数1次，不能用于最外层
 */
suspend inline fun <T> taskHeartbeat(times: Int = 1, delayTime: Long = 0, crossinline job: () -> T) = repeat(times) {
    delay(delayTime)
    job()
}

