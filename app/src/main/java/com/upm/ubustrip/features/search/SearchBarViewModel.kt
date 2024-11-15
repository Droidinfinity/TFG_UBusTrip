package com.upm.ubustrip.features.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchBarViewModel : ViewModel() {

    private val _desplegado = mutableStateOf(false)
    private val _previousState = mutableStateOf(false)
    val desplegado: State<Boolean> = _desplegado
    val previousState: State<Boolean> = _previousState


    // Función para actualizar el estado compartido
    fun updateDesplegadoState(newState: Boolean) {

        _previousState.value = _desplegado.value
        _desplegado.value = newState
    }


}