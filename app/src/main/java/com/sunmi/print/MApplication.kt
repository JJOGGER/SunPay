package com.sunmi.print

import android.app.Application
import com.tamsiree.rxkit.RxTool
import com.tencent.mmkv.MMKV

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        app = this
        MMKV.initialize(this)
    }
    companion object {
        private var app: Application? = null
        fun getApplication(): Application {
            return app!!
        }
    }
}