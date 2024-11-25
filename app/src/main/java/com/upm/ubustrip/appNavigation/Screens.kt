package com.upm.ubustrip.appNavigation


sealed class AppScreens(val route: String){

    object FirstScreen: AppScreens(route = "firstScreen")
    object LogInScreen: AppScreens(route = "loginScreen")
    object MenuScreen: AppScreens(route = "menuScreen")
    object SearchScreen : AppScreens(route = "searchScreen")
    object AccountScreen : AppScreens(route = "accountScreen")
    object RegisterScreen : AppScreens(route = "registerScreen")
    object ForgotUPasswordScreen : AppScreens(route = "forgotUPasswordScreen")

}