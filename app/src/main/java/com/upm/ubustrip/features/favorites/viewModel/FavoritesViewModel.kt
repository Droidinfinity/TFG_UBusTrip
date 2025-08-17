package com.upm.ubustrip.features.favorites.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.upm.ubustrip.DataStore.DataStoreManager
import com.upm.ubustrip.models.ParadaFavorita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = DataStoreManager(application)

    // Estado con la lista de favoritos en memoria
    private val _favorites = MutableStateFlow<List<ParadaFavorita>>(emptyList())
    val favorites: StateFlow<List<ParadaFavorita>> = _favorites.asStateFlow()

    init {
        // Cargar favoritos desde DataStore al crear el ViewModel
        viewModelScope.launch {
            //testADD()
         //   addFavorite(ParadaFavorita(id = "634563464gfg", nombre = "Parada de ejemplo ", numeroParada = "7344"))

            dataStoreManager.leerFavoritos().collect { list ->
                _favorites.value = list
            }

        }

    }

    // Guardar un nuevo favorito
    fun addFavorite(item: ParadaFavorita) {
        viewModelScope.launch {
            val nuevaLista = _favorites.value.toMutableList().apply {
                add(item)
            }
            dataStoreManager.guardarFavoritos(nuevaLista)
        }
    }

    // Eliminar un favorito
    fun removeFavorite(item: ParadaFavorita) {
        viewModelScope.launch {
            val nuevaLista = _favorites.value.toMutableList().apply {
                removeIf { it.id == item.id } // elimina por id
            }
            dataStoreManager.guardarFavoritos(nuevaLista)
        }
    }

    // Vaciar todos los favoritos
    fun clearFavorites() {
        viewModelScope.launch {
            dataStoreManager.eliminarFavoritos()
            _favorites.value = emptyList()
        }
    }


    suspend fun testADD() {
        val lista = (0..5).map { i ->
            ParadaFavorita(
                id = "6760bb3dceaa35d371867bf8",
                nombre = "Parada de ejemplo $i",
                numeroParada = "7344"
            )
        }
        dataStoreManager.guardarFavoritos(lista)
    }


}
