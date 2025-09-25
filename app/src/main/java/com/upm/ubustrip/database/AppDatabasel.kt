package com.upm.ubustrip.database


import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 *Gestiona las peticiones básicas (GET, POST, UPDATE,DELETE)  que se van a realizar a la base de datos asociada a al APP
 */
class AppDatabase {


    /**
     *Funciones estáticas para llamar a las operaciones CRUD
     */
    companion object MongoCRUD{
        private val localhost = "10.0.2.2"
        private val redURL = "10.10.70.59" //Si quiero testearlo en dispositivo físico (PONER LA IPv4 del PC O wifi)
        private val URL = "http:${localhost}:3000/api"
         val REALTIME_DATABASE_URL = "https://ubustrip-81feb-default-rtdb.europe-west1.firebasedatabase.app"

        /**
         *Realiza una petición get a la Api con el endpoint aportado
         * @param endpoint ruta de la api a la cual queremos realizar la petición
         * @return json en formato string si recibe contenido, en caso contrario null
         */
        suspend fun get(endpoint: String): String? {
            val client = OkHttpClient()

            // Crear la solicitud GET
            val request = Request.Builder()
                .url("${URL}/$endpoint")
                .build()

            // Realizar la solicitud en el contexto de IO
            return withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()

                    // Verificar si la respuesta es exitosa y retornar el cuerpo
                    if (response.isSuccessful) {
                        //Log.d("mongo","c")
                        response.body?.string()

                    } else {
                        //Log.d("mongo", "Error: ${response.code}")
                        null
                    }
                } catch (e: Exception) {
                    //Log.d("mongo", "Excepción: ${e.message}", e)
                    null
                }
            }
        }


    }


}