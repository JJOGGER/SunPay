package com.sunmi.print.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.RemoteException
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.print.R
import com.sunmi.print.data.BindInfo
import com.sunmi.print.databinding.ActivityBindResultBinding
import com.sunmi.print.ui.DataUtil.amountStr
import com.sunmi.print.utils.GsonUtil
import com.sunmi.print.utils.MMKVUtil
import com.sunmi.print.utils.PicUtils
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.PrinterSdk.PrinterListen
import com.sunmi.printerx.SdkException
import com.sunmi.printerx.api.LineApi
import com.sunmi.printerx.enums.Align
import com.sunmi.printerx.enums.ImageAlgorithm
import com.sunmi.printerx.style.BaseStyle
import com.sunmi.printerx.style.BitmapStyle
import com.sunmi.printerx.style.TextStyle
import com.xzy.pos.sdk.print.PrinterManager
import java.io.IOException

class BindResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBindResultBinding

    companion object {
        fun start(context: Context?, bindInfo: BindInfo?) {
            context?.startActivity(Intent(context, BindResultActivity::class.java).apply {
                putExtra("bindinfo", bindInfo)
            })
        }
    }

    /**
     * 指定操作的商米打印机设备
     */
    var selectPrinter: PrinterSdk.Printer? = null
    private var mBindInfo: BindInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.onClick { finish() }
        mBindInfo = intent.getSerializableExtra("bindinfo") as BindInfo?
        val success = MMKVUtil.getInstance().getBooleanValue("IS_OPEN_BIND", true)
        if (success) {
            binding.succContainer.visibility = View.VISIBLE
            MMKVUtil.getInstance().setValue("bindinfo", GsonUtil.getGson().toJson(mBindInfo))
            initPrinter()
        } else {
            binding.failContainer.visibility = View.VISIBLE
        }
    }

    private fun startBind() {
        Thread {
            try {
                val serialPrinterManager = PrinterManager()
                serialPrinterManager.cleanCache()
                val option: BitmapFactory.Options = BitmapFactory.Options().apply {
                    inScaled = false
                }
                val bitmap =
                    BitmapFactory.decodeResource(resources, R.mipmap.logo_print2, option)
                if (bitmap != null) serialPrinterManager.addBitmap(
                    PicUtils.getBytes(bitmap),
                    PrinterManager.CENTER,
                    0
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLine(
                    "PT MAGNADANA INVESTAMA",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLine(
                    "JI.MH. Thamrin No. 12 Jakarta",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLines(
                    arrayOf(
                        "Name",
                        mBindInfo?.name
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "Bank",
                        mBindInfo?.bank
                    ),
                    intArrayOf(1,2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "Account",
                        mBindInfo?.bankAccount
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "Phone",
                        mBindInfo?.phoneNumber
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "Country",
                        mBindInfo?.country
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "Swift code",
                        mBindInfo?.swiftCode
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLine("\n")
                serialPrinterManager.beginPrint()
            } catch (e: RemoteException) {
                e.printStackTrace()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

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
                    startBind()
                }

                override fun onPrinters(printers: List<PrinterSdk.Printer>) {}
            })
        } catch (e: SdkException) {
            e.printStackTrace()
        }
    }
}