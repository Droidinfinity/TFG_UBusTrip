package com.upm.ubustrip.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.upm.ubustrip.appNavigation.AppScreens
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit  var _credential: AuthCredential
    private val _googleToken = "150475440568-mbujh628biruqi1o24d739c3o8sa7dqd.apps.googleusercontent.com"
    private lateinit var _navController: NavController

    fun sigInWhithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {


            try {
                auth.signInWithCredential(credential).addOnCompleteListener { task ->

                    if(task.isSuccessful) Log.d("Login","Login Correcto")
                    _navController.popBackStack()
                    _navController.navigate(route = AppScreens.AccountScreen.route)
                }
            }
            catch (exeption:Exception){ Log.d("Login","Login  Inorrecto")}
        }

    fun setGoogleCredential(credential : AuthCredential){

        this._credential = credential

    }

    fun getGoogleToken() : String{

        return this._googleToken
    }

    fun setNavController(navController: NavController){

        this._navController = navController

    }

    fun getAuth() : FirebaseAuth{

        return this.auth
    }



}