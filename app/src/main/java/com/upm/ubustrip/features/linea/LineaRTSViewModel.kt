package com.upm.ubustrip.features.linea

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.upm.ubustrip.database.AppDatabase
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

    val database = FirebaseDatabase.getInstance(AppDatabase.REALTIME_DATABASE_URL)
    val dbRef = database.reference // Referencia raíz de la base de datos

    // Usamos MutableState para observar cambios en Composables
    private val _parada = mutableStateOf<Parada?>(null)
    val parada: State<Parada?> = _parada

     var lineaSeleccionada = mutableStateOf<Int>(0)


    private val _lineasRTModel =  mutableStateOf<MutableList<LineaRTModel>?>(mutableListOf())
    val lineasRTModel : State<MutableList<LineaRTModel>?> = _lineasRTModel

    private var _lineasRTModelMod = mutableStateOf<Int>(-1)
    var lineasRTModelMod = _lineasRTModelMod

    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    //Linite establecido para indicar que un segmento es muy largo para graficarlo en una linea
    val MAX_DISTANCIA_SEGMENTO = 2500 //en metros
    val RATIO_SEGMENTO = 5 //dividimos entre esta cantidad en caso de que se cumpla la condicion de arriba


    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    fun initPorParada(id: String) {

        //limpieza
        _parada.value = null
        _lineasRTModel.value = mutableListOf()
        _lineasRTModelMod.value = -1


        viewModelScope.launch {
            busesListener("6774351ab34b449419e3638f")

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

    //TODO: Terminar de hacerlo funcional
    private  fun busesListener(linea : String) {

        Log.d("FirebaseDB", "Inicio fun")

        val busesIdRef = dbRef.child("lineas").child(linea)
        val childListener  = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val busData = snapshot.value as Map<*, *>
                val ubicacionBus = busData["ubicacion"] as Map<*, *>

                Log.d("FirebaseDB", "Nuevo bus añadido: ${ubicacionBus["lat"]}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val busData = snapshot.value as Map<*, *>
                val busId = snapshot.key
                Log.d("FirebaseDB", "Datos actualizados del bus $busId: ${busData["matricula"]}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val busId = snapshot.key
                Log.d("FirebaseDB", "Bus eliminado: $busId")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Opcional: Implementar si el orden de los hijos cambia
                //Se implementa porque la interfaz me obliga
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseDB", "Error al escuchar cambios en buses", error.toException())
            }
        }


        busesIdRef.addChildEventListener(childListener)


    }
}