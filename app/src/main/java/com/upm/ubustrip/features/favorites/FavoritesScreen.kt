package com.upm.ubustrip.features.favorites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.upm.ubustrip.features.favorites.viewModel.FavoritesViewModel
import com.upm.ubustrip.features.linea.viewModels.LineaRTSViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//viewModel.addFavorite(ParadaFavorita("pruebaID","paradaPrueba","numero 2","color"))
//viewModel.clearFavorites()
@Composable
fun Favorites(paddingValues: PaddingValues, viewModel: FavoritesViewModel, navController: NavController,lineaRTSViewModel: LineaRTSViewModel) {
    val favoritos by viewModel.favorites.collectAsState()

    Box(Modifier.background(Color.White)) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(favoritos) { fav ->
                FavoriteCard(
                    numeroLinea = fav.numeroParada,
                    nombreParada = fav.nombre,
                    idParada = fav.id,
                    onDelete = { viewModel.removeFavorite(fav) },
                    navController = navController,
                    lineaRTSViewModel = lineaRTSViewModel

                )
            }
        }
    }
}

@Composable
fun FavoriteCard(
    numeroLinea: String,
    nombreParada: String,
    idParada: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    navController: NavController,
    lineaRTSViewModel: LineaRTSViewModel

) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
         onClick = {

             CoroutineScope(Dispatchers.IO).launch { lineaRTSViewModel.initPorParada(
                 id = idParada
             )

             }
             navController.navigate("lineaRTScreen")

         }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Caja amarilla con el número/parada
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFD600)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = numeroLinea,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Nombre de la parada
            Text(
                text = nombreParada,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el menú a la derecha

            // Botón de menú ⋮
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Ajustes",
                        tint = Color.Black
                    )
                }

                // Menú desplegable
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}

