package com.sunmi.print.data

import java.io.Serializable

class QueryOrderResponse(val status: Int?, val msg: String?, val data: QueryOrderResponseData?) :
    Serializable {
}

class QueryOrderResponseData(
    val pay_order_id: String?,
    val mch_no: String?,
    val app_Id: String?,
    val mch_order_no: String?,
    val way_code: String?,
    val amount: Int?,
    val currency: String?,
    val state: Int?,
    val client_ip: String?,
    val subject: String?,
    val channel_order_no: String?,
    val body: String?,
    val err_code: String?,
    val err_msg: String?,
    val ext_param: String?,
    val add_time: Int?,
    val success_time: Int?
) : Serializable