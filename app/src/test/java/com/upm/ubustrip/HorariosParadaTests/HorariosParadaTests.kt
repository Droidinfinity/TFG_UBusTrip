package com.upm.ubustrip.HorariosParadaTests

import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.database.HorariosParadaDB
import junit.framework.TestCase.assertEquals
import org.junit.After
import java.time.LocalTime

class HorariosParadaDBTest {

    @Before
    fun setup() {
        mockkObject(AppDatabase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getHorariosSemana() = runTest {
        val idParada = "6760bb3dceaa35d371867bf8"
        val idLinea = "679d2cc2a5f8ba8f3264385a"

        val jsonResponse = """
    {
        "tipo": "semana",
        "horarios": [
            "06:32", "06:47", "07:02", "07:17", "07:32", "07:47", 
            "08:02", "08:17", "08:32", "08:47", "09:02", "09:17", 
            "09:32", "09:47", "10:00", "10:30", "11:00", "11:30", 
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:15", 
            "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", 
            "16:00", "16:15", "16:30", "17:00", "17:30", "18:00", 
            "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", 
            "21:30", "22:00", "22:30", "23:00"
        ]
    }
    """.trimIndent()

        coEvery {
            AppDatabase.get(any())
        } returns jsonResponse

        val resultado = HorariosParadaDB.getHorariosSemana(idParada, idLinea)


        assertEquals(46, resultado.frecuencias.size)
        assertEquals(LocalTime.of(6, 32), resultado.frecuencias[0])
        assertEquals(LocalTime.of(23, 0), resultado.frecuencias[45])
    }

    @Test
    fun getHorariosSabado() = runTest {
        val idParada = "6760bb3dceaa35d371867bf8"
        val idLinea = "679d2cc2a5f8ba8f3264385a"

        val jsonResponse = """
    {"tipo":"sabado","horarios":["06:02","06:32","07:02","07:32","08:02","08:32","09:02",
    "09:32","10:02","10:32","11:02","11:32","12:02","12:32","13:02","13:32","14:02","14:32",
    "15:02","15:32","16:02","16:32","17:02","17:32","18:02","18:32","19:02","19:32","20:02","20:32",
    "21:02","21:32","22:02","22:32"]}
    """.trimIndent()

        coEvery {
            AppDatabase.get(any())
        } returns jsonResponse

        val resultado = HorariosParadaDB.getHorariosSabado(idParada, idLinea)


        assertEquals(34, resultado.frecuencias.size)
        assertEquals(LocalTime.of(6, 2), resultado.frecuencias[0])
        assertEquals(LocalTime.of(22, 32), resultado.frecuencias[33])
    }

    @Test
    fun getHorariosDomingoFestivo() = runTest {
        val idParada = "6760bb3dceaa35d371867bf8"
        val idLinea = "679d2cc2a5f8ba8f3264385a"

        val jsonResponse = """
    {"tipo":"domingo_festivo","horarios":["08:02","09:02","10:02","11:02","12:02","13:02","14:02",
    "15:02","16:02","17:02","18:02","19:02","20:02","21:02","22:02"]}
    """.trimIndent()

        coEvery {
            AppDatabase.get(any())
        } returns jsonResponse

        val resultado = HorariosParadaDB.getHorariosDomingo(idParada, idLinea)


        assertEquals(15, resultado.frecuencias.size)
        assertEquals(LocalTime.of(8, 2), resultado.frecuencias[0])
        assertEquals(LocalTime.of(22, 2), resultado.frecuencias[14])
    }
}
