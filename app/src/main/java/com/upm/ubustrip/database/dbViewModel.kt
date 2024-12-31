package com.upm.ubustrip.database


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import okhttp3.Request


class AppDatabase {


    init {

        val client = OkHttpClient()

        // Crear la solicitud GET
        val request = Request.Builder()
            .url("http://10.0.2.2:3000/api/coleccionRutas")
            .build()

        // Ejecutar la solicitud en un hilo secundario
        Thread {
            try {
                // Realizar la petición
                val response = client.newCall(request).execute()

                // Verificar si la respuesta es exitosa
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("mongo", "Respuesta: $responseBody")
                } else {
                    Log.e("mongo", "Error: ${response.code}")
                }
            } catch (e: Exception) {
                Log.e("mongo", "Excepción: ${e.message}", e)
            }
        }.start()


    }

    




}