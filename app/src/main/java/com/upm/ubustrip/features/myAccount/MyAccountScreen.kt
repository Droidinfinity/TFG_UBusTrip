package com.upm.ubustrip.features.myAccount

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun MyAccountScreen(navController: NavController) {

    Scaffold(
        topBar = { MyAccountTopBar() },
        content = { paddingValues -> ProfileContent(paddingValues) }

    )


}

@Composable
fun ProfileContent(paddingValues: PaddingValues) {

    CircularGradientBackground()

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(paddingValues)
        .background(Color.Red)){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .background(Color.Red)
        ) { }

    }


}

