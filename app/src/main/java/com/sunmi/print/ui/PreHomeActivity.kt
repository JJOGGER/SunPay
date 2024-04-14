package com.sunmi.print.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.print.data.BindInfo
import com.sunmi.print.databinding.ActivityPreHomeBinding
import com.sunmi.print.ui.login.LoginActivity
import com.sunmi.print.utils.GsonUtil
import com.sunmi.print.utils.MMKVUtil
import java.util.regex.Pattern

class PreHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvPayment.onClick {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.tvSetting.onClick {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.tvExit.onClick {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        bindInfo()

    }

    private fun bindInfo() {
        val bindinfoJson = MMKVUtil.getInstance().getStringValue("bindinfo", "")
        val bindinfo = GsonUtil.getGson().fromJson(bindinfoJson, BindInfo::class.java)
        if (bindinfo != null) {
            binding.tvAccount.text = "Account:" + hideDigitsExceptLastFour(bindinfo.bankAccount)
            binding.tvBankNumber.text = "Bank/Mechanism:" + (bindinfo.bank ?: "")
        }
    }

    private fun hideDigitsExceptLastFour(inputStr: String?): String {
        inputStr ?: return ""
        // 使用正则表达式匹配是否包含小写字母
        val containsLowerCase: Boolean = Pattern.compile("[a-z]").matcher(inputStr).find()
        // 使用正则表达式匹配是否包含大写字母
        val containsUpperCase: Boolean = Pattern.compile("[A-Z]").matcher(inputStr).find()

        val hiddenLength: Int
        if (containsLowerCase || containsUpperCase) {
            hiddenLength = 6
        } else {
            hiddenLength = 4
        }
        try {
            val prefix = inputStr.substring(0, hiddenLength)
            val suffix = inputStr.substring(inputStr.length - hiddenLength)
            val middle = "********" // 8个*
            return "$prefix$middle$suffix"
        }catch (e:Exception){
            return ""
        }
    }

    override fun onResume() {
        super.onResume()
        bindInfo()
    }

}