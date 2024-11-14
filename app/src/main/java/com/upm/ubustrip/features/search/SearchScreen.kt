package com.upm.ubustrip.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

    val searchModelView = SearchBarViewModel()

    Scaffold(topBar = { SearchTopAppBar(viewModel = searchModelView) },
        content = { paddingValues -> SearchContent(paddingValues, viewModel = searchModelView) })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(viewModel: SearchBarViewModel) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = UbusTripBottomBar,
    ) {
        TopAppBar(
            title = { CustomSearchBar(viewModel = viewModel) },
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
fun SearchContent(paddingValues: PaddingValues, viewModel: SearchBarViewModel) {

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .padding(paddingValues)) {

        if (viewModel.desplegado.value)
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(20) { index ->
                    Text(
                        text = "Elemento $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
    }


}

