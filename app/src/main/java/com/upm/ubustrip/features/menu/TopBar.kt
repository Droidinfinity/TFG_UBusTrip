package com.upm.ubustrip.features.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upm.ubustrip.features.search.CustomSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: MenuViewModel) {

    val barTitle by viewModel.barTitle
    var topBarMode by rememberSaveable {
        mutableStateOf(0) //indica que tipo de topBar debe salir: 0-> Normal 1-> Búsqueda
    }

    Surface( modifier = Modifier
        .fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp, ) {

        CenterAlignedTopAppBar(
            title = { if (topBarMode == 0) TopAppBarContent() else CustomSearchBar() },
            modifier = Modifier.statusBarsPadding(),

            )

    }


}

@Composable
fun TopAppBarContent() {

}