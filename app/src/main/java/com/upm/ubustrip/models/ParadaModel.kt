package com.upm.ubustrip.models

import android.util.Log
import com.google.gson.Gson
import com.upm.ubustrip.database.AppDatabase

data class Ubicacion(
    val long: String,
    val lat: String
)

data class ParadaModel(
    val paradaId: String,
    val nombreParada: String,
    val lineas: MutableList<String>,
    val numeroParada: String,
    val ubicacion: Ubicacion?, // puede ser null si no está
    var stops: Map<String, Int>?
)

class Parada(
    var id: String? ,
    var nombreParada: String = "",
    var numeroParada: String  = "",
    var ubicacion: Ubicacion? = null,
    var stops: Map<String, Int>? = null
) {

    var paradaId : String? = null
    val lineasParada: MutableList<String> = mutableListOf()

    companion object {

        suspend fun create(id: String): Parada {
            val responseBody = AppDatabase.get("paradas/$id")
            val paradaData = if (responseBody != null) {
                Log.d("parada", "Respuesta obtenida: $responseBody")
                decodeJSON(responseBody)
            } else {
                Log.e("linea", "No se pudo obtener la respuesta")
                null
            }

            return if (paradaData != null) {
                Parada(id).apply { //similar al await
                    asignarVariables(paradaData)
                }
            } else {
                Parada(id)
            }
        }

        private fun decodeJSON(json: String): ParadaModel {

            val gson = Gson()
            val paradaModel = gson.fromJson(json, ParadaModel::class.java)
            return paradaModel
        }



    }

    private fun asignarVariables(paradaModel: ParadaModel) {
        Log.d("parada", "Datos de la parada: $paradaModel")
        this.nombreParada = paradaModel.nombreParada
        this.numeroParada = paradaModel.numeroParada
        this.paradaId = id
        this.ubicacion = paradaModel.ubicacion
        this.stops = paradaModel.stops
        Log.d("UBICA","nombre: ${this.nombreParada} ${ubicacion?.long} y ${ubicacion?.lat}")
        Log.d("UBICA","STOPS: ${this.stops.toString()}")
        //asignamos las lineas, si hay mas de una
        for (linea in paradaModel.lineas) {
            lineasParada.add(linea)
        }
    }




}