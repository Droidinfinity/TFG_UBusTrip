package com.upm.ubustrip.models

import android.util.Log
import com.google.gson.Gson
import com.upm.ubustrip.database.AppDatabase

data class ParadaModel(val paradaId : String, val nombreParada : String, val lineas : MutableList<String>,val numeroParada : String) {
    
    init {

    }



}
class Parada(id: String) {


    //Obtenemos la linea mediante su id de mongo
    private suspend fun getParadaById(id: String) {

        val responseBody = AppDatabase.get("paradas/$id")
        if (responseBody!=null) {
            val model = decodeJSON(responseBody)

        }

    }

    private fun decodeJSON(json: String): ParadaModel {

        val gson = Gson()
        val paradaModel = gson.fromJson(json, ParadaModel::class.java)
        return paradaModel

    }

}