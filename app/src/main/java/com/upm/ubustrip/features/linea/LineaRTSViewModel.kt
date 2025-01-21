package com.upm.ubustrip.features.linea

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.LineaRTModel
import com.upm.ubustrip.models.ParadaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class LineaRTSViewModel : ViewModel() {


    var busesLinea = mutableListOf<Bus>()

    lateinit var lineasRTModel : MutableList<LineaRTModel>
    lateinit var paradaModel: ParadaModel

    // Variable mutable interna
    private val _selectedTabIndex = mutableStateOf(0)

    // Variable pública de solo de lectura
    val selectedTabIndex: State<Int> = _selectedTabIndex

    // Función para cambiar el valor del valor seleccionado
    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun initPorParada(id: String, nombreParada: String, lineasIds: MutableList<String>, numeroParada: String) {
        paradaModel = ParadaModel(
            paradaId = id,
            nombreParada = nombreParada,
            lineas = lineasIds,
            numeroParada = numeroParada
        )
        lineasRTModel = mutableListOf<LineaRTModel>() //inicializamos la lista

        viewModelScope.launch {
            for (lineaId in paradaModel.lineas) {
                val linea = LineaRTModel.create(lineaId) // Espera a que la línea se cargue completamente
                Log.d("lineaVM", "Línea cargada: ${linea.nombreLinea}")
                withContext(Dispatchers.Main) {
                    if(linea.nombreLinea != "")
                    lineasRTModel.add(linea) // Agrega la línea a la lista después de cargarla
                }
            }
        }
    }

}