package com.upm.ubustrip.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Menu
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upm.ubustrip.GreetingPreview
import com.upm.ubustrip.features.login.LoginScreen
import com.upm.ubustrip.features.login.LoginScreenViewModel
import com.upm.ubustrip.features.menu.Menu
import com.upm.ubustrip.features.menu.MenuViewModel
import com.upm.ubustrip.features.myAccount.MyAccountScreen
import com.upm.ubustrip.features.register.NewAccountViewModel
import com.upm.ubustrip.features.register.SignUp
import com.upm.ubustrip.features.search.SearchScreen
import com.upm.ubustrip.firebase.LoginViewModel

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    val menuViewModel : MenuViewModel = MenuViewModel()
    val loginViewModel : LoginViewModel = LoginViewModel()
    val newAccountViewModel : NewAccountViewModel = NewAccountViewModel()
    val loginScreenViewModel : LoginScreenViewModel = LoginScreenViewModel()

    NavHost(navController, startDestination = AppScreens.MenuScreen.route){

        composable(route = AppScreens.MenuScreen.route) { Menu(navController, menuViewModel = menuViewModel, loginViewModel = loginViewModel) }
        composable(route = AppScreens.FirstScreen.route) { GreetingPreview(navController) }
        composable(route = AppScreens.LogInScreen.route) { LoginScreen(navController, loginViewModel = loginViewModel, loginScreenViewModel = loginScreenViewModel) }
        composable(route = AppScreens.SearchScreen.route) { SearchScreen(navController) }
        composable(route = AppScreens.AccountScreen.route) { MyAccountScreen(navController, loginViewModel = loginViewModel) }
        composable(route = AppScreens.RegisterScreen.route){ SignUp(navController = navController,newAccountViewModel, loginViewModel = loginViewModel) }

    }

}