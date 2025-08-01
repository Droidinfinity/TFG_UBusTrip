package com.upm.ubustrip.features.MapsUtils
import android.util.Log
import com.upm.ubustrip.BuildConfig
import com.upm.ubustrip.models.Coordenada
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

//LLAMADAS AL API DE GOOGLE MAPS, EN CONCRETO DE RUTAS
class MapsRoutes {


    companion object{

        suspend fun getTiempoDesdeHasta(origen: Coordenada, destino: Coordenada): Int? {
            val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
            val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=${origen.longitud},${origen.latitud}" +
                    "&destination=${destino.longitud},${destino.latitud}" +
                    "&mode=driving" +
                    "&key=$apiKey"

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            return withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    Log.d("TiempoRuta", "Respuesta JSON: $bodyString")

                    val json = JSONObject(bodyString)
                    val routes = json.getJSONArray("routes")
                    if (routes.length() == 0) {
                        Log.w("TiempoRuta", "No se encontraron rutas")
                        return@withContext null
                    }

                    val durationInSeconds = routes
                        .getJSONObject(0)
                        .getJSONArray("legs")
                        .getJSONObject(0)
                        .getJSONObject("duration")
                        .getInt("value") // valor en segundos

                    val minutos = durationInSeconds / 60
                    minutos
                } else {
                    Log.e("TiempoRuta", "Error en la respuesta: ${response.code}")
                    null
                }
            }
        }


    }
}