package com.upm.ubustrip.viewModels

import androidx.lifecycle.ViewModel
import com.upm.ubustrip.models.Parada

class ParadaViewModel : ViewModel() {

    suspend fun getParadaById(id: String): Parada {
        return Parada.create(id)
    }
}