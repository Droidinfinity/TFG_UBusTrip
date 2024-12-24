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

}