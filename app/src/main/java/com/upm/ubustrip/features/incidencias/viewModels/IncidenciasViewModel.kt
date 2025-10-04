package com.upm.ubustrip.features.incidencias.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class IncidenciasViewModel : ViewModel(){

    private var _showIncidencia = mutableStateOf(true)
    var showIncidencia: State<Boolean> = _showIncidencia


    fun showIncidenciaDialog() {
        _showIncidencia.value = true
    }

    fun hideIncidenciaDialog() {
        _showIncidencia.value = false
    }

}

