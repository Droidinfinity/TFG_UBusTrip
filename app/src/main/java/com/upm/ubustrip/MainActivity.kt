package com.upm.ubustrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.upm.ubustrip.appNavigation.AppNavigation
import com.upm.ubustrip.appNavigation.AppScreens
import com.upm.ubustrip.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()


            }
        }
    }


@Composable
fun GreetingPreview(navController: NavController) {
    MyApplicationTheme {

        //TODO: En caso de que haya onboardings, es probable que haya que modificar esta parte

        //NAVAGACIÓN AL MENÚ
        navController.navigate(route = AppScreens.LogInScreen.route)


    }
}