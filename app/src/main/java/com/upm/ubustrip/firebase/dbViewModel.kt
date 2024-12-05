package com.upm.ubustrip.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class dbViewModel : ViewModel() {

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

    fun generarMatricula(): String {
        val letrasIniciales = ('J'..'M').toList() // Letras iniciales entre J y M
        val consonantes = ('B'..'Z').filterNot { it in listOf('A', 'E', 'I', 'O', 'U') } // Solo consonantes
        val numeros = (1000..9999).random() // Número de 4 dígitos
        val letraInicial = letrasIniciales.random()
        val letrasFinalesAleatorias = List(2) { consonantes.random() }.joinToString("") // Dos consonantes aleatorias
        return "$numeros$letraInicial$letrasFinalesAleatorias"
    }

}