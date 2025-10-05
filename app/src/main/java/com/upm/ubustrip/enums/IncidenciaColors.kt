package com.upm.ubustrip.enums

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

enum class IncidenciaColors(val id: Int, val hex: String) {
    INCIDENCIA_INFORMATIVA(0, "#2E7D32"),
    INCIDENCIA_LEVE(1, "#CDDC39"),
    INCIDENCIA_MEDIA(2, "#FF9800"),
    INCIDENCIA_GRAVE(3, "#DD2C00");

    val color: androidx.compose.ui.graphics.Color
        get() = Color(hex.toColorInt())

    companion object {
        fun fromId(id: Int): IncidenciaColors? = entries.find { it.id == id }
    }
}