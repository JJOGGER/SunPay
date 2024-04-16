package com.sunmi.print.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.sunmi.print.data.QueryOrderRequest
import com.sunmi.print.data.QueryOrderResponse
import com.sunmi.print.databinding.ActivitiyReceiveMoneyBinding
import com.sunmi.print.internals.taskLaunch
import com.sunmi.print.sdk.CommonConstants
import com.sunmi.print.sdk.Constants
import com.sunmi.print.sdk.LSPayResponse
import com.sunmi.print.sdk.Sign
import com.sunmi.print.ui.dialog.GlobalDialogManager
import com.sunmi.print.utils.GsonUtil
import com.sunmi.print.utils.showToast
import com.tamsiree.rxfeature.scaner.OnRxScanerListener
import com.tamsiree.rxkit.RxActivityTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.TreeMap

class ReceiveMoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitiyReceiveMoneyBinding
    private var mPayType = "WX_BAR"
    private var pollingJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitiyReceiveMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvReceive.onClick {
            ScanerCodeActivity.setScanerListener(object : OnRxScanerListener {
                override fun onFail(type: String?, message: String?) {
                }

                override fun onSuccess(type: String?, result: Result?) {
                    requestPay(result?.text)
                }

            })
            RxActivityTool.skipActivity(this, ScanerCodeActivity::class.java)
        }
    }

    private fun requestPay(text: String?) {
        text ?: return

        val money = try {
            binding.etMoney.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            null
        }
        if (money == null) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show()
            return
        }
        binding.etMoney.post {
            GlobalDialogManager.show(supportFragmentManager)
        }
        taskLaunch {
            val client = OkHttpClient()
            val mediaType: MediaType =
                "application/x-www-form-urlencoded".toMediaType() //设置请求类型
            val body: MutableMap<String?, Any?> = TreeMap { s, t1 ->
                s?.compareTo(t1!!) ?: 0 //按照key值升序
            }
            body["mch_no"] = CommonConstants.MCH_NO //商户号
            body["app_id"] = CommonConstants.APP_ID //应用ID 微信支付宝为同一id 银联支付是另一个id
            body["mch_order_no"] = System.currentTimeMillis() //商户生成的订单号
            body["way_code"] = mPayType //支付方式
            body["amount"] = (money * 100).toInt() //支付金额 单位分
            body["currency"] = "cny" //三位货币代码,人民币
            body["subject"] = "商品标题" //商品标题
            body["body"] = "商品描述" //商品描述
            body["req_time"] = System.currentTimeMillis() //请求接口时间,13位时间戳
            body["version"] = "1.0" //接口版本号，固定：1.0
            body["sign_type"] = "MD5" //签名类型，目前只支持MD5方式
            body["channel_extra"] = "{\"auth_code\":${text}}"
            body["sign"] = Sign.sign(
                body,
                "HSu2gBBNLnyQTH575nnxYCg6HIGnhyeBR6JfVMLxF6O5uCGIlTbrSwzqnbrIDORTaLuoVNxRtgeBoI8oSZ6BExJvHSHeQiuXXTNHEfKPHh4bqv86BdziPb2FUDubDoEM"
            ) //签名值
            //拼接请求体
            val bodyStr = StringBuilder()
            val keySet: Set<String?> = body.keys
            for (key in keySet) {
                val value = body[key]
                bodyStr.append(key).append("=").append(value).append("&")
            }
            bodyStr.deleteCharAt(bodyStr.length - 1) //amount=1&app_id=330794791220543488&body=商品描述&currency=cny&mch_no=1680160079&mch_order_no=1693741916985&req_time=1693741916985&sign=3B6231BF9ADD9B676CD1B63C8A1983FF&sign_type=MD5&subject=商品标题&version=1.0&way_code=WX_LITE_H5
            val requestBody: RequestBody = RequestBody.create(mediaType, bodyStr.toString())
            val request: Request = Request.Builder().url(Constants.url).post(requestBody).build()
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful && response.body != null) {
                    val data: String? = response.body?.string()
                    println("----------------->" + data)
                    when (mPayType) {
                        "WX_BAR" -> {
                            val lsPayResponse =
                                try {
                                    GsonUtil.getGson().fromJson(
                                        data,
                                        LSPayResponse::class.java
                                    )
                            }catch (e:Exception){
                                LSPayResponse()
                            }

                            if (lsPayResponse.status == 200) {
                                val orderState = lsPayResponse.data.order_state
                                if (orderState == 1) {
                                    //轮询
                                    startPolling(
                                        lsPayResponse.data.pay_order_id,
                                        lsPayResponse.data.mch_order_no
                                    )

                                } else if (orderState == 2) {

                                    withContext(Dispatchers.Main) {
                                        GlobalDialogManager.dismiss()
                                    }
                                } else {

                                    withContext(Dispatchers.Main) {
                                        GlobalDialogManager.dismiss()
                                    }
                                }
                            }else{
                                withContext(Dispatchers.Main){
                                    showToast(lsPayResponse.msg)
                                    GlobalDialogManager.dismiss()
                                }
                            }
                        }
                    }

//                            when (payType) {
//                                PayType.WX_LITE_H5 -> {
//                                    //服务器返回数据
//                                    println("服务器返回数据 $data")
//                                    //{"status":200,"msg":"成功","data":{"mch_order_no":"1693812544874","pay_order_id":"8430025781289156608","amount":1,"order_state":0,"pay_info":{"gh_id":"gh_99c0536eb852","path":"pages\/cashier\/cashier","scheme_code":"weixin:\/\/dl\/business\/?t=iYZ8wUz0Vhs"},"trans_status":"S"}}
//
//                                    //根据服务器返回调用微信sdk
//                                    val lsPayResponse = gson.fromJson(
//                                        data,
//                                        LSPayResponse::class.java
//                                    )
//                                    val respData = lsPayResponse.data
//                                    val userName = respData.pay_info.gh_id
//                                    //拼接地址 s=app 固定写死
//                                    val path =
//                                        respData.pay_info.path + "?pay_order_id=" + respData.pay_order_id + "&s=app"
//                                    val req: WXLaunchMiniProgram.Req = Req()
//                                    req.userName = userName
//                                    req.path = path
//                                    wxapi.sendReq(req)
//                                }
//
//                                PayType.ALI_LITE_H5 ->                                 //服务器返回数据
//                                    println("服务器返回数据 $data")
//
//                                PayType.BANK_FAST_JUMP ->                                 //服务器返回数据
//                                    println("服务器返回数据 $data")
//                            }
                }else{
                    withContext(Dispatchers.Main){
                        showToast("请求失败")
                        GlobalDialogManager.dismiss()
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main){
                    showToast("请求失败")
                    GlobalDialogManager.dismiss()
                }
            }
        }

    }

    private suspend fun startPolling(payOrderId: String, mchOrderNo: String) {
        //如果正在轮询，则不需要重复处理
        if (pollingJob?.isActive == true) {
            return
        }
        pollingJob = taskLaunch {
            while (pollingJob?.isActive == true) {
                delay(1500)
                queryOrders(payOrderId, mchOrderNo)
            }
        }
    }

    private fun cancelPollingJob() {
        pollingJob?.cancel()
        pollingJob = null
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelPollingJob()
    }

    private suspend fun queryOrders(payOrderId: String, mchOrderNo: String) {
        val client = OkHttpClient()
        val mediaType: MediaType =
            "application/x-www-form-urlencoded".toMediaType() //设置请求类型
        val body: MutableMap<String?, Any?> = TreeMap { s, t1 ->
            s?.compareTo(t1!!) ?: 0 //按照key值升序
        }
        body["mch_no"] = CommonConstants.MCH_NO //商户号
        body["app_id"] = CommonConstants.APP_ID //应用ID 微信支付宝为同一id 银联支付是另一个id
        body["pay_order_id"] = payOrderId //商户生成的订单号
        body["mch_order_no"] = mchOrderNo //商户生成的订单号
        body["req_time"] = System.currentTimeMillis() //请求接口时间,13位时间戳
        body["version"] = "1.0" //接口版本号，固定：1.0
        body["sign_type"] = "MD5" //签名类型，目前只支持MD5方式
        body["sign"] = Sign.sign(
            body,
            "HSu2gBBNLnyQTH575nnxYCg6HIGnhyeBR6JfVMLxF6O5uCGIlTbrSwzqnbrIDORTaLuoVNxRtgeBoI8oSZ6BExJvHSHeQiuXXTNHEfKPHh4bqv86BdziPb2FUDubDoEM"
        ) //签名值
        //拼接请求体
        val bodyStr = StringBuilder()
        val keySet: Set<String?> = body.keys
        for (key in keySet) {
            val value = body[key]
            bodyStr.append(key).append("=").append(value).append("&")
        }
        bodyStr.deleteCharAt(bodyStr.length - 1) //amount=1&app_id=330794791220543488&body=商品描述&currency=cny&mch_no=1680160079&mch_order_no=1693741916985&req_time=1693741916985&sign=3B6231BF9ADD9B676CD1B63C8A1983FF&sign_type=MD5&subject=商品标题&version=1.0&way_code=WX_LITE_H5
        val requestBody: RequestBody = RequestBody.create(mediaType, bodyStr.toString())
        val request: Request = Request.Builder().url(Constants.query).post(requestBody).build()
        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                val data: String? = response.body?.string()
                val queryOrderResponse = GsonUtil.getGson().fromJson(
                    data,
                    QueryOrderResponse::class.java
                )
                if (queryOrderResponse.status == 200) {
                    if (queryOrderResponse.data?.state != 1) {
                        if (queryOrderResponse.data?.state == 2) {
                            withContext(Dispatchers.Main) {
                                GlobalDialogManager.dismiss()
                                showToast("完成收款")
                            }
                        }
                        cancelPollingJob()
                    }
                } else {
                    cancelPollingJob()
                    withContext(Dispatchers.Main) {
                        GlobalDialogManager.dismiss()
                        showToast("网络请求失败")
                    }
                }
                println("------>" + data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}