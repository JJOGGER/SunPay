package com.sunmi.print.http.basic.config

import com.sunmi.print.http.basic.config.HttpConfig.BASE_URL

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
object HttpConfig {
    lateinit var BASE_URL: String
    lateinit var OSS_URL: String
    fun init() {
        BASE_URL = "https://api.lspay.tech/gateway/"
    }
}

fun getBaseUrl(): String {
    return BASE_URL
}
