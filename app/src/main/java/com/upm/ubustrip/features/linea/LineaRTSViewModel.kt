package com.upm.ubustrip.features.linea

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Segmento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LineaRTSViewModel : ViewModel() {

    var busesLinea = mutableListOf<Bus>()
    // Variable mutable interna
    private val _selectedTabIndex = mutableStateOf(0)

    // Variable pública solo de lectura
    val selectedTabIndex: State<Int> = _selectedTabIndex

    // Función para cambiar el valor
    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

}