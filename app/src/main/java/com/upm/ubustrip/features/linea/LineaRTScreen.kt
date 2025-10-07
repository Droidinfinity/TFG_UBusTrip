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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.features.incidencias.IncidenciaScreen
import com.upm.ubustrip.features.incidencias.viewModels.IncidenciasViewModel
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.CordenadasUtils
import com.upm.ubustrip.models.ParadaModel
import com.upm.ubustrip.models.Segmento
import com.upm.ubustrip.ui.theme.UBusTripBlueColor
import com.upm.ubustrip.ui.theme.UbusTripFilledButton2Color

/*PANTALLA PRINCIPAL CON TOP BAR Y CONTENIDO:
Tiempos de espera
Tiempo real (graficada)
Mapa
*/
@Composable
fun LineaRTScreen(viewModel: LineaRTSViewModel, navController: NavController, incidenciasViewModel: IncidenciasViewModel) {

    //para que la barra de notificaciones se funda con la appBar
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(UBusTripBlueColor)
    //-------------------------------------------------------


    if (!viewModel.lineasRTModel.value.isNullOrEmpty()) {
        val primeraLinea = viewModel.lineasRTModel.value?.firstOrNull()

        if (primeraLinea != null) {
            LaunchedEffect(Unit) {
                incidenciasViewModel.initInciidencia(primeraLinea.id)
            }
            if (!incidenciasViewModel.incidenciaShowed.value) {
                IncidenciaScreen(viewModel = incidenciasViewModel)
            }else{

                Scaffold(
                    topBar = { TopBarLineaRTS(viewModel = viewModel, navController = navController) },
                    content = { paddingValues ->
                        ContenidoParada(
                            Modifier.padding(paddingValues = paddingValues),
                            viewModel = viewModel
                        )
                    })

            }
        }
    }
    else  {
        Scaffold(
            topBar = { TopBarLineaRTS(viewModel = viewModel, navController = navController) },
            content = { paddingValues ->
                ContenidoParada(
                    Modifier.padding(paddingValues = paddingValues),
                    viewModel = viewModel
                )
            })
    }

}


@Composable
fun ContenidoParada(modifier: Modifier, viewModel: LineaRTSViewModel) {

    val selectedTabIndex by viewModel.selectedTabIndex
    var showDialog by remember { mutableStateOf(true) }



    //si tenemso lineas disponibles y en caso de que haya mas de una lanzamos el modal
    if (viewModel.lineasRTModel.value != null)
        if (viewModel.lineasRTModel.value?.size!! < 2 && viewModel.lineasRTModel.value?.size!! > 0)
            showDialog = false

    Column(modifier = modifier) {

        when (selectedTabIndex) {
            //caso TIEMPOS DE ESPERA
            0 -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {


                TiemposDeEspera(viewModel = viewModel)
            }
            //caso TIEMPO REAL
            1 -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.lineasRTModel.value?.size!! > 1)
                ModalLineas(viewModel = viewModel, showDialog = showDialog, onDismiss = {
                    showDialog = false
                })
                else
                    showDialog = false
                if (!showDialog) {
                    Spacer(Modifier.height(20.dp))
                    //Si solo existe una linea en esa parada, iniciamos el listener para esta parada
                    if (viewModel.idLineaSeleccionada.isEmpty()){

                        viewModel.idLineaSeleccionada = viewModel.lineasRTModel.value!![0].id
                        viewModel.initBusesListener(viewModel.idLineaSeleccionada)
                    }

                    LineaGraficada(viewModel = viewModel)
                }
            }
            2-> MapTestScreen(viewModel = viewModel)
        }

    }


}


@Composable
fun ModalLineas(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    viewModel: LineaRTSViewModel
) {
    if (viewModel.lineasRTModel.value != null)
        if (showDialog) {
            Dialog(onDismissRequest = { onDismiss() }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Escoge una linea", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Existe mas de una linea para esta parada.")
                        for (linea in viewModel.lineasRTModel.value!!) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    onDismiss()
                                    viewModel.lineaSeleccionada.value =
                                        viewModel.lineasRTModel.value!!.indexOf(linea)
                                    viewModel.initBusesListener(linea = linea.id)
                                    viewModel.idLineaSeleccionada = linea.id
                                    Log.d("lin","${linea.id}")
                                    Log.d("Modal", "${viewModel.lineaSeleccionada.value}")
                                },
                                colors = ButtonColors(
                                    containerColor = UBusTripBlueColor,
                                    contentColor = Color.White,
                                    disabledContainerColor = UbusTripFilledButton2Color,
                                    disabledContentColor = UbusTripFilledButton2Color
                                )

                            ) {
                                Text(linea.nombreLinea)
                            }
                        }
                    }
                }
            }
        }
}