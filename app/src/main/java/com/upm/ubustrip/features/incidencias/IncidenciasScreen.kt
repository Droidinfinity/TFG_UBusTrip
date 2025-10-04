package com.upm.ubustrip.features.incidencias

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.upm.ubustrip.features.incidencias.viewModels.IncidenciasViewModel

@Composable
fun IncidenciaScreen(viewModel: IncidenciasViewModel){

    Scaffold(

        topBar = { IncidenciasTopBar(incidenciasViewModel = viewModel) },
        content = {paddingValues ->

            IncidenciasContent(padding = paddingValues)

        }

    )


}

@Composable
fun IncidenciasContent(padding: PaddingValues){



}