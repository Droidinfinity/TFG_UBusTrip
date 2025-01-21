package com.upm.ubustrip.database


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


/**
 *Gestiona las peticiones básicas (GET, POST, UPDATE,DELETE)  que se van a realizar a la base de datos asociada a al APP
 */
class AppDatabase {


    /**
     *Funciones estáticas para llamar a las operaciones CRUD
     */
    companion object MongoCRUD{


        private const val URL = "http://10.0.2.2:3000/api"

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