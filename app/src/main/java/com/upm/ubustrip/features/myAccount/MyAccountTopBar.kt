package com.upm.ubustrip.features.myAccount

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)

@Preview
@Composable
fun MyAccountTopBar() {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)

    val configuration = LocalConfiguration.current
    val screenHeightPx = (configuration.screenHeightDp / 2) - 40


    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,         // Color de fondo de la TopAppBar
            titleContentColor = Color.Black,       // Color del título
        ),
        title = { Text("Holñdddddddddddddddddddddddddddddddda", modifier = Modifier.padding(top = 90.dp)) },
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(screenHeightPx.dp)
    )

}


@Composable
fun CircularGradientBackground() {

    val configuration = LocalConfiguration.current
    val screenHeightPx = (configuration.screenHeightDp / 2) - 20

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeightPx.dp)
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo de fondo más grande (azul oscuro)
            drawCircle(
                color = Color(0xFF42A5F5), // Cambia por el color exacto deseado
                radius = size.width * 1.2f, // Ajuste de tamaño
                center = Offset(x = 0f, y = size.height / 2)
            )
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo medio (azul medio)
            drawCircle(
                color = Color(0xFF2196F3), // Cambia por el color exacto deseado
                radius = size.width * 0.8f,
                center = Offset(x = 0f, y = size.height / 2)
            )
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo más pequeño (azul claro)
            drawCircle(
                color = Color(0xFF1E88E5), // Cambia por el color exacto deseado
                radius = size.width * 0.5f,
                center = Offset(x = 0f, y = size.height / 2)
            )
        }
    }
}

@Preview
@Composable
fun TopNavigationBar() {


    Row(

        modifier = Modifier
            .fillMaxWidth()

    ) {
        IconButton(
            onClick = {}

        ) { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }

    }


}

