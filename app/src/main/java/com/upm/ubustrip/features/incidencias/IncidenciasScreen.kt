package com.upm.ubustrip.features.incidencias

import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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

            IncidenciasContent(padding = paddingValues, viewModel = viewModel, incidenciaLevel = viewModel.incidencia.nivel)

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

        IncidendiaIcon(base64Image = viewModel.incidencia.imgB64, viewModel = viewModel, incidenciaLevel = incidenciaLevel)

        Spacer(modifier = Modifier.size(20.dp))

        InformacionText()

        Spacer(modifier = Modifier.size(20.dp))

        IncidenciaTextCard(
            titulo = viewModel.incidencia.titulo,
            text = viewModel.incidencia.mensaje,
            nivelGravedad = viewModel.nivelIncidenciaToText(viewModel.incidencia.nivel),
            paradasAfectadas = viewModel.incidencia.paradasAfectadas,
            inicioIncidencia = viewModel.incidencia.comienzo,
            finIncidencia = viewModel.incidencia.final,
            alternativaText = viewModel.incidencia.alternativa)

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
                append("Estado: ")
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