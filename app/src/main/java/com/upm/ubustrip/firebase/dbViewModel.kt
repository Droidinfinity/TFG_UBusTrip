package com.upm.ubustrip.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class dbViewModel : ViewModel() {

    val db = Firebase.firestore

    init {
        val user = hashMapOf(
            "id" to "7402HFT",
            "name" to "Juan",
            "email" to "juan@example.com",
            "age" to 25
        )
        db.collection("ColeccionBus").add(user)
            .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Documento añadido con ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al añadir documento", e)
            }
    }
}