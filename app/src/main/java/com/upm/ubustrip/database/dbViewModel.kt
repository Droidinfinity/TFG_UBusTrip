package com.upm.ubustrip.database


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class AppDatabase {

    //funciones estáticas
    companion object {

        private val _url = "http://10.0.2.2:3000/api"

        suspend fun get(endpoint: String): String? {
            val client = OkHttpClient()

            // Crear la solicitud GET
            val request = Request.Builder()
                .url("${_url}/$endpoint")
                .build()

            // Realizar la solicitud en el contexto de IO
            return withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()

                    // Verificar si la respuesta es exitosa y retornar el cuerpo
                    if (response.isSuccessful) {
                        Log.d("mongo","c")
                        response.body?.string()

                    } else {
                        Log.e("mongo", "Error: ${response.code}")
                        null
                    }
                } catch (e: Exception) {
                    Log.e("mongo", "Excepción: ${e.message}", e)
                    null
                }
            }
        }


    }


}