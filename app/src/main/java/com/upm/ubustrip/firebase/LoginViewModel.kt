package com.upm.ubustrip.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.upm.ubustrip.appNavigation.AppScreens
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var _credential: AuthCredential
    private val _googleToken =
        "150475440568-mbujh628biruqi1o24d739c3o8sa7dqd.apps.googleusercontent.com"
    private lateinit var _navController: NavController
    private val _loading =
        MutableLiveData(false) //evitamos que se creen varias cuentas de forma simultanea

    fun sigInWhithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {


            try {
                auth.signInWithCredential(credential).addOnCompleteListener { task ->

                    if (task.isSuccessful) Log.d("Login", "Login Correcto")
                    _navController.popBackStack() //si nos hemos logueado de forma correcta dissmiseamos la pantalla de login
                    _navController.navigate(route = AppScreens.AccountScreen.route)
                }
            } catch (exeption: Exception) {
                Log.d("Login", "Login  Inorrecto")
            }
        }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {

            try {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Log.d("Login", "Login Correcto")
                            _navController.popBackStack() //si nos hemos logueado de forma correcta dissmiseamos la pantalla de login
                            _navController.navigate(route = AppScreens.AccountScreen.route)
                        } else home()
                    }

            } catch (ex: Exception) {
                Log.d("Login", "Login  Inorrecto")
            }

        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        home: () -> Unit
    ) {

        if (_loading.value == false) { //si no se esta creando ningun usuario...

            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        user?.let {

                            val profileSetUp = userProfileChangeRequest {
                                displayName = name
                            }
                            it.updateProfile(profileSetUp).addOnCompleteListener { profileTask ->

                                if (profileTask.isSuccessful) {
                                    Log.d("Registro", "Nombre del usuario actualizado")
                                    home()
                                } else
                                    Log.d("Registro", "Error al actualizar el perfil del usuario")


                            }

                        }


                    } else
                        Log.d("Registro", "Registro email  Inorrecto")

                }
            _loading.value = false


        }

    }

    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit) {

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    //se podria manejar el error desde aquí, pero en este caso será mas cómodo desde el composable
                }
            }
    }


    fun setGoogleCredential(credential: AuthCredential) {

        this._credential = credential

    }

    fun getGoogleToken(): String {

        return this._googleToken
    }

    fun setNavController(navController: NavController) {

        this._navController = navController

    }

    fun getAuth(): FirebaseAuth {

        return this.auth
    }


}