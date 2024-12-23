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


@Composable
fun LineaRTScreen() {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {

        items(1) { index ->

            Column {
                CircleWithText()
                Line()
                CircleWithText()
                Line()
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
fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp) // Altura total

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
        Column {

            Spacer(Modifier.height(100.dp)) //DESPLAZAMIENTO DEL BUS
            // Box superpuesto
            Box(
                modifier = Modifier
                    .size(30.dp) // Tamaño del Box cuadrado
                    .background(Color.Red) // Color del Box

            )

        }


    }


}