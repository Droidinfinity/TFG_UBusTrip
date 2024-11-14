package com.upm.ubustrip.features.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchBarViewModel : ViewModel() {

    private val _desplegado = mutableStateOf(false)
    val desplegado: State<Boolean> = _desplegado

    // Función para actualizar el estado compartido
    fun updateDesplegadoState(newState: Boolean) {
        _desplegado.value = newState
    }


}