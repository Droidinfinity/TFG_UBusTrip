package com.upm.ubustrip.features.incidencias

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.enums.IncidenciaColors
import com.upm.ubustrip.features.incidencias.viewModels.IncidenciasViewModel
import com.upm.ubustrip.ui.theme.UBusTripBlueColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciasTopBar(incidenciasViewModel: IncidenciasViewModel){

    val idColor = 0
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = IncidenciaColors.fromId(idColor)!!.color,
        darkIcons = true
    )


    Column {

        CenterAlignedTopAppBar(
           title = {
               Text("Notificación para la Linea 2 Cosalada - Aeropueto",
                   color = incidenciasViewModel.setColorTítulo(idColor),
                   fontSize = 20.sp)
                   },

            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = IncidenciaColors.fromId(idColor)!!.color,
                titleContentColor = incidenciasViewModel.setColorTítulo(idColor),
            ),
            navigationIcon = {
                IconButton(onClick = {
                    incidenciasViewModel.hideIncidenciaDialog()

                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Volver",
                        tint = incidenciasViewModel.setColorTítulo(idColor)
                    )
                }
            }


        )


    }

}