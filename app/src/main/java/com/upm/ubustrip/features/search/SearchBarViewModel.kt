package com.upm.ubustrip.features.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.models.HorarioParadaModel
import com.upm.ubustrip.models.ParadaResultDTO
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SearchBarViewModel : ViewModel() {

    private val _desplegado = mutableStateOf(false)
    private val _previousState = mutableStateOf(false)
    val desplegado: State<Boolean> = _desplegado
    val previousState: State<Boolean> = _previousState
    private val _text = mutableStateOf("")
    var text : State<String> = _text

    private val _lista = mutableStateListOf<ParadaResultDTO>()
    val lista: List<ParadaResultDTO> get() = _lista

    // Función para actualizar el estado compartido
    fun updateDesplegadoState(newState: Boolean) {

        _previousState.value = _desplegado.value
        _desplegado.value = newState
    }

    fun updateText(newText: String){

        _text.value = newText

    }

    suspend fun setParadasEnLaLista(nombre: String){

        _lista.clear()
        _lista.addAll(getParadasPorNombre(nombre))

    }

    suspend fun getParadasPorNombre(nombre: String): List<ParadaResultDTO>{

        val result = AppDatabase.get("paradas/nombre/${nombre}")
        var listaVacia : List<ParadaResultDTO> = emptyList()


        if (result != null)
            return transformarJSON(result)

        return listaVacia

    }
    private fun transformarJSON(json: String) : List<ParadaResultDTO> {

        val gson = Gson()
        val jsonArray = gson.fromJson(json, JsonArray::class.java)
        val lista = mutableListOf<ParadaResultDTO>()

        for (element in jsonArray) {
            val obj = element.asJsonObject

            val id = obj["_id"]?.asString ?: ""
            val nombre = obj["nombreParada"]?.asString ?: ""
            val numeroParada = obj["idParada"]?.asString ?: ""

            lista.add(
                ParadaResultDTO(
                    id = id,
                    nombre = nombre,
                    numeroParada = numeroParada
                )
            )
        }

        return lista

    }

}