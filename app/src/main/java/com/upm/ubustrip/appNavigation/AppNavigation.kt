package com.upm.ubustrip.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upm.ubustrip.GreetingPreview
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.features.login.ForgotUPassViewModel
import com.upm.ubustrip.features.login.ForgotUPasswordScreen
import com.upm.ubustrip.features.login.LoginScreen
import com.upm.ubustrip.features.login.LoginScreenViewModel
import com.upm.ubustrip.features.menu.Menu
import com.upm.ubustrip.features.menu.MenuViewModel
import com.upm.ubustrip.features.myAccount.MyAccountScreen
import com.upm.ubustrip.features.register.NewAccountViewModel
import com.upm.ubustrip.features.register.SignUp
import com.upm.ubustrip.features.search.SearchScreen
import com.upm.ubustrip.database.LoginViewModel
import com.upm.ubustrip.features.linea.LineaRTSViewModel
import com.upm.ubustrip.features.linea.LineaRTScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    val dbViewModel : AppDatabase = AppDatabase()
    val menuViewModel : MenuViewModel = MenuViewModel()
    val loginViewModel : LoginViewModel = LoginViewModel()
    val newAccountViewModel : NewAccountViewModel = NewAccountViewModel()
    val loginScreenViewModel : LoginScreenViewModel = LoginScreenViewModel()
    val forgotUPassWordViewModel: ForgotUPassViewModel = ForgotUPassViewModel()
    val lineaRTSViewModel : LineaRTSViewModel = LineaRTSViewModel()

    NavHost(navController, startDestination = AppScreens.MenuScreen.route){

        composable(route = AppScreens.MenuScreen.route) { Menu(navController, menuViewModel = menuViewModel, loginViewModel = loginViewModel) }
        composable(route = AppScreens.FirstScreen.route) { GreetingPreview(navController) }
        composable(route = AppScreens.LogInScreen.route) { LoginScreen(navController, loginViewModel = loginViewModel, loginScreenViewModel = loginScreenViewModel) }
        composable(route = AppScreens.SearchScreen.route) { SearchScreen(navController) }
        composable(route = AppScreens.AccountScreen.route) { MyAccountScreen(navController, loginViewModel = loginViewModel) }
        composable(route = AppScreens.RegisterScreen.route){ SignUp(navController = navController,newAccountViewModel, loginViewModel = loginViewModel) }
        composable(route = AppScreens.ForgotUPasswordScreen.route){ ForgotUPasswordScreen(loginViewModel = loginViewModel, navController = navController, forgotUPassViewModel = forgotUPassWordViewModel) }
        composable(route = AppScreens.LineaRTScreen.route){ LineaRTScreen(viewModel = lineaRTSViewModel, navController = navController,"","") }
    }

}