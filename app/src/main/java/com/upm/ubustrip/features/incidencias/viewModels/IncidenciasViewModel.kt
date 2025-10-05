package com.upm.ubustrip.features.incidencias.viewModels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class IncidenciasViewModel : ViewModel(){

    private var _showIncidencia = mutableStateOf(true)
    var showIncidencia: State<Boolean> = _showIncidencia


    fun showIncidenciaDialog() {
        _showIncidencia.value = true
    }

    fun hideIncidenciaDialog() {
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

}

