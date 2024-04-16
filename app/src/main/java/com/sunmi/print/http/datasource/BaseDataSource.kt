package com.sunmi.print.http.datasource

import com.sunmi.print.http.basic.RetrofitManager
import com.sunmi.print.http.service.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody

open class BaseDataSource {
    val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
    fun getRequestBody(json: String): RequestBody {
        return RequestBody.create("application/x-www-form-urlencoded".toMediaType(), json)
    }
}