package com.upm.ubustrip.features.linea

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun LineaRTScreen() {

    val linea: String = ""
    val s = Segmento("gkuuFjxrTQCOAgBOSCo@Ge@EoAKuAO_@Ca@Ac@?_@B", numeroSegmento = 2)
    s.esSegmentoInicial = true
    /* CordenadasUtils.distanciaCoordenadasHaversineSegmento(s)
     CordenadasUtils.distanciaHaversineHastaCoordenada(s, Coordenada(40.41941,-3.54195))
 */
    //val systemUiController = rememberSystemUiController()
    //systemUiController.setSystemBarsColor(Color.Transparent)

    Column {

        Spacer(Modifier.height(200.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {

            items(1) { index ->

                Column {

                    LineaSegmento(s)

                }
            }

        }

    }


}

@Composable
fun CircleWithText() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {
        // Círculo
        Box(
            modifier = Modifier
                .size(30.dp) // Tamaño del círculo
                .background(color = Color.Blue, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el círculo y el texto

        // Texto al lado del círculo
        Text(
            text = "Parada",

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

    val distanciaSemento = CordenadasUtils.distanciaCoordenadasHaversineSegmento(segmento)
    if (segmento.esSegmentoInicial) {

        Box {

            Column {

                CircleWithText()
                Line(distanciaSemento.toInt())

            }

            //MOVIMIENTO DEL BUS------------------------------------------------------
            Column {

                Spacer(Modifier.height(30.dp)) //Tamaño del circulo (hay que considerarlo)
                Spacer(Modifier.height(0.dp)) //desplazamiento del bus
                // Box superpuesto
                Box(
                    modifier = Modifier
                        .size(30.dp) // Tamaño del Box cuadrado
                        .background(Color.Red) // Color del Box

                )

            }
        }


    } //fin si era segmento inicial


}

fun buscarBusesSegmento(segmento: Segmento, buses: MutableList<Bus>) : MutableList<Bus> {

    val busesSegmento = mutableListOf<Bus>()

    for (bus in buses){

      if(bus.ultimoSegmentoVisitado<=segmento.numeroSegmento){

          

      }

    }

    return busesSegmento

}