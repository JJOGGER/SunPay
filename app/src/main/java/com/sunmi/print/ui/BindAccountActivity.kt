package com.sunmi.print.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.print.databinding.ActivityBindAccountBinding
import com.sunmi.print.ui.dialog.GlobalDialogManager
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.PrinterSdk.PrinterListen
import com.sunmi.printerx.SdkException
import com.sunmi.printerx.api.LineApi
import com.sunmi.printerx.enums.Align
import com.sunmi.printerx.style.BaseStyle
import com.sunmi.printerx.style.TextStyle

class BindAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindAccountBinding
    private fun showLoading() {
        GlobalDialogManager.show(supportFragmentManager)
    }

    private fun dismissLoading() {
        GlobalDialogManager.dismiss()
    }

    /**
     * 指定操作的商米打印机设备
     */
    var selectPrinter: PrinterSdk.Printer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBind.onClick {
            startBind()
        }
        binding.ivBack.onClick { finish() }
        initPrinter()

    }

    private fun startBind() {
        showLoading()
        try {
            val api: LineApi? = selectPrinter?.lineApi()
            if (api == null) {
                dismissLoading()
                return
            }
            api.initLine(BaseStyle.getStyle())
            api.addText("Name", TextStyle.getStyle())
            api.printText(
                binding.etName.text.toString(),
                TextStyle.getStyle().setAlign(Align.RIGHT)
            )
            api.addText("Bank account", TextStyle.getStyle())
            api.printText(
                binding.etBankAccount.text.toString(),
                TextStyle.getStyle().setAlign(Align.RIGHT)
            )
            api.addText("Phone number", TextStyle.getStyle())
            api.printText(
                binding.etPhoneNumber.text.toString(),
                TextStyle.getStyle().setAlign(Align.RIGHT)
            )
            api.addText("Country", TextStyle.getStyle())
            api.printText(
                binding.etCountry.text.toString(),
                TextStyle.getStyle().setAlign(Align.RIGHT)
            )
            api.addText("Swift code", TextStyle.getStyle())
            api.printText(
                binding.etSwiftCode.text.toString(),
                TextStyle.getStyle().setAlign(Align.RIGHT)
            )
            api.autoOut()
            dismissLoading()
        } catch (e: SdkException) {
            e.printStackTrace()
            dismissLoading()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        PrinterSdk.getInstance().destroy()
    }

    /**
     * 初始化默认打印机作为待操作的打印设备
     */
    private fun initPrinter() {
        try {
            PrinterSdk.getInstance().getPrinter(this, object : PrinterListen {
                override fun onDefPrinter(printer: PrinterSdk.Printer) {
                    selectPrinter = printer
                }

                override fun onPrinters(printers: List<PrinterSdk.Printer>) {}
            })
        } catch (e: SdkException) {
            e.printStackTrace()
        }
    }
}