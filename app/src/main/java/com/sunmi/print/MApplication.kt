package com.sunmi.print

import android.app.Application
import com.tamsiree.rxkit.RxTool
import com.tencent.mmkv.MMKV

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxTool.init(this)
        MMKV.initialize(this)
    }
}