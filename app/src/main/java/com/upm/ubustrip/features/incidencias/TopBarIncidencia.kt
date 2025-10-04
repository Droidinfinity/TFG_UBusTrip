package com.upm.ubustrip.features.incidencias

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upm.ubustrip.features.incidencias.viewModels.IncidenciasViewModel
import com.upm.ubustrip.ui.theme.UBusTripBlueColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciasTopBar(incidenciasViewModel: IncidenciasViewModel){

    Column {

        CenterAlignedTopAppBar(
           title = {
               Text("Incidencia en la línea", color = Color.White) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = UBusTripBlueColor,
                titleContentColor = Color.White,
            ),
            navigationIcon = {
                IconButton(onClick = {
                    incidenciasViewModel.hideIncidenciaDialog()

                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }


        )


    }

}