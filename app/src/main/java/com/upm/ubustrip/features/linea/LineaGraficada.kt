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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LineaGraficada(){

    //Desplazamiento a la ubicación de la parada
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch { //creamos nuevo hilo para el desplazamiento
        listState.animateScrollToItem(8)
    }

    val s = Segmento("gkuuFjxrTQCOAgBOSCo@Ge@EoAKuAO_@Ca@Ac@?_@B", numeroSegmento = 2)
    s.esSegmentoInicial = true


    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {

        items(3) { index ->

            Column {

            LineaSegmento(s)
                LineaSegmento(s)
                LineaSegmento(s)

            }
        }

    }


}




@Composable
fun CircleWithText(text : String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),

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
        Text(
            text = text,

            )
    }
}

@Composable
fun Line(height: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp) // Altura total

    ) {
        // Línea
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

@Composable
fun LineaSegmento(segmento: Segmento) {

    val listaBus = mutableListOf<Bus>()
    listaBus.add(Bus("a", Coordenada(40.42018,-3.54167 ),1))
    listaBus.add(Bus("b", Coordenada(40.42087,-3.54156 ),1))

    listaBus.add(Bus("c", Coordenada(40.42275,-3.54189 ),1))
    listaBus.add(Bus("d", Coordenada(40.42042,-3.54280 ),1))

    segmento.paradaInicial = "Avda.Contitución"
    val distanciaSemento = CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento)
    if (segmento.esSegmentoInicial) {

        Box {

            Column {

                CircleWithText(segmento.paradaInicial!!)
                Line(distanciaSemento.toInt())

            }

            //BUSCAMOS TODOS LOS BUSES QUE HAYAN ES ESE SEGMENTO
            for(bus in segmento.buscarBuses(listaBus)){

                val posicionBus = CordenadasUtils.distanciaHaversineHastaCoordenada(segmento,bus.second)
                //MOVIMIENTO DEL BUS------------------------------------------------------
                Column {

                    Spacer(Modifier.height(30.dp)) //Tamaño del circulo (hay que considerarlo)
                    Spacer(Modifier.height(posicionBus.dp)) //desplazamiento del bus
                    // Box superpuesto
                    Box(
                        modifier = Modifier
                            .size(30.dp) // Tamaño del bus
                            .background(UbusTripBusRTColor) // Color del bus

                    )

                }

            }

        }


    } //fin si era segmento inicial
//TODO: EN CASO DE QUE NO SEA UN SEGMENTO FINAL

}