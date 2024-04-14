package com.sunmi.print.data

import com.sunmi.print.data.model.LoggedInUser
import com.sunmi.print.utils.MMKVUtil
import com.sunmi.print.utils.NetTools
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val accountCache = MMKVUtil.getInstance().getStringValue("_ACCOUNT", "user0908")
            val pwdCache = MMKVUtil.getInstance().getStringValue("_PASSWORD", "qwer1234")
            if (username == accountCache && password == pwdCache) {
                val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
                return Result.Success(fakeUser)
            } else {
                return Result.Error("Password error")
            }
        } catch (e: Throwable) {
            return Result.Error("Password error")
        }
    }

    fun logout() {
    }
}