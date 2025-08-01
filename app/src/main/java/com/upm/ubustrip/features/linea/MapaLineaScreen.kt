package com.upm.ubustrip.features.linea

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.upm.ubustrip.R
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapTestScreen(viewModel: LineaRTSViewModel) {
    if (viewModel.parada.value != null) {
        val coordenadasLineas: List<Pair<List<LatLng>, Color>> = viewModel.transformPolylinesToGoogle()

        val latParada = viewModel.parada.value?.ubicacion?.lat?.toDoubleOrNull() ?: -3.7038
        val longParada = viewModel.parada.value?.ubicacion?.long?.toDoubleOrNull() ?: 40.4168
        val ubicacionParada = LatLng(longParada, latParada)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(ubicacionParada, 20f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Dibujar polilíneas
            for ((puntos, color) in coordenadasLineas) {
                Polyline(
                    points = puntos,
                    color = color,
                    width = 8f
                )
            }



            // Markers de los buses
            for ((lineaId, buses) in viewModel.busesPorLinea) {
                for (bus in buses) {
                    val lat = bus.ubicacion.longitud
                    val lng = bus.ubicacion.latitud
                    if (lat != null && lng != null) {
                        Marker(
                            state = MarkerState(position = LatLng(lat, lng)),
                            title = lineaId,
                            icon = scaledMarkerIcon(R.drawable.ic_marker_bus_custom, scale = 0.15f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun scaledMarkerIcon(@DrawableRes resId: Int, scale: Float): BitmapDescriptor {
    val context = LocalContext.current
    val originalBitmap = BitmapFactory.decodeResource(context.resources, resId)
    val scaledBitmap = Bitmap.createScaledBitmap(
        originalBitmap,
        (originalBitmap.width * scale).toInt(),
        (originalBitmap.height * scale).toInt(),
        false
    )
    return BitmapDescriptorFactory.fromBitmap(scaledBitmap)
}
