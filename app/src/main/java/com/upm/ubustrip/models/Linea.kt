package com.upm.ubustrip.models

import android.util.Log
import com.google.gson.Gson
import com.upm.ubustrip.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Modelo en tiempo real de una línea.
 *
 * @property id Identificador único de la línea (mongo).
 * @property nombreLinea Nombre de la línea.
 * @property lineaId Identificador asociado a la línea.
 * @property segmentosLinea Lista de segmentos de la línea.
 * @property direccion Dirección de la línea (ida o  vuelta).
 */
class LineaRTModel(
    var id: String,
    var nombreLinea: String = "",
    var lineaId: String = "",
    var segmentosLinea: MutableList<Segmento> = mutableListOf(),
    var direccion: String = ""
) {

    companion object {
        /**
         * Crea una instancia de [LineaRTModel] obteniendo mediante los datode la base de datos de remota.
         *
         * @param id Identificador de la línea.
         * @return Una instancia de [LineaRTModel].
         */
        suspend fun create(id: String): LineaRTModel {
            val responseBody = AppDatabase.get("lineas/$id")
            val lineaData = if (responseBody != null) {
                Log.d("linea", "Respuesta obtenida: $responseBody")
                decodeJSON(responseBody)
            } else {
                Log.e("linea", "No se pudo obtener la respuesta")
                null
            }

            return if (lineaData != null) {
                LineaRTModel(id).apply { //similar al await
                    asignarVariables(lineaData)
                }
            } else {
                LineaRTModel(id)
            }
        }

        /**
         * Decodifica un JSON en un objeto de tipo [LineaData].
         *
         * @param json Cadena JSON con los datos de la línea.
         * @return Una instancia de [LineaData] decodificada del JSON.
         */
        private fun decodeJSON(json: String): LineaData {
            val gson = Gson()
            return gson.fromJson(json, LineaData::class.java)
        }
    }

    /**
     * Asigna los valores de los datos de la línea a las propiedades de la clase.
     *
     * @param lineaData Datos de la línea en formato [LineaData].
     */
    private fun asignarVariables(lineaData: LineaData) {
        Log.d("linea", "Datos de la línea: $lineaData")
        this.nombreLinea = lineaData.nombre
        this.lineaId = lineaData.lineaId
        this.direccion = lineaData.direccion

        //asignamos los segmentos, el último segmento se le activa la flag de último segmento
        for ((index, segmento) in lineaData.segmentos.withIndex()) {
            val esUltimoSegmento = index == lineaData.segmentos.lastIndex
            val s = Segmento(
                polyline = segmento.polyline,
                esSegmentoFinal = esUltimoSegmento,
                numeroSegmento = index,
                paradaId = segmento.paradaId
            )
            segmentosLinea.add(s)
        }
    }
}
