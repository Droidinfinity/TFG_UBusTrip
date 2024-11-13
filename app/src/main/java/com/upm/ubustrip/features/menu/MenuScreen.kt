package com.upm.ubustrip.features.menu

import android.annotation.SuppressLint
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
import com.upm.ubustrip.features.favorites.Favorites


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null,
    val navLocation: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navigator: NavController) {

    // setting up the individual tabs
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
        navLocation = "miCuentaScreen"
    )

    // creating a list of all the tabs
    val tabBarItems = listOf(homeTab, settingsTab, moreTab)


    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = { BottomBar(tabBarItems, navigator, viewModel = MenuViewModel()) },
            topBar = { TopBar(viewModel = MenuViewModel()) },
            content = { paddingValues ->  Favorites(paddingValues) }

            )
    }


}








