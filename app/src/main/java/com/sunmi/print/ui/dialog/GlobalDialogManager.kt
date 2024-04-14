package com.sunmi.print.ui.dialog

import androidx.fragment.app.FragmentManager

object GlobalDialogManager {
    private var mLoadingDialog: IOSLoadingDialog? = null
    private var mIsShow = false //是否显示

    init {
        init()
    }

    private fun init() {
        if (mLoadingDialog == null) {
            mLoadingDialog = IOSLoadingDialog()
        }
    }

    /**
     * 展示加载框
     */
    fun show(manager: FragmentManager?) {
        if (manager != null && mLoadingDialog != null && !mIsShow) {
            mLoadingDialog?.showAllowingStateLoss(manager, "loadingDialog")
            mIsShow = true
        }
    }

    /**
     * 隐藏加载框
     */
    fun dismiss() {
        if (mLoadingDialog != null && mIsShow) {
            mLoadingDialog!!.dismissAllowingStateLoss()
            mIsShow = false
        }
    }
}