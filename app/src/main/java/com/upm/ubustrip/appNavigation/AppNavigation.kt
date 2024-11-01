package com.upm.ubustrip.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upm.ubustrip.GreetingPreview
import com.upm.ubustrip.MainActivity
import com.upm.ubustrip.login.LoginScreen
import com.upm.ubustrip.ui.theme.MyApplicationTheme

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.FirstScreen.route){

        composable(route = AppScreens.FirstScreen.route) { GreetingPreview(navController) }
        composable(route = AppScreens.LogInScreen.route) { LoginScreen(navController) }

    }

}