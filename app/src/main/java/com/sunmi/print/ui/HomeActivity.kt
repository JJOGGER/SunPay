package com.sunmi.print.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.zxing.Result
import com.sunmi.print.databinding.ActivityHomeBinding
import com.sunmi.print.internals.taskLaunch
import com.sunmi.print.sdk.Constants.url
import com.sunmi.print.sdk.Sign
import com.tamsiree.rxfeature.scaner.OnRxScanerListener
import com.tamsiree.rxkit.RxActivityTool
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.TreeMap

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.flReceiveMoney.onClick {
            startActivity(Intent(this, ReceiveMoneyActivity::class.java))
        }


    }


}