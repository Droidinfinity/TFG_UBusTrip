package com.upm.ubustrip.features.linea.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.upm.ubustrip.DataStore.DataStoreManager
import com.upm.ubustrip.DataStore.DataStoreManagerSilguenton
import com.upm.ubustrip.models.ParadaFavorita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TopBarLRTSViewModel(application: Application) : AndroidViewModel(application) {

   // private val dataStoreManager = DataStoreManager(application,"prueba","c")
    // Estado con la lista de favoritos en memoria
    private val _favorites = MutableStateFlow<List<ParadaFavorita>>(emptyList())
    val favorites: StateFlow<List<ParadaFavorita>> = _favorites.asStateFlow()

    init {
        // Cargar favoritos desde DataStore al crear el ViewModel
        viewModelScope.launch {
            try {
                DataStoreManagerSilguenton.leerFavoritos().collect { list ->
                    _favorites.value = list
                }
            } catch (e: Exception) {
                // Manejar error apropiadamente
                Log.e("TopBarLRTSViewModel", "Error loading favorites", e)
            }
        }

    }

    fun addFavorite(item: ParadaFavorita) {

        if (!comprobarSiExisteFav(item)) {
            viewModelScope.launch {
                val list = _favorites.value.toMutableList().apply {
                    add(item)
                }
                DataStoreManagerSilguenton.guardarFavoritos(list)
            }
        }
    }

    // Eliminar un favorito
    fun removeFavorite(item: ParadaFavorita) {
        viewModelScope.launch {
            val nuevaLista = _favorites.value.toMutableList().apply {
                removeIf { it.nombre == item.nombre } // elimina por id
            }
            DataStoreManagerSilguenton.guardarFavoritos(nuevaLista)
        }
    }

     fun comprobarSiExisteFav(item: ParadaFavorita) : Boolean{

        var existe = false

        for(element in _favorites.value){

        if(element.id == item.id)
            existe = true

        }
        return existe
    }
}