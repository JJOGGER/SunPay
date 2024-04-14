package com.sunmi.print.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.RemoteException
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.print.R
import com.sunmi.print.databinding.ActivitySuccessBinding
import com.sunmi.print.ui.dialog.GlobalDialogManager
import com.sunmi.print.utils.MMKVUtil
import com.sunmi.print.utils.PicUtils
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.PrinterSdk.PrinterListen
import com.sunmi.printerx.SdkException
import com.xzy.pos.sdk.print.PrinterManager
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding

    /**
     * 指定操作的商米打印机设备
     */
    var selectPrinter: PrinterSdk.Printer? = null
    var nums = arrayOf("01", "02", "05", "34", "80", "81", "83", "84", "85", "86", "88", "89")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.onClick {
            finish()
        }
        val switchA = MMKVUtil.getInstance().getBooleanValue("SWITCH_A", false)
        val switchB = MMKVUtil.getInstance().getBooleanValue("SWITCH_B", false)
//        val switch4 = MMKVUtil.getInstance().getBooleanValue("SWITCH_4", false)
//        val switch5 = MMKVUtil.getInstance().getBooleanValue("SWITCH_5", false)
        if (switchA) {
            val switchASuccReal = MMKVUtil.getInstance().getIntValue("SWITCH_A_SUCC_COUNT_REAL", 0)
            val switchASuccCount = MMKVUtil.getInstance().getIntValue("SWITCH_SUCC_A_COUNT", 0)
            if (switchASuccReal + 1 > switchASuccCount) {
                val switchAFailReal =
                    MMKVUtil.getInstance().getIntValue("SWITCH_A_FAIL_COUNT_REAL", 0)
                val switchAFailCount = MMKVUtil.getInstance().getIntValue("SWITCH_FAIL_A_COUNT", 0)
                if (switchAFailReal + 1 > switchAFailCount) {
                    MMKVUtil.getInstance().setValue("SWITCH_A_FAIL_COUNT_REAL", 0)
                    MMKVUtil.getInstance().setValue("SWITCH_A", false)
                    handleSwitchB()
                } else {
                    MMKVUtil.getInstance().setValue("SWITCH_A_FAIL_COUNT_REAL", switchAFailReal + 1)
                    errorHandle(1)
                }
            } else {
                MMKVUtil.getInstance().setValue("SWITCH_A_SUCC_COUNT_REAL", switchASuccReal + 1)
                successHandle()
            }
        } else {
            handleSwitchB()
        }

    }

    private fun handleSwitchB() {

        val switchBCountReal = MMKVUtil.getInstance().getIntValue("SWITCH_B_COUNT_REAL", 0)
        val switchBCount = MMKVUtil.getInstance().getIntValue("SWITCH_B_COUNT", 0)
        if (switchBCountReal + 1 > switchBCount) {
//            MMKVUtil.getInstance().setValue("SWITCH_B_COUNT_REAL", 0)
//            MMKVUtil.getInstance().setValue("SWITCH_B", false)
            errorHandle(2)
        } else {
            MMKVUtil.getInstance().setValue("SWITCH_B_COUNT_REAL", switchBCountReal + 1)
            successHandle()
        }

    }

    private fun errorHandle(i: Int) {//2封锁 1拒绝
        binding.tvTitle.text = "Failed"
        binding.succContainer.visibility = View.GONE
        binding.failContainer.visibility = View.VISIBLE
        if (i == 2) {
            binding.tvError2.visibility = View.VISIBLE
        } else {
            binding.tvError1.visibility = View.VISIBLE
            binding.tvError1.text =
                nums[generateRandomIndex()] + "-Failed payment.The card issue bank rejected the transaction."
        }
        binding.tvTryAgain.onClick {
            startActivity(Intent(this, PreHomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
            finish()
        }
    }

    private fun successHandle() {
        binding.succContainer.visibility = View.VISIBLE
        binding.failContainer.visibility = View.GONE
        binding.tvTitle.text = "Payment details"
        binding.tvAmount.text = DataUtil.amountStr
        binding.tvCvv.text = DataUtil.cvvStr
        binding.tvDate.text = DataUtil.dateStr
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US)
        val dateString = dateFormat.format(currentDate)
        binding.tvTime.text = dateString
        binding.tvOrderId.text = generateRandomNumber(18)

        binding.tvCardNumber.text = DataUtil.cardNumber

        binding.tvCompleted.setOnClickListener {
            startActivity(Intent(this, PreHomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
        initPrinter()
    }

    fun generateRandomNumber(count: Int): String {
        val random = java.util.Random()
        val stringBuilder = StringBuilder(count)

        for (i in 0 until count) {
            stringBuilder.append(random.nextInt(10))
        }

        return stringBuilder.toString()
    }

    fun generateRandomIndex(): Int {
        val random = java.util.Random()
        return random.nextInt(12)
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
                    startPrint()
                }

                override fun onPrinters(printers: List<PrinterSdk.Printer>) {}
            })
        } catch (e: SdkException) {
            e.printStackTrace()
        }
    }

    private fun showLoading() {
        GlobalDialogManager.show(supportFragmentManager)
    }

    private fun dismissLoading() {
        GlobalDialogManager.dismiss()
    }

    val wifiManager by lazy {
        applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun isWifiEnabled(): Boolean {
        return wifiManager.isWifiEnabled
    }

    private fun hideDigitsExceptLastFour(inputStr: String): String {
        val length = inputStr.length
        val hiddenLength = length - 4
        val hiddenPart = "*".repeat(hiddenLength)
        val visiblePart = inputStr.takeLast(4)
        return hiddenPart + visiblePart
    }

    private fun getDateStr(): String {
        // 创建日期对象
        val date = Date()

        // 创建日期格式化对象，指定格式为 "MMM dd, yy"
        val sdf = SimpleDateFormat("MMM dd, yy", Locale.US)

        // 格式化日期并输出
        return sdf.format(date)
    }

    private fun getTimeStr(): String {
        // 创建日期对象
        val date = Date()

        // 创建日期格式化对象，指定格式为 "MMM dd, yy"
        val sdf = SimpleDateFormat("HH:mm", Locale.US)

        // 格式化日期并输出
        return sdf.format(date)
    }

    private fun startPrint() {
        showLoading()
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
                serialPrinterManager.addLine("",
                    false,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER)
                serialPrinterManager.addLine(
                    DataUtil.amountType,
                    true,
                    false,
                    false,
                    PrinterManager.FONT_LARGE,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("",
                    false,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER)
                serialPrinterManager.addLine(
                    "BORDERS & RESIDENCE",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_LARGE,
                    PrinterManager.CENTER
                )

                serialPrinterManager.addLine(
                    "AIRPORT ROAD-INSIDE QUEEN ALIA AIRPORT",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLines(
                    arrayOf("TERMINAL ID:", generateRandomNumber(8)),
                    intArrayOf(1, 1),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf("MERCHANT #", generateRandomNumber(15)),
                    intArrayOf(2, 3),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )

                serialPrinterManager.addLines(
                    arrayOf(
                        "${DataUtil.amountType}",
                        " ${hideDigitsExceptLastFour(binding.tvCardNumber.text.toString())}"
                    ),
                    intArrayOf(3, 5),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf("AID: A${generateRandomNumber(13)}", "${DataUtil.amountType}"),
                    intArrayOf(5, 3),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf("SALE", "EXP: ${binding.tvDate.text}"),
                    intArrayOf(1, 1),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "BATCH: ${generateRandomNumber(6)}",
                        "INVOICE: ${generateRandomNumber(6)}"
                    ),
                    intArrayOf(1, 1),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf("DATE: ${getDateStr()}", "TIME: ${getTimeStr()}"),
                    intArrayOf(1, 1),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLines(
                    arrayOf(
                        "RRN: ${generateRandomNumber(10)}",
                        "AUTO NO: ${generateRandomNumber(6)}"
                    ),
                    intArrayOf(10, 9),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_NORMAL, PrinterManager.FONT_NORMAL),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLine("\n")
                val amountStr = if (binding.tvAmount.text.toString().contains(".")) {
                    binding.tvAmount.text
                } else {
                    "${binding.tvAmount.text}.00"
                }
                serialPrinterManager.addLines(
                    arrayOf(
                        "BCA BANK",
                        "${DataUtil.transaction}: $amountStr"
                    ),
                    intArrayOf(1, 2),
                    booleanArrayOf(true, true),
                    booleanArrayOf(false, false),
                    booleanArrayOf(false, false),
                    intArrayOf(PrinterManager.FONT_LARGE, PrinterManager.FONT_LARGE),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.RIGHT)
                )
                serialPrinterManager.addLine("")
                serialPrinterManager.addLine(
                    "(TRANSACTION CURRENCY: ${DataUtil.transaction})",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("")
                serialPrinterManager.addLine(
                    "TOTAL    ${DataUtil.transaction} $amountStr",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_LARGE,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLines(
                    arrayOf(
                        "",
                        "I ACCEPT THAT I HAVE GEEN OFFEREDA CHOICEOF CURRENCTES FOR PAYNEN6THAT THIS CHOTGE IS-FIMADYNATC CURRENCY CONVERSION I5CONDUCTED BY THE MERCHANY AND IS NO!ASSOCIATED WITH OR ENDORSEDBY VISA\n",
                        ""
                    ),
                    intArrayOf(1, 8, 1),
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(false, false, false),
                    intArrayOf(
                        PrinterManager.FONT_SMALL,
                        PrinterManager.FONT_SMALL,
                        PrinterManager.FONT_SMALL
                    ),
                    intArrayOf(PrinterManager.LEFT, PrinterManager.CENTER, PrinterManager.RIGHT)
                )

                serialPrinterManager.addBarcode(
                    "323423423432255",
                    520,
                    230,
                    "CODE_128",
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.addLine(
                    "_________CUSTOMER COPY_________",
                    true,
                    false,
                    false,
                    PrinterManager.FONT_NORMAL,
                    PrinterManager.CENTER
                )
                serialPrinterManager.addLine("\n")
                serialPrinterManager.beginPrint()
            } catch (e: RemoteException) {
                e.printStackTrace()
                runOnUiThread {
                    dismissLoading()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    dismissLoading()
                }
            }
            runOnUiThread {
                dismissLoading()
            }


        }.start()

    }

    fun formatAmount(amountInDollars: String?): String {
        if (TextUtils.isEmpty(amountInDollars)) {
            return "0"
        }
        val amountInCent = amountInDollars?.toFloatOrNull()
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat.format(amountInCent) // 使用 NumberFormat 类来格式化金额
    }
}