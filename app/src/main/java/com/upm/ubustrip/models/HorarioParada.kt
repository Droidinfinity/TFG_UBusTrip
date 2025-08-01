package com.upm.ubustrip.models

import java.time.LocalTime

class HorarioParada(var horarioParadaModel: HorarioParadaModel) {

    fun getHorariosFrom(hora: LocalTime, numHorarios: Int): List<LocalTime> {
        return horarioParadaModel.frecuencias
            .dropWhile { it <= hora }
            .take(numHorarios)
    }
}