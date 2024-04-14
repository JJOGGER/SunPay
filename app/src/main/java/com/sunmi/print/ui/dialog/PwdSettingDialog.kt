package com.sunmi.print.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.sunmi.print.R
import com.sunmi.print.ui.SettingSwitchActivity
import com.sunmi.print.ui.onClick

/**
 *    author : jogger
 *    date   : 2024/2/11
 *    desc   :
 */
class PwdSettingDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_pwd_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.tv_ok).onClick {
            val pwd = view.findViewById<EditText>(R.id.et_pwd).text.toString()
            if (pwd == "2024") {
                startActivity(Intent(activity, SettingSwitchActivity::class.java))
            } else {
                Toast.makeText(activity, "password error", Toast.LENGTH_SHORT).show()
            }
            dismissAllowingStateLoss()
        }
    }
}