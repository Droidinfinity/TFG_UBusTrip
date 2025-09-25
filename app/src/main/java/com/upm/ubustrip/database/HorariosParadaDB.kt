package com.upm.ubustrip.database

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.upm.ubustrip.models.HorarioParadaModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//ESTA CLASE SE ENCARGA DE OBTENER LOS HORARIOS DE LA BASE DE DATOS DEPENDIENDO DE LO QUE SE QUIERA

class HorariosParadaDB() {

    companion object{

        var result : String? = null

        suspend fun getHorariosSemana(idLinea: String, idParada: String): HorarioParadaModel {
            return getHorariosFromDatabase(idLinea, idParada, "semana")
        }

        suspend fun getHorariosSabado(idLinea: String, idParada: String): HorarioParadaModel {
            return getHorariosFromDatabase(idLinea, idParada, "sabado")
        }

        suspend fun getHorariosDomingo(idLinea: String, idParada: String): HorarioParadaModel {
            return getHorariosFromDatabase(idLinea, idParada, "domingo_festivo")
        }

        private suspend fun getHorariosFromDatabase(idLinea: String, idParada: String, tipo: String): HorarioParadaModel {
            val result = AppDatabase.get("horariosParada/${idParada}/${idLinea}/$tipo")

            val gson = Gson()
            val jsonObject = gson.fromJson(result, JsonObject::class.java)
            val horariosJsonArray = jsonObject.getAsJsonArray("horarios")
            val horariosList = horariosJsonArray.map { it.asString }

            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val tiempos = horariosList.map { LocalTime.parse(it, formatter) }

//            Log.d("HParadaDB", "RESULTADO [$tipo]: $tiempos")

            return HorarioParadaModel(frecuencias = tiempos)
        }

    }
}