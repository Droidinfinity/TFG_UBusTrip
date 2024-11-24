package com.upm.ubustrip.features.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NewAccountViewModel : ViewModel() {

    private var _password = mutableStateOf("")
    private var _passwordRepeated = mutableStateOf("")
    private var _email = ""


    fun setPassword(password: String) {

        this._password.value = password

    }

    fun getPassword(): String {

        return this._password.value
    }

    fun setRepeatedPassword(password: String) {

        this._passwordRepeated.value = password

    }

    fun getRepeatedPassword(): String {

        return this._passwordRepeated.value
    }

    fun setEmail(email: String) {

        this._email = email

    }

    fun getEmail(): String {

        return this._email
    }


}