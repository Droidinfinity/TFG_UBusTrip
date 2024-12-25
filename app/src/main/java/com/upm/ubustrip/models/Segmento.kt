package com.upm.ubustrip.models

import android.util.Log
import com.upm.ubustrip.features.polylines.Polyline

class Segmento(
    polyline: String,
    esSegmentoInicial: Boolean = false,
    esSegmentoFinal: Boolean = false,
    numeroSegmento: Int
) {

    var segmento = mutableListOf<Coordenada>()
    var paradaInicial: String? = null
    var paradaFinal: String? = null
    var esSegmentoInicial: Boolean = false
    var esSegmentoFinal: Boolean = false
    var numeroSegmento: Int = -1

    init {

        val listaCoordenadas = Polyline.decode(polyline) //decodificamos la polyline

        this.segmento =
            toCordenadasList(listaCoordenadas) //lo convertimos a una lista de tipo Coordenadas

        //observamos si es segmento intermedio, inicial o final
        this.esSegmentoInicial = esSegmentoInicial
        this.esSegmentoFinal = esSegmentoFinal

        //asignamos su numero de segmento
        this.numeroSegmento = numeroSegmento

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
        val radio = 30.0 //radio de 15 metros, consideramos impresición en el GPS

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