package com.upm.ubustrip.features.incidencias.viewModels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.models.HorarioParadaModel
import com.upm.ubustrip.models.IncidenciaDTO
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class IncidenciasViewModel : ViewModel(){

    private var _showIncidencia = mutableStateOf(true)
    var incidenciaShowed: State<Boolean> = _showIncidencia

    private var _showScafold = mutableStateOf(false)
    var showScafold: State<Boolean> = _showScafold

    var incidencia by mutableStateOf(
        IncidenciaDTO(
            titulo = "NULL", _id = "NULL", nivel = 0,
            activa = true, imgB64 = "", idLinea = "null",
            mensaje = "null", paradasAfectadas = "null",
            alternativa = "null", comienzo = "null", final = "null"
        )
    )
        private set

    fun showIncidenciaDialog() {
        _showIncidencia.value = true
    }

    fun hideIncidenciaDialog() {
        _showIncidencia.value = false
    }

    fun loadScafoldScreen() {
        _showIncidencia.value = true
    }

    fun hideScafoldScreen() {
        _showIncidencia.value = false
    }

    //Dependiendo de que color sea la TopBar entonces el texto será negro o blanco
    fun setColorTítulo(id : Int) : Color{

        var color = Color.Black

        if(id == 0 || id ==3)
            color = Color.White

        return color
    }

    fun decodeBase64ToBitmap(base64: String): Bitmap? {
        return try {
            val byteArray = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setBackground(nivelIncidencia: Int) : Color{

        var color = Color.White

        when(nivelIncidencia){

            0-> color = Color(0xff3c9341).copy(alpha = 0.3f)
            1-> color = Color(0xffb8c534).copy(alpha = 0.3f)
            2-> color = Color(0xFFE39B30).copy(alpha = 0.3f)
            3-> color = Color(0xFFcc3d1a).copy(alpha = 0.3f)

        }

        return  color
    }

    suspend fun getIncidenciasPorId(idLinea: String): IncidenciaDTO {
        val result = AppDatabase.get("incidencias/$idLinea")
        val gson = Gson()
        return gson.fromJson(result, IncidenciaDTO::class.java)
    }
     fun initInciidencia(idLinea: String){

        viewModelScope.launch {
            val inci = getIncidenciasPorId(idLinea)
            incidencia = inci
            if (inci.activa) hideIncidenciaDialog()
            else showIncidenciaDialog()
        }

    }
    fun nivelIncidenciaToText(nivel: Int): String{

        var text = "desconocido"

        when(nivel){

            0-> text = "INFORMATIVO"
            1->text = "LEVE"
            2->text = "MEDIO"
            3-> text = "GRAVE"

        }

        return text

    }

}

