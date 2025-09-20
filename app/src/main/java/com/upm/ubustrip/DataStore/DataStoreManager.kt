package com.upm.ubustrip.DataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upm.ubustrip.models.ParadaFavorita
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context,name: String,s: String) {

    val Context.dataStore by preferencesDataStore(name = "app_prefs")

    private val KEY_FAVORITOS = stringPreferencesKey(s)
    private val gson = Gson()

    // Guardar lista de paradas favoritas
    suspend fun guardarFavoritos(lista: List<ParadaFavorita>) {
        val json = gson.toJson(lista)
        context.dataStore.edit { prefs ->
            prefs[KEY_FAVORITOS] = json
        }
    }

    // Leer lista de paradas favoritas
    fun leerFavoritos(): Flow<List<ParadaFavorita>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[KEY_FAVORITOS]
            if (json.isNullOrEmpty()) {
                emptyList()
            } else {
                val type = object : TypeToken<List<ParadaFavorita>>() {}.type
                gson.fromJson(json, type)
            }
        }
    }

    // Eliminar favoritos
    suspend fun eliminarFavoritos() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_FAVORITOS)
        }
    }
}
