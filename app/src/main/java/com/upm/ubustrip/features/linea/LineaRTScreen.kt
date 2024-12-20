package com.upm.ubustrip.features.linea

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.appNavigation.AppScreens

@Composable
fun LineaRTScreen() {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp), // Ajuste horizontal

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Lista de elementos (círculo y línea alternados)
        items(70) { index ->


            if (index % 2 == 0) {
                // Círculo
                Circle()
            } else {
                // Línea
                Line()
            }
        }
    }
}

@Composable
fun Circle() {
    Box(
        modifier = Modifier
            .size(20.dp) // Tamaño del círculo
            .background(color = Color.Blue, shape = CircleShape)
    )
}

@Composable
fun Line() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Altura de la línea
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = 4.dp.toPx() // Grosor de la línea
        )
    }
}