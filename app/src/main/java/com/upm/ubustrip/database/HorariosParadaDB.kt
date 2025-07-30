package com.upm.ubustrip.database

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.upm.ubustrip.models.HorarioParadaModel


//ESTA CLASE SE ENCARGA DE OBTENER LOS HORARIOS DE LA BASE DE DATOS DEPENDIENDO DE LO QUE SE QUIERA

class HorariosParadaDB() {

    companion object{

        var result : String? = null

        //el json completo...
        suspend fun getHorarios(idLinea: String, idParada : String){

            result = AppDatabase.get("horariosParada/${idParada}/${idLinea}")
            Log.d("HParadaDB","RESULTADO $result")

        }

        suspend fun getHorariosSemana(idLinea: String,idParada: String){

            result = AppDatabase.get("horariosParada/${idParada}/${idLinea}/semana")

            val gson = Gson()
            val jsonObject = gson.fromJson(result, JsonObject::class.java)
            val horariosJsonArray = jsonObject.getAsJsonArray("horarios")
            val horariosList = horariosJsonArray.map { it.asString }.toMutableList()

            val horarioParada = HorarioParadaModel(frecuencias = horariosList)

            Log.d("HParadaDB","RESULTADO ${horarioParada.frecuencias.toString()}")

        }

        suspend fun getHorariosSabado(idLinea: String,idParada: String){

            result = AppDatabase.get("horariosParada/${idParada}/${idLinea}/sabado")
            Log.d("HParadaDB","RESULTADO $result")

        }

        suspend fun getHorariosDomingoFestivo(idLinea: String,idParada: String){

            result = AppDatabase.get("horariosParada/${idParada}/${idLinea}/domingo_festivo")
            Log.d("HParadaDB","RESULTADO $result")

        }


    }
}