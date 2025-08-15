package com.upm.ubustrip.models

/**
 * DTO obtenido desde el  JSON de la linea
 *
 * @property polyline
 * @property paradaId
 * @constructor Create empty Segmento data
 */
data class SegmentoData(
    val polyline: String,
    val paradaId: String,
    val paradaFinalId: String?
)