package com.upm.ubustrip.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upm.ubustrip.models.ParadaFavorita
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
/**
//según he vito y recomiendan, el uso correcto es aplicar un patrón Singuelton para esta clase para evitar qeu mas de una instancia acceda al mismo fichero a la vez
 */
object DataStoreManagerSilguenton {

    // Clave para las preferencias
    private val KEY_FAVORITOS = stringPreferencesKey("favoritos")
    private val gson = Gson()

    // Contexto de aplicación (se inicializa una vez)
    private lateinit var appContext: Context

    // Extension property para DataStore (usando el appContext)
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

    /**
     * IMPORTANTE!!!!
     * Inicializar el DataStoreManager con el contexto de aplicación
     * Debe llamarse una vez en onCreate() en MainActivity, si no la liamos...
     */
    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    /**
     * Guarda la lista de paradas favoritas
     */
    suspend fun guardarFavoritos(lista: List<ParadaFavorita>) {
        val json = gson.toJson(lista)
        appContext.dataStore.edit { preferences ->
            preferences[KEY_FAVORITOS] = json
        }
    }

    /**
     * Leer la lista de paradas favoritas
     */
    fun leerFavoritos(): Flow<List<ParadaFavorita>> {
        return appContext.dataStore.data.map { preferences ->
            val json = preferences[KEY_FAVORITOS]
            if (json.isNullOrEmpty()) {
                emptyList()
            } else {
                try {
                    val type = object : TypeToken<List<ParadaFavorita>>() {}.type
                    gson.fromJson(json, type) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
    }

    /**
     * Eliminar todos los favoritos
     */
    suspend fun eliminarFavoritos() {
        appContext.dataStore.edit { preferences ->
            preferences.remove(KEY_FAVORITOS)
        }
    }

}