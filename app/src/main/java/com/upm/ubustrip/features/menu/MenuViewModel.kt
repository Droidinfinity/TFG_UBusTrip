package com.upm.ubustrip.features.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.models.LineaRTModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    init {


    }

    // Estado mutable que mantiene el título actual
    private val _barTitle = mutableStateOf("Favoritos")
    val barTitle: State<String> = _barTitle

    // Función para actualizar el título
    fun updateTitle(newTitle: String) {
        _barTitle.value = newTitle
    }

}