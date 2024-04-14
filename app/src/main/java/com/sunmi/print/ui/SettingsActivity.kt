package com.sunmi.print.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sunmi.print.data.BindInfo
import com.sunmi.print.databinding.ActivitySettingsBinding
import com.sunmi.print.ui.dialog.GlobalDialogManager
import com.sunmi.print.ui.dialog.PwdSettingDialog
import com.sunmi.print.utils.MMKVUtil
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.SdkException
import com.sunmi.printerx.api.LineApi
import com.sunmi.printerx.enums.Align
import com.sunmi.printerx.style.BaseStyle
import com.sunmi.printerx.style.TextStyle

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBind.onClick {
            val bankAccount = binding.etBankAccount.text.toString()
            if (bankAccount.length < 16) {
                Toast.makeText(this, "Numerical error.", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            binding.tvLoadingHint.text = "Connecting..."
            binding.loading.visibility = View.VISIBLE
            binding.tvBind.postDelayed({
                binding.tvLoadingHint.text = "Please wait..."
                binding.tvBind.postDelayed({
                    binding.tvLoadingHint.text = "Receiving the bank's return data..."
                    binding.tvBind.postDelayed({
                        binding.loading.visibility = View.GONE
                        BindResultActivity.start(
                            this, BindInfo(
                                binding.etName.text.toString(),
                                bankAccount,
                                binding.etBank.text.toString(),
                                binding.etPhoneNumber.text.toString(),
                                binding.etCountry.text.toString(),
                                binding.etSwiftCode.text.toString()
                            )
                        )
                    }, 5000)
                }, 3000)
            }, 2000)
        }
        binding.ivBack.onClick { finish() }

        binding.ivMore.onClick {
            PwdSettingDialog().show(supportFragmentManager, "PwdSettingDialog")
        }

    }


    override fun onDestroy() {
        super.onDestroy()

        PrinterSdk.getInstance().destroy()
    }

}