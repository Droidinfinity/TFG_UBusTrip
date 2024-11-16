package com.upm.ubustrip.features.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.R
import com.upm.ubustrip.features.search.CustomSearchBar
import com.upm.ubustrip.ui.theme.UbusTripBottomBar
import com.upm.ubustrip.ui.theme.UbusTripFilledButton1Color
import com.upm.ubustrip.ui.theme.UbusTripFilledGoogleButtom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: MenuViewModel) {

    val barTitle by viewModel.barTitle

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.White,  // Elige tu color aquí
        darkIcons = true           // Controla el color de los íconos: true para oscuro, false para claro
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = UbusTripBottomBar,
        shadowElevation = 2.dp,
    ) {

        TopAppBar(
            title = { TopAppBarContent() },
            modifier = Modifier.statusBarsPadding().height(50.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,         // Color de fondo de la TopAppBar
                titleContentColor = Color.Black,       // Color del título
            )

        )

    }


}

@Composable
fun TopAppBarContent() {

    Row(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = R.drawable.ubustrip_icon_sin_fondo),
            contentDescription = "LoogoApp",
            Modifier

                .padding(end = 10.dp)
        )

        Text(
            "UBusTrip",
            style = TextStyle(fontSize = 40.sp),

            fontWeight = FontWeight.Bold,
            color = UbusTripFilledButton1Color
        )

    }

}