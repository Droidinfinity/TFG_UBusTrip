package com.upm.ubustrip.features.linea
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import kotlinx.coroutines.delay

@Composable
fun TiemposDeEspera(viewModel: LineaRTSViewModel) {
    val isLoading = viewModel.isLoading.value
    val parada = viewModel.parada.value
    val lineas = viewModel.lineasRTModel.value
    val lineaSeleccionada = viewModel.lineaSeleccionada.value
    val buses = viewModel.busesLinea

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        parada?.lineasParada?.isEmpty() == true || parada == null || lineas == null || viewModel.lineasRTModelMod.value < 0 -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error. No se han podido cargar los datos por ahora")
            }
        }

        else -> {
            Log.d("erasmus","Datos cargados")
                if (viewModel.firstRefresh){
                    viewModel.iniciarCambioDeTabs()
                }


            Column {
                for (bus in buses) {
                    BusItem(
                        route = lineas[lineaSeleccionada].nombreLinea,
                        lineNumber = "361", // <-- esto debería ser dinámico idealmente
                        timeMinutes = 3     // <-- también este
                    )
                }
            }
        }
    }
}


@Composable
fun BusItem(
    lineNumber: String,
    route: String,
    timeMinutes: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Caja con número de línea
        Box(
            modifier = Modifier
                .size(50.dp, 40.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lineNumber,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = route,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = "In $timeMinutes minutes",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(end = 5.dp))
    }

    HorizontalDivider()
}