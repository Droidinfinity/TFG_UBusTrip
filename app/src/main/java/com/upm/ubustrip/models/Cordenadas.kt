package com.upm.ubustrip.models

data class Coordenada(var longitud: Double, var latitud: Double) {


}

class Segmento() {

    var segmento = mutableListOf<Coordenada>()

}

class Linea() {

    var linea = mutableListOf<Segmento>()

}