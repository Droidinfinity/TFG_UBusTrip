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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upm.ubustrip.enums.LineColors
import com.upm.ubustrip.features.MapsUtils.MapsRoutes.Companion.getTiempoDesdeHasta
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.LineaRTModel
import kotlinx.coroutines.delay
import java.time.LocalTime


@Composable
fun TiemposDeEspera(viewModel: LineaRTSViewModel) {
    val isLoading = viewModel.isLoading.value
    var listIsReady = remember { mutableStateOf(false) }
    val parada = viewModel.parada.value
    val lineas = viewModel.lineasRTModel.value
    val lineaSeleccionada = viewModel.lineaSeleccionada.value
    val buses = viewModel.busesPorLinea

    val busesOrdenados = remember { mutableStateListOf<Triple<String, String, Int>>() }

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

            val scrollState = rememberScrollState()
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)) {

                CabeceraHorarios("Buses en circulación")

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
                                        //val tiempo = getTiempoDesdeHasta(origen, destino)
                                        val tiempo = -1 //TODO: ÉSTO ES SOLO PARA NO REVENTAR A LLAMADAS A LA API (QUITAR CUANDO SE QUIERA FUNCIONAL)
                                        if (tiempo != null) {
                                            tiempoLlegadaState.value = tiempo
                                            busesOrdenados.add(Triple(nombreLinea, numeroLinea, tiempo))
                                            busesOrdenados.sortBy { it.third }

                                        } else
                                            tiempoLlegadaState.value = -1

                                        Log.d("TiempoRuta", "Tiempo estimado: $tiempo")
                                    } catch (e: Exception) {
                                        Log.e("TiempoRuta", "Error al obtener tiempo: ${e.message}")
                                    }



                                }

                            }else
                                Log.e("TiempoRuta", "Error al obtener la ubicación de la parada,")


                        }
                    }

                    for ((nombreLinea, numeroLinea, tiempo) in busesOrdenados) {
                        BusItem(
                            route = nombreLinea,
                            lineNumber = numeroLinea,
                            timeMinutes = tiempo,

                        )
                    }
                    listIsReady.value = true
                }

                CabeceraHorarios("Próximos horarios")

                //GENERACIÓN DE LOS HORARIOS PRESTABLECIDOS (EN LA BD)

                val listaOrdenada = mutableListOf<Triple<String, String, LocalTime>>() // (numeroLinea, nombreLinea, hora)
                for ((linea, horario) in viewModel.horariosParadaMap) {

                    val lineaRTM: LineaRTModel? = viewModel.lineasRTModel.value?.find { it.id == linea }
                    var nombreLinea = "¿¿¿Linea Desconocida???"
                    var numeroLinea = "¿¿L??"

                    if (lineaRTM != null) {
                        nombreLinea = lineaRTM.nombreLinea
                        numeroLinea = lineaRTM.number
                    }

                    val horaActual: LocalTime = LocalTime.now()
                    val horas = horario.getHorariosFrom(horaActual,3)
                    for (hora in horas){
                    BusItem(lineNumber = numeroLinea,nombreLinea, timeMinutes = -2, hour = hora.toString())

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
    timeMinutes: Int,
    hour : String = ""

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 3.dp)

    ) {
        // Caja con número de línea
        Box(
            modifier = Modifier
                .size(50.dp, 40.dp)
                .background(LineColors.AZUL.color, shape = RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lineNumber,
                fontWeight = FontWeight.Bold,
                color = Color.White
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

        if (timeMinutes == -2)
            Text(
                    text = "$hour ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        else
        Text(
            text = "En $timeMinutes minutos",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(end = 5.dp))

    }

    HorizontalDivider()
}

@Composable
fun CabeceraHorarios(titulo: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFF4A4A4A))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C2C2C)
            )

        }
    }

    Spacer(modifier = Modifier.padding(bottom = 10.dp))
}
