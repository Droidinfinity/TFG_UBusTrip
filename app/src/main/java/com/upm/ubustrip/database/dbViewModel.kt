package com.upm.ubustrip.database


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import okhttp3.Request


data class Bus(val busId: String, val concesion: String)


class DBViewModel : ViewModel() {

    val db = Firebase.firestore

    val firestore = FirebaseFirestore.getInstance()

    // Especifica la colección
    val collectionRef = firestore.collection("ColeccionBus")

    // Especifica el ID manualmente
    val customId = "user_12345"

    // Datos a guardar
    val userData = mapOf(
        "nombre" to "Carlos",
        "email" to "carlos@example.com",
        "edad" to 28
    )


    init {

// Crear un cliente de OkHttp
        val client = OkHttpClient()

        // Crear la solicitud GET
        val request = Request.Builder()
            .url("http://10.0.2.2:3000/api/buses") // Cambia por la URL de tu API
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

    fun leerDBF() {
        collectionRef.document("1034MPZ").get().addOnSuccessListener { document ->

            Log.d("Firebase", document.data.toString())

        }
    }

    fun generarMatricula(): String {
        val letrasIniciales = ('J'..'M').toList() // Letras iniciales entre J y M
        val consonantes =
            ('B'..'Z').filterNot { it in listOf('A', 'E', 'I', 'O', 'U') } // Solo consonantes
        val numeros = (1000..9999).random() // Número de 4 dígitos
        val letraInicial = letrasIniciales.random()
        val letrasFinalesAleatorias =
            List(2) { consonantes.random() }.joinToString("") // Dos consonantes aleatorias
        return "$numeros$letraInicial$letrasFinalesAleatorias"
    }

    fun anadirMatriculaBD() {

        for (i in 1..20) {
            // Generar un ID único usando la matrícula
            val matricula = generarMatricula()
            val busData = mapOf(
                "matricula" to matricula,
                "concesion" to "CRTM",
                "empresa" to if (i % 2 == 0) "Avanza" else "Alsa", // Alternar entre Avanza y Alsa
                "numero" to (1000..9999).random(), // Número identificativo de 4 dígitos
                "tipo" to 1 // Tipo fijo
            )

            // Guardar documento con ID = matrícula
            collectionRef.document(matricula).set(busData)
                .addOnSuccessListener {
                    Log.d("Firestore", "Documento añadido correctamente con matrícula: $matricula")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error al añadir el documento", e)
                }
        }
    }

}