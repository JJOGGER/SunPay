package com.sunmi.print.http.basic

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by jogger on 2020/4/7
 * 描述：
 */
object HttpsFactroy {
    fun getSSLSocketFactory(): SSLSocketFactory {
        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, creatTrustManager(), SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun creatTrustManager(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
    }

    fun creatX509TrustManager(): X509TrustManager {

        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }

    }

    fun creatSkipHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { s, sslSession -> true }
    }
}