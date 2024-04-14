package com.sunmi.print.sdk

import java.util.Locale

object Sign {
    fun sign(map: Map<String?, Any?>, key: String?): String? {
        val sb = StringBuilder()
        map.forEach {
            sb.append(it.key).append("=").append(it.value).append("&")
        }
        sb.append("key=").append(key)
        return MD5Utils.encrypt(sb.toString()).uppercase(Locale.getDefault())
    }
}