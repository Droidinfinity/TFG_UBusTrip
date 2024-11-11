package com.upm.ubustrip.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Menu
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upm.ubustrip.GreetingPreview
import com.upm.ubustrip.features.login.LoginScreen
import com.upm.ubustrip.features.menu.Menu

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(navController, startDestination = AppScreens.MenuScreen.route){

        composable(route = AppScreens.MenuScreen.route) { Menu(navController) }
        composable(route = AppScreens.FirstScreen.route) { GreetingPreview(navController) }
        composable(route = AppScreens.LogInScreen.route) { LoginScreen(navController) }

    }

}