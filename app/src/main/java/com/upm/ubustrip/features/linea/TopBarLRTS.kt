package com.upm.ubustrip.features.linea


import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.upm.ubustrip.features.favorites.viewModel.FavoritesViewModel
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import com.upm.ubustrip.features.linea.viewModels.TopBarLRTSViewModel
import com.upm.ubustrip.models.ParadaFavorita
import com.upm.ubustrip.ui.theme.UBusTripBlueColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLineaRTS(viewModel: LineaRTSViewModel, navController: NavController) {
    val parada = viewModel.parada.value
    var selectedTabIndex by remember { mutableStateOf(0) }
    val opciones = listOf("Tiempos de espera", "Tiempo real", "Mapa")
    val topBarLRTSViewModel : TopBarLRTSViewModel = viewModel()

    val favorites by topBarLRTSViewModel.favorites.collectAsState()

    // ⭐ CALcular si es favorito basado en el estado actual
    val esFav = remember(parada?.id, favorites) {
        parada?.id?.let { id -> favorites.any { it.id == id } } ?: false
    }


    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = UBusTripBlueColor,
                titleContentColor = Color.White,
            ),
            title = {
                Text(
                    parada?.nombreParada ?: "Cargando...",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                    viewModel.setSelectedTabIndex(0)
                    viewModel.removeBusesListener(viewModel.idLineaSeleccionada)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            },
            actions = {

                val fav = ParadaFavorita(
                    id = viewModel.parada.value?.id ?: "",
                    numeroParada = viewModel.parada.value?.numeroParada ?: "DESCONOCIDA",
                    nombre = viewModel.parada.value?.nombreParada ?: "DESCONOCIDA"
                )

                if(viewModel.parada.value !=null) {
                    IconButton(onClick = {


                        if (esFav) {
                            topBarLRTSViewModel.removeFavorite(fav)
                        } else {
                            topBarLRTSViewModel.addFavorite(fav)
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorito",
                            tint = if (esFav) Color.White else Color.LightGray
                        )
                    }
                }
            }
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = UBusTripBlueColor
                )
            }
        ) {
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
