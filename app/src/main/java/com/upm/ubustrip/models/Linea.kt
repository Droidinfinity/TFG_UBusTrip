package com.upm.ubustrip.models

import android.util.Log
import com.google.gson.Gson
import com.upm.ubustrip.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

data class LineaData(
    val _id: String,
    val lineaId: String,
    val segmentos: List<SegmentoData>,
    val direccion: String,
    val nombre: String
)

data class SegmentoData(
    val polyline: String,
    val paradaId: String
)

class LineaRTModel(id: String) {

    var id = ""
    var nombreLinea: String = ""
    var lineaId: String = "" //numero de parada
    var segmentosLinea = mutableListOf<Segmento>()
    var direccion = ""
    private  var lineaData: LineaData? = null

    init {

        this.id = id

        CoroutineScope(Dispatchers.Main).launch {
            lineaData = getLineaById(id)
            if (lineaData != null)
                asignarVariables(lineaData!!)

        }


    }

    //Obtenemos la linea mediante su id de mongo
    private suspend fun getLineaById(id: String): LineaData? {

        val responseBody = AppDatabase.get("lineas/$id")

        return if (responseBody != null) {
            Log.d("linea", "Respuesta obtenida: $responseBody")
            decodeJSON(responseBody)
        } else {
            Log.e("linea", "No se pudo obtener la respuesta")
            null
        }
    }

    private fun decodeJSON(json: String): LineaData {

        val gson = Gson()
        val lineaData = gson.fromJson(json, LineaData::class.java)
        return lineaData

    }

    private fun asignarVariables(lineaData: LineaData) {

        Log.d("linea", "mmm: $lineaData")
        this.nombreLinea = lineaData.nombre
        this.lineaId = lineaData.lineaId
        this.direccion = lineaData.direccion

        for ((index, segmento) in lineaData.segmentos.withIndex()) {

            var esUltimoSegmento = false

            if (index == lineaData.segmentos.lastIndex) { //indicamos si es el último segmento
                esUltimoSegmento = true
            }
            if (segmento.polyline.length<=500) {
                val s = Segmento(
                    polyline = segmento.polyline,
                    esSegmentoFinal = esUltimoSegmento,
                    numeroSegmento = index
                )
                segmentosLinea.add(s)
            }

        }

    }


}