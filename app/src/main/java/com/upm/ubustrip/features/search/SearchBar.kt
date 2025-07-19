package com.upm.ubustrip.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(viewModel: SearchBarViewModel) {

    //Texto de la búsqueda
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    //Determina si el contenido aparece
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .statusBarsPadding()
            .padding(end = 30.dp),

        ) {
        SearchBarCore(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = 0f }
            .statusBarsPadding(), inputField = {

            // Campo de texto de entrada para la búsqueda
            TextField(
                textStyle = TextStyle(fontSize = 17.sp),
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = it.text.isNotEmpty() // Expande el contenido cuando hay texto
                    viewModel.updateDesplegadoState(it.text.isNotEmpty())
                },
                placeholder = {
                    Text("Buscar paradas y líneas", fontSize = 17.sp, lineHeight = 4.sp)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Buscar"
                    )
                },
                trailingIcon = {
                    if (searchText.text.isNotEmpty()) { //Solo se muestr cuando hay texto
                        IconButton(onClick = {
                            searchText = TextFieldValue("")
                            viewModel.updateDesplegadoState(false)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Borrar búsqueda"
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            //Fin del textField
        }, expanded = expanded, onExpandedChange = { expanded = it }, content = { })
    }
}

@ExperimentalMaterial3Api
@Composable
fun SearchBarCore(
    inputField: @Composable () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = SearchBarDefaults.inputFieldShape,
    colors: SearchBarColors = SearchBarDefaults.colors(),
    tonalElevation: Dp = SearchBarDefaults.TonalElevation,
    shadowElevation: Dp = SearchBarDefaults.ShadowElevation,
    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {

        inputField()
    }
}
