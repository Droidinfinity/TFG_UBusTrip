package com.upm.ubustrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.upm.ubustrip.appNavigation.AppNavigation
import com.upm.ubustrip.appNavigation.AppScreens
import com.upm.ubustrip.ui.theme.MyApplicationTheme
import  android.Manifest

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        solicitarPermisoUbicacion()

        enableEdgeToEdge()
        setContent {
            AppNavigation()


        }
    }

    fun solicitarPermisoUbicacion() {

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }

                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

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