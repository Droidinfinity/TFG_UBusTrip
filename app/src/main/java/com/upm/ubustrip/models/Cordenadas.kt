package com.upm.ubustrip.models

import android.util.Log
import kotlin.math.*

//CLASE COORDENADA
data class Coordenada(var longitud: Double, var latitud: Double) {


}

//TRAPICHEOS QUE PODEMOS HACER CON LAS COORDENADAS
class CordenadasUtils() {

    companion object {

        //Usamos la formula de Haversine para el calculo de distancia entre dos coordenadas (en metros)
        fun distanciaCoordenadasHaversine(co1: Coordenada, co2: Coordenada): Double {

            val radioTierra = 6371000.0 // Radio de la Tierra en metros

            // Convertir grados a radianes
            val dLat = Math.toRadians(co2.latitud - co1.latitud)
            val dLon = Math.toRadians(co2.longitud - co1.longitud)

            val rLat1 = Math.toRadians(co1.latitud)
            val rLat2 = Math.toRadians(co2.latitud)

            // Fórmula del Haversine
            val a = sin(dLat / 2).pow(2) + cos(rLat1) * cos(rLat2) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return radioTierra * c

        }


        fun distanciaCoordenadasHaversineSegmento(segmento: Segmento): Double {


            var distanciaT = 0.0

            for ((index, element) in segmento.segmento.withIndex()) {

                if (index + 1 < segmento.segmento.size) {

                    distanciaT += distanciaCoordenadasHaversine(
                        element,
                        segmento.segmento[index + 1]
                    )

                }

            }

            Log.d("segmento", distanciaT.toString())
            return distanciaT
        }

        //vemos la distancia que hay hasta la coordenada dada
        fun distanciaHaversineHastaCoordenada(segmento: Segmento, cord: Coordenada): Double {

            var distanciaT = 0.0

            for ((index, element) in segmento.segmento.withIndex()) {

                if (element == cord)
                    break

                if (index + 1 < segmento.segmento.size) {

                    distanciaT += distanciaCoordenadasHaversine(
                        element,
                        segmento.segmento[index + 1]
                    )

                }

            }
            Log.d("segmento", "Distancia hasta cord: $distanciaT")

            return distanciaT

        }

        fun coordenadaDentroDeRadio(radio: Double, co1: Coordenada, co2: Coordenada): Double {

            //GUIA
            //co1 centro del radio
            //co2 coordenada a comprobar si está dento del radio

            var distanciaT = -1.0
            val distanciaPunto = distanciaCoordenadasHaversine(co1,co2)

            if (distanciaPunto<=radio)
                distanciaT = distanciaPunto

            return distanciaT
        }


    }


}