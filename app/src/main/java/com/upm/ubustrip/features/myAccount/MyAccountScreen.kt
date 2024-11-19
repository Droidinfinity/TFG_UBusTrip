package com.upm.ubustrip.features.myAccount

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.upm.ubustrip.firebase.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")

@Composable
fun MyAccountScreen(navController: NavController,loginViewModel: LoginViewModel) {

    val sesion = loginViewModel.getAuth() //Usuario
    val userEmail = loginViewModel.getAuth().currentUser?.email

    Column {

        //Parte de arriba
        MyProfileCard(navController = navController, loginViewModel = loginViewModel)
        if(userEmail!=null)
        OptionsCard(false, tile = "Email", content = userEmail)
        OptionsCard(true, tile = "Sesión", content = "Cerrar sesión", textColor = Color.Red, onClick = {
            sesion.signOut()
            navController.popBackStack()

        }

        )


    }//Fin del Colum

}

@Composable
fun OptionsCard(isClickable : Boolean = false, onClick : ()-> Unit = {},tile : String,content : String,textColor: Color = Color.Black) {

    Box(modifier = Modifier.clickable(enabled = isClickable, onClick = onClick)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Column {

                Text(
                    text = tile,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = content,
                    modifier = Modifier.padding(start = 20.dp),
                    color = textColor
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)

                )

            }
        }
    }

}