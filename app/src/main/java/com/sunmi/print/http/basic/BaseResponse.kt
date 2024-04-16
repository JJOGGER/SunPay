package com.sunmi.print.http.basic

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
class BaseResponse<T> {
    var code: Int = 0

    var message: String? = null

    var data: T? = null

    override fun toString(): String {
        return "BaseResponse{" +
            "code=" + code +
            ", message='" + message + '\''.toString() +
            ", data=" + data +
            '}'.toString()
    }

    fun isSuccess(): Boolean {
        return code == 200
    }
}