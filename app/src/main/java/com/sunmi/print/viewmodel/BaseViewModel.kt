package com.sunmi.print.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunmi.print.internals.BusinessPolicy
import com.sunmi.print.internals.IPolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(), LifecycleObserver {
    // 业务线程池
    private var sBusinessPolicy: IPolicy? = null

    // 用于网络请求的线程
    private val ThreadPool_BUSINESS = getBusinessPolicy().executor?.asCoroutineDispatcher()
    fun getBusinessPolicy(): IPolicy {
        if (null == sBusinessPolicy) {
            synchronized(this) {
                if (sBusinessPolicy == null) {
                    sBusinessPolicy =
                        BusinessPolicy()
                }
            }
        }
        return sBusinessPolicy!!
    }
    /**
     * 发起一个api请求，block块会阻塞线程（会在非主线程中运行）
     */
    fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        if (ThreadPool_BUSINESS != null) {
            viewModelScope.launch(ThreadPool_BUSINESS) {
                block()
            }
        }
    }


    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }
}