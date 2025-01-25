package com.upm.ubustrip.models

import android.util.Log
import com.google.gson.Gson
import com.upm.ubustrip.database.AppDatabase

data class ParadaModel(
    val paradaId: String,
    val nombreParada: String,
    val lineas: MutableList<String>,
    val numeroParada: String
)

class Parada(
    var id: String? ,
    var nombreParada: String = "",
    var numeroParada: String  = ""
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

        //asignamos las lineas, si hay mas de una
        for (linea in paradaModel.lineas) {
            lineasParada.add(linea)
        }
    }




}