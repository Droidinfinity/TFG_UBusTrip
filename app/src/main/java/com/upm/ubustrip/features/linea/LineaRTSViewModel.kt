package com.upm.ubustrip.features.linea

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.LineaModel
import com.upm.ubustrip.models.Segmento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LineaRTSViewModel : ViewModel() {

    var busesLinea = mutableListOf<Bus>()
    var linea = LineaModel()
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