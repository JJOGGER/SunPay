package com.sunmi.print.data

import java.io.Serializable

class BindInfo(
    val name: String?,
    val bankAccount: String?,
    val bank:String?,
    val phoneNumber: String?,
    val country: String?,
    val swiftCode: String?,
) : Serializable {
}