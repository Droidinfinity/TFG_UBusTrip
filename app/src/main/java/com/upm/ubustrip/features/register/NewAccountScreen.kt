package com.upm.ubustrip.features.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.R
import com.upm.ubustrip.appNavigation.AppScreens
import com.upm.ubustrip.firebase.LoginViewModel
import com.upm.ubustrip.ui.theme.UbusTripFilledButton1Color
import com.upm.ubustrip.ui.theme.UbusTripFilledButton2Color

@Composable
fun SignUp(
    navController: NavController,
    accountViewModel: NewAccountViewModel,
    loginViewModel: LoginViewModel
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)

    CircularGradientBackground()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)

        ) {

            Spacer(modifier = Modifier.height(40.dp))



            Text(
                "REGISTRATE",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 30.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
            CorreoTextFied(viewModel = accountViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            ContrasenaTextFied(
                "Introduce una contraseña",
                isRepeated = false,
                newAccountViewModel = accountViewModel
            )
            Spacer(modifier = Modifier.height(20.dp))
            ContrasenaTextFied(
                "Repite la contraseña",
                isRepeated = true,
                newAccountViewModel = accountViewModel
            )
            Spacer(modifier = Modifier.height(30.dp))
            BotonRegistro(
                loginViewModel = loginViewModel,
                accountViewModel = accountViewModel,
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtomMasTarde(navController = navController)

        }
    }


}


@Composable
fun CorreoTextFied(viewModel: NewAccountViewModel) {
    var correo by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = correo,
        onValueChange = {
            correo = it
            if (!isValidEmail(correo))
                isError = true
            else
                isError = false
            viewModel.setEmail(correo)
        },
        label = { if (!isError) Text("Correo") else Text("Correo no válido") },
        modifier = Modifier.padding(top = 20.dp),
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(errorBorderColor = Color.Red)
    )
}

@Composable
fun ContrasenaTextFied(
    text: String,
    isRepeated: Boolean,
    newAccountViewModel: NewAccountViewModel
) {
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(text) }
    var isEnabled by remember { mutableStateOf(false) }

    //Controlamos que se sincronicen ambos campos de contraseña en caso de que alguno de error
    if (isRepeated && newAccountViewModel.getPassword() != newAccountViewModel.getRepeatedPassword())
        isError = true
    else if (isRepeated && newAccountViewModel.getPassword() == newAccountViewModel.getRepeatedPassword())
        isError = false
    //-----------------------------------------------------------------------------------------

    OutlinedTextField(
        value = password,

        onValueChange = {
            if (!isRepeated) { //si es el campo de introducir la contraseña por primera vez------
                password = it
                newAccountViewModel.setPassword(password = password)
                if (!isPasswordValid(password)) {
                    isError = true
                    title = "Contraseña no válida "

                } else {
                    isError = false
                    title = text
                }
            }//---------------------------------------------------------------------------------

            else {//si es el campo de repetir la contraseña
                password = it
                newAccountViewModel.setRepeatedPassword(password = password)
                if (newAccountViewModel.getPassword() != password) {
                    isError = true
                    title = "La contraseña no coicide "
                } else {
                    isError = false
                    title = text
                }

            }
        },
        label = { Text(text = title) },
        visualTransformation = if (!isEnabled) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.padding(top = 5.dp),
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(errorBorderColor = Color.Red),
        trailingIcon = {
            IconButton(content = {
                Icon(
                    painter = if (!isEnabled) painterResource(R.drawable.ojo) else painterResource(R.drawable.invisible),
                    contentDescription = ""
                )
            }, onClick = {

                if (isEnabled)
                    isEnabled = false
                else
                    isEnabled = true

            })
        }
    )
}

@Composable
fun BotonRegistro(
    loginViewModel: LoginViewModel,
    accountViewModel: NewAccountViewModel,
    navController: NavController
) {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp - 100



    Button(
        onClick = {

            val password = accountViewModel.getPassword()
            val repeatedPassword = accountViewModel.getRepeatedPassword()
            val email = accountViewModel.getEmail()

            if (isPasswordValid(password) && password == repeatedPassword) {

                if (isValidEmail(email))
                    loginViewModel.createUserWithEmailAndPassword(email, password) {

                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate(AppScreens.AccountScreen.route)

                    }

            }

        },
        shape = RoundedCornerShape(16.dp), // Ajusta el radio para redondear los bordes
        modifier = Modifier
            .size(width = screenWidthPx.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = UbusTripFilledButton2Color)
    ) {
        Text("Registrarse", color = Color.White)
    }
}

@Composable
fun ButtomMasTarde(navController: NavController) {
    TextButton(

        modifier = Modifier,
        onClick = { navController.popBackStack() },
    ) {
        Text("Quiero registrarme mas tarde")
    }
}

@Composable
fun CircularGradientBackground() {

    val configuration = LocalConfiguration.current
    val screenHeightPx = 130

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeightPx.dp)
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo de fondo más grande (azul oscuro)
            drawCircle(
                color = Color(0xFF42A5F5), // Cambia por el color exacto deseado
                radius = size.width * 1.2f, // Ajuste de tamaño
                center = Offset(x = 0f, y = size.height / 2)
            )
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo medio (azul medio)
            drawCircle(
                color = Color(0xFF2196F3), // Cambia por el color exacto deseado
                radius = size.width * 0.8f,
                center = Offset(x = 0f, y = size.height / 2)
            )
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            // Círculo más pequeño (azul claro)
            drawCircle(
                color = Color(0xFF1E88E5), // Cambia por el color exacto deseado
                radius = size.width * 0.5f,
                center = Offset(x = 0f, y = size.height / 2)
            )
        }
    }
}

fun isPasswordValid(password: String): Boolean {
    val regex =
        Regex("^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{9,}$")
    return regex.matches(password)
}


fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:/[a-zA-Z]{2,})?$".toRegex()
    return emailRegex.matches(email)
}