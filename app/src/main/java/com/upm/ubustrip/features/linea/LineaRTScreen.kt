package com.upm.ubustrip.features.linea

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.CordenadasUtils
import com.upm.ubustrip.models.Segmento
import com.upm.ubustrip.ui.theme.UBusTripBlueColor


@Composable
fun LineaRTScreen(viewModel: LineaRTSViewModel) {

    //para que la barra de notificaciones se funda con la appBar
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(UBusTripBlueColor)
    //-------------------------------------------------------

    val listaBus = mutableListOf<Bus>()
    listaBus.add(Bus("a", Coordenada(40.42018, -3.54167), 1))
    listaBus.add(Bus("b", Coordenada(40.42087, -3.54156), 1))

    listaBus.add(Bus("c", Coordenada(40.42275, -3.54189), 1))
    listaBus.add(Bus("d", Coordenada(40.42042, -3.54280), 1))

    val s = Segmento("gkuuFjxrTQCOAgBOSCo@Ge@EoAKuAO_@Ca@Ac@?_@B", numeroSegmento = 2)
    s.esSegmentoInicial = true


    /* CordenadasUtils.distanciaCoordenadasHaversineSegmento(s)
     CordenadasUtils.distanciaHaversineHastaCoordenada(s, Coordenada(40.41941,-3.54195))
 */



    Scaffold(
        topBar = { TopBarLineaRTS(viewModel = viewModel) },
        content = { paddingValues ->
            ContenidoParada(
                Modifier.padding(paddingValues = paddingValues),
                viewModel = viewModel
            )
        })

}


@Composable
fun ContenidoParada(modifier: Modifier, viewModel: LineaRTSViewModel) {

    val selectedTabIndex by viewModel.selectedTabIndex

    Column(modifier = modifier) {

        when (selectedTabIndex) {
            0 -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Pantalla Opción 1")
            }
            1 -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Pantalla Opción 2")
            }
        }

    }


}


