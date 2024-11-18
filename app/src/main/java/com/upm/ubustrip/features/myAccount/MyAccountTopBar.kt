package com.upm.ubustrip.features.myAccount

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MyProfileCard(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)

    val configuration = LocalConfiguration.current
    val screenHeightPx = (configuration.screenHeightDp / 2) - 40


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeightPx.dp)
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
    ) {

        CircularGradientBackground()

        Column(modifier = Modifier.statusBarsPadding()) {

            //Barra de navegación (solo ir atrás)
            TopNavigationBar(Modifier.padding(top = 5.dp), navController = navController)
           // ------------------------------------------------------
            Spacer(modifier = Modifier.height(40.dp))

            //Imagén de perfíl y nombre-------------------------------
            Column() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                { CircularImage(modifier = Modifier.size(150.dp)) }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { Text("USUARIO", color = Color.White, fontSize = 18.sp) }
            }
            //-------------------------------------------------------------
        }


    }

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


@Composable
fun TopNavigationBar(modifier: Modifier,navController: NavController) {


    Row(

        modifier = modifier.fillMaxWidth(),

    ) {
        IconButton(
            onClick = {navController.popBackStack()}

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }




        Text("PERFÍL Y AJUSTES",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(start = 70.dp, top = 15.dp)
        )


    }



}


@Composable
fun CircularImage(modifier: Modifier) {
    //TODO: Usará la imagen de la cuenta en cuestión
    Image(
        painter = painterResource(id = R.drawable.ubustrip_icon),
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
    )
}



