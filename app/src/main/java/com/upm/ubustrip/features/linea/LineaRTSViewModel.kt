package com.upm.ubustrip.features.linea

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.LineaRTModel
import com.upm.ubustrip.models.ParadaModel


class LineaRTSViewModel : ViewModel() {


    var busesLinea = mutableListOf<Bus>()

    lateinit var lineas : MutableList<LineaRTModel>
    lateinit var paradaModel: ParadaModel

    // Variable mutable interna
    private val _selectedTabIndex = mutableStateOf(0)

    // Variable pública de solo de lectura
    val selectedTabIndex: State<Int> = _selectedTabIndex

    // Función para cambiar el valor del valor seleccionado
    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun initPorParada(id: String, nombreParada: String, lineas: MutableList<String>, numeroParada: String) {

        paradaModel = ParadaModel(
            paradaId = id,
            nombreParada = nombreParada,
            lineas = lineas,
            numeroParada = numeroParada
        )

        for(l in paradaModel.lineas){
            val linea = LineaRTModel(l)
            Log.d("lineaVM", linea.nombreLinea)
           // this.lineas.add(linea)
        }

    }

}