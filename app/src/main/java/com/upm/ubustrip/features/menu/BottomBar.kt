package com.upm.ubustrip.features.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upm.ubustrip.features.linea.LineaRTSViewModel
import com.upm.ubustrip.ui.theme.UbusTripBottomBar
import com.upm.ubustrip.ui.theme.UbusTripBottomBarSelected

@Composable
fun BottomBar(
    tabBarItems: List<TabBarItem>,
    navController: NavController,
    viewModel: MenuViewModel,
    lineaViewModel: LineaRTSViewModel
) {

    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 10.dp,
    ) {

        NavigationBar(containerColor = Color.White) {
            // looping over each tab to generate the views and navigation for each item
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,   // Color del ícono cuando está seleccionado
                    unselectedIconColor = Color.Black,  // Color del ícono cuando no está seleccionado
                    selectedTextColor = Color.Black,   // Color del texto cuando está seleccionado
                    unselectedTextColor = Color.Black,  // Color del texto cuando no está seleccionado
                    indicatorColor = UbusTripBottomBarSelected
                ), selected = selectedTabIndex == index, onClick = {
                    selectedTabIndex = index
                    //pruebas linea
                    lineaViewModel.initPorParada(
                        id = "67743f46812b47c0083d0065",
                        nombreParada = "Av.Constitución-Pza.Marañón",
                        lineas = mutableListOf("6774351ab34b449419e3638f"),
                        numeroParada = "07173"
                    )
                    navController.navigate(tabBarItem.navLocation)

                    viewModel.updateTitle(tabBarItem.title)
                    selectedTabIndex = 0
                }, icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                }, label = { Text(tabBarItem.title) })
            }
        }


    }


}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            }, contentDescription = title
        )
    }
}

// This component helps to clean up the API call from our TabBarIconView above,
// but could just as easily be added inside the TabBarIconView without creating this custom component
@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}

