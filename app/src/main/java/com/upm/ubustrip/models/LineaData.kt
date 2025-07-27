package com.upm.ubustrip.models

import androidx.compose.ui.graphics.Color

/**
 * Modelo obtenido del JSON de la linea
 *
 * @property _id id del objeto de mongo
 * @property lineaId id de la linea, cpn un formato mas legible para la persona
 * @property segmentos
 * @property direccion sentido de la linea
 * @property nombre nombre de la linea
 * @constructor Create empty Linea data
 */
data class LineaData(
    val _id: String,
    val lineaId: String,
    val segmentos: List<SegmentoData>,
    val direccion: String,
    val nombre: String,
    val number : String,
    val color : String
)