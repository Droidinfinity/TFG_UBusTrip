package com.upm.ubustrip.features.incidencias

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upm.ubustrip.R
import com.upm.ubustrip.features.incidencias.viewModels.IncidenciasViewModel

@Composable
fun IncidenciaScreen(viewModel: IncidenciasViewModel){

    Scaffold(

        topBar = { IncidenciasTopBar(incidenciasViewModel = viewModel) },
        content = {paddingValues ->

            IncidenciasContent(padding = paddingValues, viewModel = viewModel, incidenciaLevel = 0)

        }

    )


}

@Composable
fun IncidenciasContent(padding: PaddingValues,viewModel: IncidenciasViewModel,incidenciaLevel: Int){

    val scrollState = rememberScrollState()
    Box(){
    Column(
        modifier = Modifier
            .padding(top = padding.calculateTopPadding())
            .fillMaxSize()
            .background(viewModel.setBackground(incidenciaLevel))
            .verticalScroll(scrollState),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IncidendiaIcon(base64Image = "", viewModel = viewModel, incidenciaLevel = incidenciaLevel)

        Spacer(modifier = Modifier.size(20.dp))

        InformacionText()

        Spacer(modifier = Modifier.size(20.dp))

        IncidenciaTextCard(titulo = "Mantenimiento en la A-2", text = "Estimado viajero, Le informamos que, debido a trabajos de mantenimiento programados en la autovía A-2, el servicio habitual de la línea A-2 se verá temporalmente interrumpido entre los días 6 y 9 de octubre de 2025. Esta intervención forma parte de un plan de mejora de la infraestructura vial que busca garantizar una mayor seguridad y eficiencia en el transporte público a largo plazo.",
            nivelGravedad = "Medio",
            paradasAfectadas = "Avenida de América, Canillejas, San Fernando, Torrejón de Ardoz.",
            inicioIncidencia = "11 de Noviembre del 2025",
            finIncidencia = "12 de Noviembre del 2025",
            alternativaText = "Realizar transbordo en la línea 1 antes de llegar al metro de Canillejas.\n" +
                    "\n" +
                    "Utilizar la línea 223 desde Torrejón como ruta alternativa hacia Madrid.")








        }

    }

}

@Composable
fun IconoExclamacion(incidenciaLevel : Int) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
    when (incidenciaLevel) {

        0 ->
            Icon(
                painter = painterResource(R.drawable.in_informativa_icon), contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(150.dp)
            )

        1 ->
            Icon(
                painter = painterResource(R.drawable.in_leve_icon), contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(150.dp)
            )

        2 ->
            Icon(
                painter = painterResource(R.drawable.in_media_icon), contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(150.dp)
            )

        3 ->
            Icon(
                painter = painterResource(R.drawable.in_grave_icon), contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(150.dp)
            )
     }

    }

}
@Composable
fun InformacionText(){

    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
    Text("INFORMACIÓN",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    }
}
@Composable
fun IncidendiaIcon(base64Image: String,viewModel: IncidenciasViewModel,incidenciaLevel: Int){

    val bitmap = viewModel.decodeBase64ToBitmap(base64Image)
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Imagen decodificada",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 30.dp)
        )
    } else {
        Box(modifier = Modifier.padding(top = 30.dp)) {
            IconoExclamacion(incidenciaLevel = incidenciaLevel)
        }
    }

}
@Composable
fun IncidenciaTextCard(text: String,titulo: String,nivelGravedad: String, paradasAfectadas : String,inicioIncidencia: String,finIncidencia: String,alternativaText: String){
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF6ED)) // fondo cálido
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black // dorado suave
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            NivelGravedadIncidencia(nivelGravedad)
            ParadasAfectadasIncidencia(paradasAfectadas)
            InicioIncidencia(inicioIncidencia)
            FinalIncidencia(finIncidencia)
            AlternativasIncidencia(text = alternativaText)

        }
    }

}
@Composable
fun NivelGravedadIncidencia(text: String){

    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Gravedad: ")
            }
            append(text)
        }, color = Color.DarkGray

    )

}
@Composable
fun ParadasAfectadasIncidencia(text: String){

    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Paradas afectadas: ")
            }
            append(text)
        }, color = Color.DarkGray

    )
}
@Composable
fun InicioIncidencia(text: String){
    Row {

        Text("Inicio: ", fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Text(text, color = Color.DarkGray)
    }
}
@Composable
fun FinalIncidencia(text: String){
    Row {

        Text("Fin estimado: ", fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Text(text, color = Color.DarkGray)
    }
}
@Composable
fun AlternativasIncidencia(text: String){

    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Alternativas: ")
            }
            append(text)
        }, color = Color.DarkGray

    )
}
@Composable
fun DetallesCard(){

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF6ED))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

        }
    }



}