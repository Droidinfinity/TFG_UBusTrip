package com.upm.ubustrip.features.linea.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.models.Bus
import com.upm.ubustrip.models.Coordenada
import com.upm.ubustrip.models.LineaRTModel
import com.upm.ubustrip.models.Parada
import com.upm.ubustrip.viewModels.ParadaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LineaRTSViewModel : ViewModel() {
    var busesLinea = mutableStateListOf<Bus>()
    var refreshScreen = mutableStateOf(0)

    //Contiene todos los buses de todas las lineas que correspondan a la parada
    val busesPorLinea = mutableMapOf<String, MutableList<Bus>>()

    var isLoading = mutableStateOf(true)
        private set
    var firstRefresh = true

    val database = FirebaseDatabase.getInstance(AppDatabase.REALTIME_DATABASE_URL)
    val dbRef = database.reference // Referencia raíz de la base de datos

    // Usamos MutableState para observar cambios en Composables
    private val _parada = mutableStateOf<Parada?>(null)
    val parada: State<Parada?> = _parada

     var lineaSeleccionada = mutableStateOf<Int>(0)
    var idLineaSeleccionada = ""


    private val _lineasRTModel =  mutableStateOf<MutableList<LineaRTModel>?>(mutableListOf())
    val lineasRTModel : State<MutableList<LineaRTModel>?> = _lineasRTModel

    private var _lineasRTModelMod = mutableStateOf<Int>(-1)
    var lineasRTModelMod = _lineasRTModelMod

    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    //Linite establecido para indicar que un segmento es muy largo para graficarlo en una linea
    val MAX_DISTANCIA_SEGMENTO = 2500 //en metros
    val RATIO_SEGMENTO = 5 //dividimos entre esta cantidad en caso de que se cumpla la condicion de arriba


    //LISTENERS-----------------------------------------------------------
    private val listenersActivos = mutableMapOf<String, ChildEventListener>()

    val childListener  = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val busData = snapshot.value as Map<*, *>
            val ubicacionBus = busData["ubicacion"] as Map<*, *>

            val bus = Bus(ubicacion = Coordenada(longitud = ubicacionBus["long"] as Double, latitud = ubicacionBus["lat"] as Double), matricula = busData["matricula"] as String)

            busesLinea.add(bus)
            refreshScreen.value++

            Log.d("FirebaseDB", "Nuevo bus añadido: ${busData["matricula"]}")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val busData = snapshot.value as Map<*, *>
            val ubicacionBus = busData["ubicacion"] as Map<*, *>
            val busAnterior = busesLinea.find { it.matricula == busData["matricula"] }

            val bus = Bus(ubicacion = Coordenada(longitud = ubicacionBus["long"] as Double, latitud = ubicacionBus["lat"] as Double))
            busesLinea.remove(busAnterior)
            busesLinea.add(bus)
            refreshScreen.value++

            Log.d("FirebaseDB", "Datos actualizados del bus ${busData["matricula"]}")
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



    //FIN LISTENERS----------------------------------------------------------------------------------

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun initPorParada(id: String) {

        isLoading.value = true
        busesLinea.clear()
        //limpieza
        _parada.value = null
        _lineasRTModel.value = mutableListOf()
        _lineasRTModelMod.value = -1
        busesLinea.clear()


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
            initListenerAllLineas()
            isLoading.value = false

        }
    }

    //TODO: Terminar de hacerlo funcional
    public fun initBusesListener(linea : String) {

        Log.d("FirebaseDB", "Inicio fun")

        val busesIdRef = dbRef.child("lineas").child(linea)

        busesIdRef.addChildEventListener(childListener)


    }


    public fun removeBusesListener(linea:  String){

        if (linea.isNotEmpty()){
            val busesIdRef = dbRef.child("lineas").child(linea)
            busesIdRef.removeEventListener(childListener)
        }

    }

    //Esto lo uso para refrescar el la pantalla de tiempos de espera : (cargaba la info, pero no refrescaba el composable)
    fun iniciarCambioDeTabs() {
        viewModelScope.launch {
            if (firstRefresh) {
                firstRefresh = false
                setSelectedTabIndex(1)
                delay(50)

                setSelectedTabIndex(0)
            }
        }
    }

    fun crearYRegistrarListener(lineaId: String): ChildEventListener {
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val busData = snapshot.value as Map<*, *>
                val ubicacionBus = busData["ubicacion"] as Map<*, *>
                val nextStop = (busData["nextStop"] as? Long)?.toInt() ?: 0

                val bus = Bus(ubicacion = Coordenada(longitud = ubicacionBus["long"] as Double, latitud = ubicacionBus["lat"] as Double), matricula = busData["matricula"] as String, nextStop = nextStop)

                if (busesPorLinea[lineaId] == null) {
                    busesPorLinea[lineaId] = mutableListOf()
                }
                busesPorLinea[lineaId]?.add(bus)
                Log.d("FirebaseDBRefactor", "Nuevo bus añadido: ${busData["matricula"]}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Tu lógica
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Tu lógica
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseDB", "Error: ${error.message}")
            }
        }

        // Registrar listener en el nodo correspondiente
        val ref = dbRef.child("lineas").child(lineaId)
        ref.addChildEventListener(listener)

        return listener
    }

    fun iniciarListenerLinea(lineaId: String) {
        if (listenersActivos[lineaId] == null) {
            val listener = crearYRegistrarListener(lineaId)
            listenersActivos[lineaId] = listener
        }
    }

    fun eliminarListenerLinea(lineaId: String) {
        val listener = listenersActivos.remove(lineaId)
        if (listener != null) {
            dbRef.child("lineas").child(lineaId).removeEventListener(listener)
        }
    }

    public fun initListenerAllLineas(){

        if (lineasRTModel.value != null) {
            for (linea in lineasRTModel.value!!) {
                iniciarListenerLinea(lineaId = linea.id)
            }
        }

    }

}