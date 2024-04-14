package com.sunmi.print.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.sunmi.print.data.LoginRepository
import com.sunmi.print.data.Result

import com.sunmi.print.R
import com.sunmi.print.viewmodel.BaseViewModel
import kotlinx.coroutines.delay

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        launch {
            delay(1500)
            val result = loginRepository.login(username, password)

            if (result is Result.Success) {
                _loginResult.postValue(
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName)))
            } else {
                _loginResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}