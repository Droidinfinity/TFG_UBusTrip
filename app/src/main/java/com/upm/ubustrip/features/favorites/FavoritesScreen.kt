package com.upm.ubustrip.features.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Favorites(paddingValues: PaddingValues) {

    val itemsList = List(20) { "Elemento ${it + 1}" }

    Box(Modifier.background(Color.White)) {


        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            items(itemsList) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }


}