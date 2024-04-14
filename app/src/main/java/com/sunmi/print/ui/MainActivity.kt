package com.sunmi.print.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.sunmi.print.R
import com.sunmi.print.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mTextWatcher1: TextWatcher? = null
    private var mTextWatcher2: TextWatcher? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val etNumber = findViewById<EditText>(R.id.et_number)
        val etDate = findViewById<EditText>(R.id.et_date)
        val etCvv = findViewById<EditText>(R.id.et_cvv)
        binding.ivBack.onClick { finish() }
        mTextWatcher1 = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // 移除之前添加的空格，以免重复
                etNumber.removeTextChangedListener(mTextWatcher1)

                // 获取当前文本
                val text = s.toString()

                // 移除所有空格
                val formattedText = text.replace("\\s".toRegex(), "")
                if (formattedText.startsWith("4") || formattedText.startsWith("5")
                    || formattedText.startsWith("35")
                    || formattedText.startsWith("34") || formattedText.startsWith("37")
                ) {
                    binding.flCardType.visibility = View.VISIBLE
                    if (formattedText.startsWith("4")) {
                        binding.ivCardTypevisa.visibility = View.VISIBLE
                        binding.ivCardTypejcb.visibility = View.GONE
                        binding.ivCardTypeqq.visibility = View.GONE
                        binding.ivCardTypeqrc.visibility = View.GONE
                    } else if (formattedText.startsWith("5")) {
                        binding.ivCardTypevisa.visibility = View.GONE
                        binding.ivCardTypejcb.visibility = View.GONE
                        binding.ivCardTypeqq.visibility = View.VISIBLE
                        binding.ivCardTypeqrc.visibility = View.GONE
                    } else if (formattedText.startsWith("34") || formattedText.startsWith("35")) {
                        binding.ivCardTypevisa.visibility = View.GONE
                        binding.ivCardTypejcb.visibility = View.VISIBLE
                        binding.ivCardTypeqq.visibility = View.GONE
                        binding.ivCardTypeqrc.visibility = View.GONE
                    } else if (formattedText.startsWith("37")) {
                        binding.ivCardTypevisa.visibility = View.GONE
                        binding.ivCardTypejcb.visibility = View.GONE
                        binding.ivCardTypeqq.visibility = View.GONE
                        binding.ivCardTypeqrc.visibility = View.VISIBLE
                    }
                } else {
                    binding.flCardType.visibility = View.INVISIBLE
                }
                // 插入空格
                val sb = StringBuilder()
                for (i in formattedText.indices) {
                    sb.append(formattedText[i])
                    if ((i + 1) % 4 == 0 && i != formattedText.length - 1) {
                        sb.append(" ")
                    }
                }

                // 更新文本
                etNumber.setText(sb.toString())

                // 将光标移动到最后
                etNumber.setSelection(sb.length)

                // 重新添加监听器
                etNumber.addTextChangedListener(mTextWatcher1)
            }

        }
        etNumber.addTextChangedListener(mTextWatcher1)

        mTextWatcher2 = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                // 移除之前添加的空格，以免重复
                etDate.removeTextChangedListener(mTextWatcher2)

                // 获取当前文本
                val text = s.toString()

                // 移除所有空格
                val formattedText = text.replace("/", "")

                val sb = StringBuilder()
                for (i in formattedText.indices) {
                    sb.append(formattedText[i])
                    if (i == 1) {
                        sb.append("/")
                    }
                }

                // 更新文本
                etDate.setText(sb.toString())

                // 将光标移动到最后
                etDate.setSelection(sb.length)

                // 重新添加监听器
                etDate.addTextChangedListener(mTextWatcher2)
            }

        }
        etDate.addTextChangedListener(mTextWatcher2)

        findViewById<View>(R.id.tv_next).onClick {
            val numberStr = etNumber.text.toString()
            val dateStr = etDate.text.toString()
            val cvvStr = etCvv.text.toString()
            if (TextUtils.isEmpty(numberStr)) {
                Toast.makeText(this, "Please enter the card number", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(dateStr)) {
                Toast.makeText(this, "Please enter the expiry date", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(cvvStr)) {
                Toast.makeText(this, "Please enter the CVV/CVV2", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (binding.ivCardTypejcb.visibility == View.VISIBLE) {
                DataUtil.amountType = "JCB"
            } else if (binding.ivCardTypeqq.visibility == View.VISIBLE) {
                DataUtil.amountType = "MASTERCARD"
            } else if (binding.ivCardTypeqrc.visibility == View.VISIBLE) {
                DataUtil.amountType = "AMERICAN EXPRESS"
            } else if (binding.ivCardTypevisa.visibility == View.VISIBLE) {
                DataUtil.amountType = "VISA"
            }
            DataUtil.cardNumber = numberStr
            DataUtil.dateStr = dateStr
            DataUtil.cvvStr = cvvStr
            startActivity(Intent(this, StepActivity::class.java))
        }
    }

}