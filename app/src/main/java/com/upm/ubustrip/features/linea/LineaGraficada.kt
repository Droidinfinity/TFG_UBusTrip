package com.upm.ubustrip.features.linea

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.CordenadasUtils
import com.upm.ubustrip.models.Segmento
import com.upm.ubustrip.ui.theme.UBusTripBlueColor
import com.upm.ubustrip.ui.theme.UbusTripBusRTColor
import kotlinx.coroutines.launch

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

    coroutineScope.launch {
        // Creamos un nuevo hilo para el desplazamiento
        listState.animateScrollToItem(6)
    }

    // Si no hay líneas para esa parada, mostramos una pantalla de error
    if (viewModel.lineasRTModel.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error. No se han podido cargar los datos")
        }
    } else {
        // En caso contrario, mostramos la línea graficada
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {

            items(viewModel.lineasRTModel.first().segmentosLinea.size) { index ->
                Column {
                    LineaSegmento(viewModel.lineasRTModel.first().segmentosLinea[index])
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
fun Line(height: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp) // Altura total
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
fun LineaSegmento(segmento: Segmento) {

    // TODO: Lista de autobuses debería estar en el ViewModel al traernos los datos de la base de datos
    val listaBus = mutableListOf<Bus>()

    // Distancia del segmento calculada usando Haversine
    val distanciaSegmento = CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento)

    if (!segmento.esSegmentoFinal) {
        // Si no es el segmento final
        Box {
            Column {
                CircleWithText(segmento.parada.nombreParada)
                Line(distanciaSegmento.toInt())
            }

            // Buscamos todos los buses que hay en ese segmento
            for (bus in segmento.buscarBuses(listaBus)) {
                val posicionBus = CordenadasUtils.distanciaHaversineHastaCoordenada(segmento, bus.second)
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
    } else {
        // Si es el último segmento
        Box {
            Column {
                Line(distanciaSegmento.toInt())
                CircleWithText("${segmento.parada.nombreParada} (FINAL DE LINEA)")
            }

            // Buscamos todos los buses que hay en ese segmento
            for (bus in segmento.buscarBuses(listaBus)) {
                val posicionBus = CordenadasUtils.distanciaHaversineHastaCoordenada(segmento, bus.second)
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