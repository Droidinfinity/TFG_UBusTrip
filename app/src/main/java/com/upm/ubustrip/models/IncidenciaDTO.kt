package com.upm.ubustrip.models

data class IncidenciaDTO(
    val _id : String,val idLinea: String,val titulo: String,
    val mensaje: String,val paradasAfectadas:String,val nivel: Int,
    val alternativa: String, val imgB64: String,val activa: Boolean,val comienzo: String, val final: String)
