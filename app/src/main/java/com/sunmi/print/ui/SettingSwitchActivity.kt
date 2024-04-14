package com.sunmi.print.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.print.databinding.ActivitySettingSwitchBinding
import com.sunmi.print.utils.MMKVUtil

class SettingSwitchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingSwitchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingSwitchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.onClick { finish() }
        binding.scBindSwitch.isChecked =
            MMKVUtil.getInstance().getBooleanValue("IS_OPEN_BIND", true)
        MMKVUtil.getInstance().setValue("SWITCH_A_SUCC_COUNT_REAL", 0)
        MMKVUtil.getInstance().setValue("SWITCH_A_FAIL_COUNT_REAL", 0)
        MMKVUtil.getInstance().setValue("SWITCH_B_COUNT_REAL", 0)
//        binding.etSwitch1.setText(MMKVUtil.getInstance().getIntValue("SWITCH_1_COUNT", 0).toString())
        binding.etSwitchSuccACount.setText(
            MMKVUtil.getInstance().getIntValue("SWITCH_SUCC_A_COUNT", 0).toString()
        )
        binding.etSwitchFailACount.setText(
            MMKVUtil.getInstance().getIntValue("SWITCH_FAIL_A_COUNT", 0).toString()
        )
        binding.etSwitchBCount.setText(
            MMKVUtil.getInstance().getIntValue("SWITCH_B_COUNT", 0).toString()
        )
//        binding.scSwitch1.isChecked = MMKVUtil.getInstance().getBooleanValue("SWITCH_1", false)
        binding.scSwitchA.isChecked = MMKVUtil.getInstance().getBooleanValue("SWITCH_A", false)
        binding.scSwitchB.isChecked = MMKVUtil.getInstance().getBooleanValue("SWITCH_B", false)
//        binding.scSwitch4.isChecked = MMKVUtil.getInstance().getBooleanValue("SWITCH_4", false)
//        binding.scSwitch5.isChecked = MMKVUtil.getInstance().getBooleanValue("SWITCH_5", false)
//        binding.scBindSwitch.setOnCheckedChangeListener { compoundButton, b ->
//            MMKVUtil.getInstance().setValue("IS_OPEN_BIND", b)
//        }
        binding.tvOk.onClick {
//            var count = 0
//            if (binding.scSwitch1.isChecked) {
//                count++
//            }
//            if (binding.scSwitcha.isChecked) {
//                count++
//            }
//            if (binding.scSwitch4.isChecked) {
//                count++
//            }
//            if (binding.scSwitch5.isChecked) {
//                count++
//            }
//            if (count > 1) {
//                Toast.makeText(this, "1,2,4,5只能开一个", Toast.LENGTH_SHORT).show()
//                return@onClick
//            }
            if (TextUtils.isEmpty(binding.etSwitchSuccACount.text.toString()) ||
                TextUtils.isEmpty(binding.etSwitchFailACount.text.toString()) ||
                TextUtils.isEmpty(binding.etSwitchBCount.text.toString())
            ) {
                Toast.makeText(this, "次数填入不能为空", Toast.LENGTH_SHORT).show()
                return@onClick
            }
//            MMKVUtil.getInstance().setValue("SWITCH_1", binding.scSwitch1.isChecked)
            MMKVUtil.getInstance().setValue("SWITCH_A", binding.scSwitchA.isChecked)
            MMKVUtil.getInstance().setValue("SWITCH_B", binding.scSwitchB.isChecked)
//            MMKVUtil.getInstance().setValue("SWITCH_4", binding.scSwitch4.isChecked)
//            MMKVUtil.getInstance().setValue("SWITCH_5", binding.scSwitch5.isChecked)
            MMKVUtil.getInstance().setValue("IS_OPEN_BIND", binding.scBindSwitch.isChecked)
            MMKVUtil.getInstance()
                .setValue("SWITCH_SUCC_A_COUNT", binding.etSwitchSuccACount.text.toString().toInt())
            MMKVUtil.getInstance()
                .setValue("SWITCH_FAIL_A_COUNT", binding.etSwitchFailACount.text.toString().toInt())
            MMKVUtil.getInstance()
                .setValue("SWITCH_B_COUNT", binding.etSwitchBCount.text.toString().toInt())
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.etAccount.setText(MMKVUtil.getInstance().getStringValue("_ACCOUNT", "user0908"))
        binding.etPwd.setText(MMKVUtil.getInstance().getStringValue("_PASSWORD", "qwer1234"))

        binding.tvAccountOk.onClick {
            val etAccount = binding.etAccount.text.toString()
            val etPwd = binding.etPwd.text.toString()
            if (etPwd.length <= 5) {
                Toast.makeText(this, "密码至少6位", Toast.LENGTH_SHORT).show()

                return@onClick
            }
            MMKVUtil.getInstance().setValue("_ACCOUNT", etAccount)
            MMKVUtil.getInstance().setValue("_PASSWORD", etPwd)
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
    }
}