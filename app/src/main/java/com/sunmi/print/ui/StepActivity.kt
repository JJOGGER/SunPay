package com.sunmi.print.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.sunmi.print.databinding.ActivityStepNewBinding
import com.sunmi.print.utils.NetTools

class StepActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStepNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStepNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.usdContainer.onClick {
            binding.usdContainer.isSelected = true
            binding.tvUsd.isSelected = true
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.eurContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = true
            binding.tvEur.isSelected = true
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.hkdContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = true
            binding.tvHkd.isSelected = true
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.gbpContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = true
            binding.tvGbp.isSelected = true
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.jpyContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = true
            binding.tvJpy.isSelected = true
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.audContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = true
            binding.tvAud.isSelected = true
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.myrContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = true
            binding.tvMyr.isSelected = true
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.sarContainer.onClick {

            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = true
            binding.tvSar.isSelected = true
            binding.qarContainer.isSelected = false
            binding.tvQar.isSelected = false
        }
        binding.qarContainer.onClick {
            binding.usdContainer.isSelected = false
            binding.tvUsd.isSelected = false
            binding.eurContainer.isSelected = false
            binding.tvEur.isSelected = false
            binding.hkdContainer.isSelected = false
            binding.tvHkd.isSelected = false
            binding.gbpContainer.isSelected = false
            binding.tvGbp.isSelected = false
            binding.jpyContainer.isSelected = false
            binding.tvJpy.isSelected = false
            binding.audContainer.isSelected = false
            binding.tvAud.isSelected = false
            binding.myrContainer.isSelected = false
            binding.tvMyr.isSelected = false
            binding.sarContainer.isSelected = false
            binding.tvSar.isSelected = false
            binding.qarContainer.isSelected = true
            binding.tvQar.isSelected = true
        }
        binding.tvPay.setOnClickListener {
            if (!NetTools.canNetworking(this)){
                Toast.makeText(this, "Please connect to the network.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            startActivity(Intent(this, SuccessActivity::class.java))
            if (!binding.usdContainer.isSelected && !binding.eurContainer.isSelected &&
                !binding.hkdContainer.isSelected && !binding.gbpContainer.isSelected &&
                !binding.jpyContainer.isSelected && !binding.audContainer.isSelected &&
                !binding.myrContainer.isSelected && !binding.sarContainer.isSelected &&
                !binding.qarContainer.isSelected
            ) {
                Toast.makeText(this, "Please select legal tender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.etAmount.text.toString())) {
                Toast.makeText(this, "Please enter the amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            DataUtil.transaction = when {
                binding.usdContainer.isSelected -> "USD"
                binding.eurContainer.isSelected -> "EUR"
                binding.hkdContainer.isSelected -> "HKD"
                binding.gbpContainer.isSelected -> "GBP"
                binding.jpyContainer.isSelected -> "JPY"
                binding.audContainer.isSelected -> "AUD"
                binding.myrContainer.isSelected -> "MYR"
                binding.sarContainer.isSelected -> "SAR"
                binding.qarContainer.isSelected -> "QAR"
                else -> ""
            }
            DataUtil.amountStr = binding.etAmount.text.toString()
            binding.loading.visibility = View.VISIBLE
            binding.tvLoadingHint.text = "Connecting..."
            binding.tvPay.postDelayed({
                binding.tvLoadingHint.text = "Please wait..."
                binding.tvPay.postDelayed({
                    binding.tvLoadingHint.text = "Receiving the bank's return data..."
                    binding.tvPay.postDelayed({
                        binding.loading.visibility = View.GONE
                        startActivity(Intent(this, SuccessActivity::class.java))
                    }, 5000)
                }, 3000)
            }, 2000)
        }
        binding.ivBack.onClick {
            finish()
        }
    }

}