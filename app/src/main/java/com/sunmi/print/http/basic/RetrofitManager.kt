package com.sunmi.print.http.basic

import com.sunmi.print.http.basic.config.getBaseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
object RetrofitManager {
    private const val READ_TIMEOUT: Long = 10000
    private const val WRITE_TIMEOUT: Long = 10000
    private const val CONNECT_TIMEOUT: Long = 10000
    private var mServiceMap: ConcurrentHashMap<String, Any> = ConcurrentHashMap()


    fun <T> getService(clz: Class<T>): T {
        return getService(clz, getBaseUrl())
    }

    fun <T> getService(clz: Class<T>, host: String): T {
        val value: T
        if (mServiceMap.containsKey(host)) {
            val obj = mServiceMap.get(host)
            if (obj == null) {
                //没有对应service，则创建
                value = createRetrofit(host).create(clz)
                mServiceMap.put(host, value!!)
            } else {
                value = obj as T
            }
        } else {
            value = createRetrofit(host).create(clz)
            mServiceMap.put(host, value!!)
        }
        return value
    }

    private fun createRetrofit(url: String): Retrofit {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .sslSocketFactory(
                HttpsFactroy.getSSLSocketFactory(),
                HttpsFactroy.creatX509TrustManager()
            ).hostnameVerifier(HttpsFactroy.creatSkipHostnameVerifier())
        val client: OkHttpClient
//        try {
//            val sslContext = SSLContext.getInstance("SSL")
//            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
//            client = builder.sslSocketFactory(sslContext.socketFactory)
//                .hostnameVerifier { hostname, session -> true }.build()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            client = builder.build()
//        }
        client = builder.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

