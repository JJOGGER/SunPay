package com.sunmi.print.data

import com.sunmi.print.sdk.CommonConstants
import retrofit2.http.Path

data class QueryOrderRequest(
    val mch_no: String = CommonConstants.MCH_NO,
    val app_id: String = CommonConstants.APP_ID,
    val pay_order_id: String,
    val mch_order_no: String,
    val req_time: Long = System.currentTimeMillis(),
    val version: String="1.0",
    val sign: String,
    val sign_type: String="MD5"
) {
}