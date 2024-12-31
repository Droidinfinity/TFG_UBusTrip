package com.upm.ubustrip.features.linea


import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.upm.ubustrip.ui.theme.UBusTripBlueColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLineaRTS(viewModel: LineaRTSViewModel, navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val opciones = listOf("Tiempos de espera", "Tiempo real", "Mapa")

    Column {

        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = UBusTripBlueColor,
                titleContentColor = Color.White,
            ),
            title = {
                Text(
                    "Parada",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                    viewModel.setSelectedTabIndex(0) // reseteamos para que el tab asignado vuelva a ser el inicial

                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },


            )


        // TabRow para las opciones
        TabRow(selectedTabIndex = selectedTabIndex, indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = UBusTripBlueColor
            )
        }) {
            opciones.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        viewModel.setSelectedTabIndex(index)
                    },
                    text = { Text(title, color = Color.Black) },

                    )
            }
        }


    }

}

