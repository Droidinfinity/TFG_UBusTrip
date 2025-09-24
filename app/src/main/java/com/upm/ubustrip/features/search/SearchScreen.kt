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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upm.ubustrip.features.menu.TopAppBarContent
import com.upm.ubustrip.ui.theme.UbusTripBottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navigator: NavController) {

    val searchModelView = SearchBarViewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        SearchTopAppBar(
            viewModel = searchModelView,
            scrollBehavior = scrollBehavior
        )
    },
        content = { paddingValues ->
            SearchContent(
                paddingValues,
                viewModel = searchModelView,
                scrollBehavior = scrollBehavior
            )
        })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(viewModel: SearchBarViewModel, scrollBehavior: TopAppBarScrollBehavior) {


    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = UbusTripBottomBar,
    ) {
        TopAppBar(
            title = { CustomSearchBar(viewModel = viewModel) },
            scrollBehavior = scrollBehavior,
            modifier = Modifier
                .statusBarsPadding(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
            )

        )


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    paddingValues: PaddingValues,
    viewModel: SearchBarViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {

    //elementos necesarios para dismissear el teclado en caso de hacer scroll
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    //--------------------------------------------------------------------

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        if (viewModel.desplegado.value)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection) //detectamos el scroll para notificar a la appTopBar

                //TODO: DESHABILITADO POR EL MOMENTO, PROVOCA PARPADEOS AL ACTUALIZARSE LA LISTA
                   /**
                    .onGloballyPositioned { //dismiseamos el teclado en caso de scroll

                        if (viewModel.previousState.value) {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }

                    }*/

            ) {

                items(viewModel.lista.size) { index ->
                    Text(
                        text = "${viewModel.lista.get(index).nombre} ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
    }


}

