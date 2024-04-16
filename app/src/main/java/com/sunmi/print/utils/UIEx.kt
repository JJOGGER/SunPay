package com.sunmi.print.utils

import android.view.View


/**
 * Created by jogger on 2020/2/26
 * 描述：
 */

fun showToast(resId: Int) {
    ToastHelper.showToast(resId)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
fun showToast(text: String) {
    ToastHelper.showToast(text)
}

