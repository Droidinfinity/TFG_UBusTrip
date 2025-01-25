package com.upm.ubustrip.features.linea

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.LineaRTModel
import com.upm.ubustrip.models.Parada
import com.upm.ubustrip.models.ParadaModel
import com.upm.ubustrip.viewModels.ParadaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class LineaRTSViewModel : ViewModel() {
    var busesLinea = mutableListOf<Bus>()

    // Usamos MutableState para observar cambios en Composables
    private val _parada = mutableStateOf<Parada?>(null)
    val parada: State<Parada?> = _parada

    private val _lineasRTModel =  mutableStateOf<MutableList<LineaRTModel>?>(mutableListOf())
    val lineasRTModel : State<MutableList<LineaRTModel>?> = _lineasRTModel

    private var _lineasRTModelMod = mutableStateOf<Int>(-1)
    var lineasRTModelMod = _lineasRTModelMod

    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun initPorParada(id: String) {

        //limpieza
        _parada.value = null
        _lineasRTModel.value = mutableListOf()
        _lineasRTModelMod.value = -1


        viewModelScope.launch {
            val paradaCargada = ParadaViewModel().getParadaById(id)
            _parada.value = paradaCargada // Actualiza el estado observado

            if (_parada.value !=null )
            for (lineaId in parada.value!!.lineasParada) {
                val linea = LineaRTModel.create(lineaId) // Espera a que la línea se cargue completamente
                Log.d("lineaVM", "Línea cargada: ${linea.nombreLinea}")
                withContext(Dispatchers.Main) {

                        lineasRTModel.value?.add(linea) // Agrega la línea a la lista después de cargarla
                        lineasRTModelMod.value++

                }
            }

        }
    }
}