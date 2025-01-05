package com.upm.ubustrip.features.linea

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.LineaRTModel


class LineaRTSViewModel : ViewModel() {

    var busesLinea = mutableListOf<Bus>()
    //var linea = LineaRTModel()
    var lineaId = ""
    var paradaId = ""


    // Variable mutable interna
    private val _selectedTabIndex = mutableStateOf(0)

    // Variable pública de solo de lectura
    val selectedTabIndex: State<Int> = _selectedTabIndex

    // Función para cambiar el valor del valor seleccionado
    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

}