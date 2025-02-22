package com.upm.ubustrip.models

import android.util.Log
import com.upm.ubustrip.features.polylines.Polyline
import com.upm.ubustrip.viewModels.ParadaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Segmento(
    polyline: String,
    esSegmentoFinal: Boolean = false,
    numeroSegmento: Int,
    paradaId: String? = null,
    paradaFinalId: String? = null
) {

    var segmento = mutableListOf<Coordenada>()
    var parada: Parada? = null
        private set
    var paradaFinal: Parada? = null
        private set
    private val paradaViewModel = ParadaViewModel()

    var esSegmentoFinal: Boolean = false
    var numeroSegmento: Int = -1

    init {
        val listaCoordenadas = Polyline.decode(polyline) // Decodificamos la polyline
        this.segmento = toCordenadasList(listaCoordenadas) // Convertimos a lista de Coordenadas
        this.esSegmentoFinal = esSegmentoFinal
        this.numeroSegmento = numeroSegmento

        // Cargar parada y parada final
        if (paradaId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    parada = paradaViewModel.getParadaById(paradaId)
                    Log.d("Segmento", "Parada cargada: ${parada?.nombreParada}")
                } catch (e: Exception) {
                    Log.e("Segmento", "Error al cargar parada: ${e.message}")
                }
            }
        }
        if (paradaFinalId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    paradaFinal = paradaViewModel.getParadaById(paradaFinalId)
                    Log.d("Segmento", "Parada final cargada: ${paradaFinal?.nombreParada}")
                } catch (e: Exception) {
                    Log.e("Segmento", "Error al cargar parada final: ${e.message}")
                }
            }
        }
    }

    private fun toCordenadasList(listaCoordenadas: List<Pair<Double, Double>>): MutableList<Coordenada> {
        val segmentoCoordenada = mutableListOf<Coordenada>()
        listaCoordenadas.forEach { element ->
            segmentoCoordenada.add(Coordenada(longitud = element.first, latitud = element.second))
        }
        return segmentoCoordenada
    }

    suspend fun cargarParadas(paradaId: String?, paradaFinalId: String?) {
        if (paradaId != null) {
            parada = paradaViewModel.getParadaById(paradaId)
            Log.d("Segmento", "Parada cargada: ${parada?.nombreParada}")
        }
        if (paradaFinalId != null) {
            paradaFinal = paradaViewModel.getParadaById(paradaFinalId)
            Log.d("Segmento", "Parada final cargada: ${paradaFinal?.nombreParada}")
        }
    }

    fun buscarBuses(buses: MutableList<Bus>): MutableList<Pair<Bus, Coordenada>> {
        val busesSegmento = mutableListOf<Pair<Bus, Coordenada>>()
        val radio = 15.0 // Radio de 15 metros, consideramos imprecisión en el GPS

        for (bus in buses) {
            var coordenadaMasCercana: Pair<Double, Coordenada> =
                Pair(999999.0, Coordenada(longitud = 0.0, latitud = 0.0))

            if (bus.ultimoSegmentoVisitado <= this.numeroSegmento) {
                for (cord in segmento) {
                    val d = CordenadasUtils.coordenadaDentroDeRadio(radio = radio, bus.ubicacion, cord)
                    if (d >= 0 && d < coordenadaMasCercana.first) // Está dentro del radio y es la más cercana
                        coordenadaMasCercana = Pair(d, cord)
                }

                if (coordenadaMasCercana.first < 999999.0) { // Si es menor que este número, el bus está en este segmento
                    busesSegmento.add(Pair(bus, coordenadaMasCercana.second))
                }
            }
        }
        //COMENTADA PORQUE SI ELIMINO EL BUS DE LA REFERENCIA, AL REFRESCAR EL COMPOSABLE VUELVE A EJECUTAR EL MÉTODO
        //(si se elimina el bus desaparecerá...)
        //for (busE in busesSegmento) buses.remove(busE.first)

        return busesSegmento
    }
}
