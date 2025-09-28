import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import android.util.Log
import com.upm.ubustrip.database.AppDatabase
import com.upm.ubustrip.models.Parada

class ParadaTest {

    @Before
    fun setup() {

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0


        mockkObject(AppDatabase)
    }

    @Test
    fun crearParada() = runTest {
        // ARRANGE
        val testId = "6760bb3dceaa35d371867bf8"
        val mockJsonResponse = """
        {
            "_id": "$testId",
            "idParada": "20653",
            "nombreParada": "Honduras-Est.La Rambla",
            "municipio": "Coslada",
            "provincia": "Madrid",
            "lineas": [
                "6774351ab34b449419e3638f",
                "679d2cc2a5f8ba8f3264385a"
            ],
            "ubicacion": {
                "long": "40.42501",
                "lat": "-3.54802"
            },
            "stops": {
                "679d2cc2a5f8ba8f3264385a": 1,
                "6774351ab34b449419e3638f": 4
            }
        }
        """.trimIndent()

        // ⭐ MOCK CORRECTO - usar any() o el string exacto
        coEvery {
            AppDatabase.get(any<String>())
        } returns mockJsonResponse

        // ACT
        val result = Parada.create(testId)


        assert(result.id == testId)
        assert(result.nombreParada == "Honduras-Est.La Rambla")
        assert(result.numeroParada == "20653")
        assert(result.paradaId == testId)

        // Verificar ubicación
        assert(result.ubicacion != null)
        assert(result.ubicacion?.long == "40.42501")
        assert(result.ubicacion?.lat == "-3.54802")

        // Verificar stops
        assert(result.stops != null)
        assert(result.stops?.size == 2)
        assert(result.stops?.get("679d2cc2a5f8ba8f3264385a") == 1)
        assert(result.stops?.get("6774351ab34b449419e3638f") == 4)

        // Verificar líneas
        assert(result.lineasParada.size == 2)
        assert(result.lineasParada.contains("6774351ab34b449419e3638f"))
        assert(result.lineasParada.contains("679d2cc2a5f8ba8f3264385a"))
    }
}