package com.upm.ubustrip.features.linea

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.CordenadasUtils
import com.upm.ubustrip.models.Segmento
import com.upm.ubustrip.ui.theme.UBusTripBlueColor
import com.upm.ubustrip.ui.theme.UbusTripBusRTColor
import com.upm.ubustrip.ui.theme.UbusTripGreen700
import kotlinx.coroutines.launch


fun scrollToStop(
    viewModel: LineaRTSViewModel,
    segmentosLinea: MutableList<Segmento>
): Int { //scroleamos a la parada que esté en el viewModel

    var index = 0

    for ((i, segmento) in segmentosLinea.withIndex()) {

        if (segmento.parada?.nombreParada == viewModel.parada.value!!.nombreParada)
            index = i

    }

    return index
}

/**
 * Representa la interfaz gráfica para mostrar las líneas graficadas de una parada.
 *
 * @param viewModel [LineaRTSViewModel] viewModel asociado.
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LineaGraficada(viewModel: LineaRTSViewModel) {

    // Desplazamiento a la ubicación de la parada
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    // Si no hay líneas para esa parada, mostramos una pantalla de error
    if (viewModel.parada.value?.lineasParada?.isEmpty() == true || viewModel.parada.value == null || viewModel.lineasRTModel.value == null || viewModel.lineasRTModelMod.value < 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error. No se han podido cargar los datos")
        }
    } else {


        //asignamos el desplazamiento a la parada escogida
        coroutineScope.launch {
            // Creamos un nuevo hilo para el desplazamiento
            listState.animateScrollToItem(
                scrollToStop(
                    viewModel = viewModel,
                    segmentosLinea = viewModel.lineasRTModel.value!![viewModel.lineaSeleccionada.value].segmentosLinea
                )
            )
        }

        // En caso contrario, mostramos la línea graficada
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {


            items(viewModel.lineasRTModel.value!![viewModel.lineaSeleccionada.value].segmentosLinea.size) { index ->
                Column {
                    LineaSegmento(
                        viewModel.lineasRTModel.value!![viewModel.lineaSeleccionada.value].segmentosLinea[index],
                        viewModel
                    )
                }
            }

        }
    }
}

/**
 * Representa un círculo con texto a su lado.
 *
 * @param text Texto que se mostrará junto al círculo.
 */
@Composable
fun CircleWithText(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo
        Box(
            modifier = Modifier
                .size(30.dp) // Tamaño del círculo
                .background(color = Color(0xffC62828), shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el círculo y el texto

        // Texto al lado del círculo
        Text(text = text)
    }
}

/**
 * Dibuja una línea vertical con una altura específica.
 *
 * @param height Altura de la línea en dp.
 */
@Composable
fun Line(height: Int, viewModel: LineaRTSViewModel) {

    var tamano = height
    //si el segmento es muy largo, lo reducimos a una escala menor...
    if (height >= viewModel.MAX_DISTANCIA_SEGMENTO)
        tamano = (height / viewModel.RATIO_SEGMENTO).toInt()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(tamano.dp) // Altura total
    ) {
        Canvas(
            modifier = Modifier
                .width(4.dp) // Grosor de la línea
                .fillMaxHeight() // Llena el alto disponible
                .align(Alignment.CenterStart) // Alineada a la izquierda
                .padding(start = 15.dp) // Alineamos con el medio del círculo
        ) {
            drawLine(
                color = Color.Gray,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 4.dp.toPx() // Grosor de la línea
            )
        }
    }
}

/**
 * Representa un segmento de línea con su información y visualización correspondiente.
 *
 * @param segmento Objeto [Segmento] que contiene la información del segmento de la linea a graficar.
 */
@Composable
fun LineaSegmento(segmento: Segmento, viewModel: LineaRTSViewModel) {

    // TODO: Lista de autobuses debería estar en el ViewModel al traernos los datos de la base de datos
    val listaBus = mutableListOf<Bus>()
    listaBus.add(Bus(ubicacion = Coordenada(longitud = 40.42555, latitud = -3.549921)))
    listaBus.add(Bus(ubicacion = Coordenada(longitud = 40.42600, latitud = -3.551466)))
    // Distancia del segmento calculada usando Haversine
    val distanciaSegmento = CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento)

    if(viewModel.refreshScreen.value>0)
    if (!segmento.esSegmentoFinal) {
        // Si no es el segmento final
        Box {
            Column {
                CircleWithText(segmento.parada?.nombreParada ?: "Parada desconocida")
                Line(distanciaSegmento.toInt(), viewModel)
            }

            // Buscamos todos los buses que hay en ese segmento
            for (bus in segmento.buscarBuses(viewModel.busesLinea)) {
                var posicionBus =
                    CordenadasUtils.distanciaHaversineHastaCoordenada(segmento, bus.second)
                var tamSegmento =
                    CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento = segmento)
                if (tamSegmento >= viewModel.MAX_DISTANCIA_SEGMENTO)
                    posicionBus = (posicionBus / viewModel.RATIO_SEGMENTO)
                // Movimiento del bus
                Column {
                    Spacer(Modifier.height(30.dp)) // Tamaño del círculo
                    Spacer(Modifier.height(posicionBus.dp)) // Desplazamiento del bus
                    // Representación del bus
                    Box(
                        modifier = Modifier
                            .size(30.dp) // Tamaño del bus
                            .background(UbusTripBusRTColor) // Color del bus
                    )
                }
            }
        }
        //TODO: Refactorizar el if con el else (codigo repetido) queda ver si eso afecta a los buses
    } else {
        // Si es el último segmento
        Box {
            Column {
                CircleWithText(segmento.parada?.nombreParada ?: "Parada desconocida")
                Line(distanciaSegmento.toInt(), viewModel)
                CircleWithText(segmento.paradaFinal?.nombreParada ?: "Parada desconocida")

            }

            // Buscamos todos los buses que hay en ese segmento
            for (bus in segmento.buscarBuses(viewModel.busesLinea)) {
                var posicionBus =
                    CordenadasUtils.distanciaHaversineHastaCoordenada(segmento, bus.second)
                var tamSegmento =
                    CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento = segmento)
                if (tamSegmento >= viewModel.MAX_DISTANCIA_SEGMENTO)
                    posicionBus = (posicionBus / viewModel.RATIO_SEGMENTO)
                // Movimiento del bus
                Column {
                    Spacer(Modifier.height(30.dp)) // Tamaño del círculo
                    Spacer(Modifier.height(posicionBus.dp)) // Desplazamiento del bus
                    // Representación del bus
                    Box(
                        modifier = Modifier
                            .size(30.dp) // Tamaño del bus
                            .background(UbusTripBusRTColor) // Color del bus
                    )
                }
            }
        }
    }
}



