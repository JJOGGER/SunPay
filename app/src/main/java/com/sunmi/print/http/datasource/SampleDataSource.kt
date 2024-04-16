package com.sunmi.print.http.datasource

import com.sunmi.print.data.QueryOrderRequest
import com.sunmi.print.http.datasource.BaseDataSource
import com.sunmi.print.sdk.Constants.url
import okhttp3.ResponseBody


object SampleDataSource : BaseDataSource() {
    suspend fun queryOrder(queryOrderRequest: QueryOrderRequest?): ResponseBody? {
        queryOrderRequest ?: return null
        return mService.queryOrder(
            queryOrderRequest.mch_no, queryOrderRequest.app_id, queryOrderRequest.pay_order_id,
            queryOrderRequest.mch_order_no, queryOrderRequest.req_time, queryOrderRequest.version,
            queryOrderRequest.sign, queryOrderRequest.sign_type
        )
    }

}