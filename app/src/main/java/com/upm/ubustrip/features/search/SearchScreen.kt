package com.upm.ubustrip.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upm.ubustrip.features.menu.TopAppBarContent
import com.upm.ubustrip.ui.theme.UbusTripBottomBar


@Composable
fun SearchScreen(navigator: NavController) {

    Scaffold(topBar = { SearchTopAppBar() },
        content = {paddingValues -> SearchContent(paddingValues) })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar() {

    val searchBarModifier = Modifier.padding(end = 40.dp)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = UbusTripBottomBar,
        shadowElevation = 2.dp,
    ) {
        TopAppBar(
            title = { CustomSearchBar(searchBarModifier) },
            modifier = Modifier
                .statusBarsPadding(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,         // Color de fondo de la TopAppBar
                titleContentColor = Color.Black,       // Color del título
            )

        )


    }


}

@Composable
fun SearchContent(paddingValues: PaddingValues){

    Box(modifier = Modifier.background(Color.White).fillMaxSize())

}