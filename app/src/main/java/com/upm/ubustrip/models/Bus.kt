package com.upm.ubustrip.models

data class Bus(val matricula: String = "", val ubicacion: Coordenada, var ultimoSegmentoVisitado: Int = 0)