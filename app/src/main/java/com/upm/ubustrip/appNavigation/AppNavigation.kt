package com.upm.ubustrip.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upm.ubustrip.GreetingPreview
import com.upm.ubustrip.features.login.LoginScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.FirstScreen.route){

        composable(route = AppScreens.FirstScreen.route) { GreetingPreview(navController) }
        composable(route = AppScreens.LogInScreen.route) { LoginScreen(navController) }

    }

}