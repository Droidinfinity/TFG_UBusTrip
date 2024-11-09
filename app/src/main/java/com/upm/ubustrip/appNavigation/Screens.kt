package com.upm.ubustrip.appNavigation


sealed class AppScreens(val route: String){

    object FirstScreen: AppScreens(route = "firstScreen")
    object LogInScreen: AppScreens(route = "loginScreen")


}