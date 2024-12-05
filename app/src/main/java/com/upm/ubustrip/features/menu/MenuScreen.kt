package com.upm.ubustrip.features.menu

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.upm.ubustrip.appNavigation.AppScreens
import com.upm.ubustrip.features.favorites.Favorites
import com.upm.ubustrip.firebase.LoginViewModel
import com.upm.ubustrip.firebase.dbViewModel


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null,
    val navLocation: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Menu(
    navigator: NavController,
    menuViewModel: MenuViewModel,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    dbViewModel: dbViewModel
) {

    val isLogged = loginViewModel.getAuth().currentUser != null
    var accountNavigation = ""

    if(isLogged)
        accountNavigation = "accountScreen"
    else
        accountNavigation = "loginScreen"


    // tabs para la navegación
    val homeTab = TabBarItem(
        title = "Favoritos",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        navLocation = "menuScreen"
    )

    val settingsTab = TabBarItem(
        title = "Buscar",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        navLocation = "searchScreen"
    )
    val moreTab = TabBarItem(
        title = "Mi Cuenta",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        navLocation = accountNavigation
    )


    val tabBarItems = listOf(homeTab, settingsTab, moreTab)



    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = { BottomBar(tabBarItems, navigator, viewModel = MenuViewModel()) },
            topBar = { TopBar(viewModel = MenuViewModel()) },
            content = { paddingValues -> Favorites(paddingValues) }

        )
    }


}








