package com.sunmi.print.http.service

import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @POST("/query")
    suspend fun queryOrder(@Path("mch_no") mch_no:String,
                           @Path("app_id") app_id:String,
                           @Path("pay_order_id") pay_order_id:String,
                           @Path("mch_order_no") mch_order_no:String,
                           @Path("req_time") req_time:Long,
                           @Path("version") version:String,
                           @Path("sign") sign:String,
                           @Path("sign_type") sign_type:String): ResponseBody?

}