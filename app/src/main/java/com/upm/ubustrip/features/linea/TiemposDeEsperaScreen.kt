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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upm.ubustrip.features.MapsUtils.MapsRoutes.Companion.getTiempoDesdeHasta
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import com.upm.ubustrip.models.Coordenada
import kotlinx.coroutines.delay

@Composable
fun TiemposDeEspera(viewModel: LineaRTSViewModel) {
    val isLoading = viewModel.isLoading.value
    val parada = viewModel.parada.value
    val lineas = viewModel.lineasRTModel.value
    val lineaSeleccionada = viewModel.lineaSeleccionada.value
    val buses = viewModel.busesPorLinea

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
               //     viewModel.iniciarCambioDeTabs()
                }


            Column {
                for ((lineaId, busList) in buses) {
                    val nombreLinea = viewModel.lineasRTModel.value?.find { it.id == lineaId }?.nombreLinea ?: "Desconocida"
                    val numeroLinea = viewModel.lineasRTModel.value?.find { it.id == lineaId }?.number ?: "¿L?"
                    val stop: Int = viewModel.parada.value?.stops?.get(lineaId) ?: 0



                    for (bus in busList) {
                        if (bus.nextStop <= stop) {

                            val origen = Coordenada(latitud = bus.ubicacion.latitud, longitud = bus.ubicacion.longitud)
                            val detsLat = parada.ubicacion?.lat?.toDoubleOrNull()
                            val detLong = parada.ubicacion?.long?.toDoubleOrNull()
                            val tiempoLlegadaState = remember(bus.matricula) { mutableStateOf<Int?>(-1) }

                            val o = Coordenada(latitud = -3.549559, longitud = 40.428229)
                            val d = Coordenada(latitud = -3.518797, longitud = 40.413201)

                            if (detsLat != null && detLong != null) {
                                val destino = Coordenada(latitud = detsLat, longitud = detLong)

                                Log.d("TiempoRuta", "Origen: ${origen.toString()}")
                                Log.d("TiempoRuta", "Destino: ${destino.toString()}")
                                LaunchedEffect(origen,destino) {
                                    try {
                                        val tiempo = getTiempoDesdeHasta(origen, destino)
                                        if (tiempo != null) {
                                            tiempoLlegadaState.value = tiempo
                                        } else
                                            tiempoLlegadaState.value = -1

                                        Log.d("TiempoRuta", "Tiempo estimado: $tiempo")
                                    } catch (e: Exception) {
                                        Log.e("TiempoRuta", "Error al obtener tiempo: ${e.message}")
                                    }

                                }

                            }else
                                Log.e("TiempoRuta", "Error al obtener la ubicación de la parada,")


                            BusItem(
                                route = nombreLinea,  // Usando la ID de la línea
                                lineNumber = numeroLinea, // Nombre de la línea
                                timeMinutes = tiempoLlegadaState.value ?: -1     // Tiempo dinámico si es necesario
                            )
                        }
                    }
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