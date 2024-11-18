package com.upm.ubustrip.features.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

class MenuViewModel : ViewModel() {

    // Estado mutable que mantiene el título actual
    private val _barTitle = mutableStateOf("Favoritos")
    val barTitle: State<String> = _barTitle

    // Función para actualizar el título
    fun updateTitle(newTitle: String) {
        _barTitle.value = newTitle
    }

}