package com.upm.ubustrip.features.login

import androidx.lifecycle.ViewModel

class LoginScreenViewModel : ViewModel() {

    private var _email = ""
    private var _password = ""


    fun setEmail(email: String) {

        this._email = email

    }

    fun setPassword(password: String) {

        this._password = password

    }

    fun getEmail(): String {
        return this._email
    }

    fun getPassword(): String {
        return this._password
    }

}