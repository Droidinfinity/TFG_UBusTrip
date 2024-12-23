package com.upm.ubustrip.features.polylines

//Usamos esta clase para no abusar de la API de Google maps o similares
class Polyline {

    companion object { //lo hago estático para mayor comodidad y no instanciar un objeto
        fun decode(encoded: String): List<Pair<Double, Double>> {
            val polyline =
                mutableListOf<Pair<Double, Double>>() //lista de nuetras coordenadas decodificadas
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            //BUCLE PARA RECORRER LA CADENA
            while (index < len) {
                var shift = 0
                var result = 0

                // Decodificar latitud-----------------------------------------------------------------------
                do {
                    val b = encoded[index++].code - 63 //Conviertir el carácter a su valor numérico en base 64
                    result = result or (b and 0x1f shl shift)//el número codificado bit a bit
                    shift += 5 //se avanza de 5 en 5 bits
                } while (b >= 0x20)
                val deltaLat = if (result and 1 != 0) result.inv() shr 1 else result shr 1
                lat += deltaLat
                //---------------------------------------------------------------------------------------

                // Decodificar longitud-----------------------------------------------
                shift = 0
                result = 0
                do {
                    val b = encoded[index++].code - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val deltaLng = if (result and 1 != 0) result.inv() shr 1 else result shr 1
                lng += deltaLng
                //-------------------------------------------------------------------------

                polyline.add(Pair(lat / 1e5, lng / 1e5)) // Añadimos las coordenadas decodificadas a la lista
            } //FIN DEL BUCLE PARA RECORRER LA CADENA

            return polyline
        }
    }
}