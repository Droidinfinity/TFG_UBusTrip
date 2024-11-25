package com.upm.ubustrip.features.login

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel

class ForgotUPassViewModel : ViewModel() {

    private var _email = ""


    fun setEmail(email: String) {

        this._email = email
    }

    fun getEmail(): String {

        return this._email
    }


}