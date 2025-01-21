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
    paradaId : String? = null
) {

    var segmento = mutableListOf<Coordenada>()
    lateinit var parada : Parada
    private val paradaViewModel = ParadaViewModel()

    var esSegmentoFinal: Boolean = false
    var numeroSegmento: Int = -1

    init {

        val listaCoordenadas = Polyline.decode(polyline) //decodificamos la polyline

        this.segmento =
            toCordenadasList(listaCoordenadas) //lo convertimos a una lista de tipo Coordenadas

        //indicamos si es segmento final

        this.esSegmentoFinal = esSegmentoFinal

        //asignamos su numero de segmento
        this.numeroSegmento = numeroSegmento

        if (paradaId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                parada = paradaViewModel.getParadaById(paradaId)
                Log.d("Segmento", "Parada cargada: ${parada?.nombreParada} $paradaId")
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


    fun buscarBuses(buses: MutableList<Bus>): MutableList<Pair<Bus, Coordenada>> {

        val busesSegmento = mutableListOf<Pair<Bus, Coordenada>>()
        val radio = 15.0 //radio de 15 metros, consideramos impresición en el GPS

        for (bus in buses) {

            var coordenadaMasCercana: Pair<Double, Coordenada> = Pair(999999.0, Coordenada(longitud = 0.0, latitud = 0.0))

            if (bus.ultimoSegmentoVisitado <= this.numeroSegmento) {

                for (cord in segmento) {

                    val d = CordenadasUtils.coordenadaDentroDeRadio(radio = radio, bus.ubicacion, cord)
                    if (d >= 0 && d < coordenadaMasCercana.first) //está dentro del radio y si esta nueva coordenada es la mas cercana...
                        coordenadaMasCercana = Pair(d, cord)
                }

                if (coordenadaMasCercana.first<999999.0){ //si es menor que este numero es que el bus esta en ese segmento

                    busesSegmento.add(Pair(bus,coordenadaMasCercana.second))


                }

            }

        }

        for (busE in busesSegmento)
            buses.remove(busE.first)

        return busesSegmento

    }


}