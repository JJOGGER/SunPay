package com.sunmi.print

import android.app.Application
import com.tencent.mmkv.MMKV

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)
    }
}