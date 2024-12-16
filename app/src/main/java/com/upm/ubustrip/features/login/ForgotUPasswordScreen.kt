package com.upm.ubustrip.features.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.upm.ubustrip.R
import com.upm.ubustrip.database.LoginViewModel
import com.upm.ubustrip.ui.theme.UbusTripFilledButton2Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForgotUPasswordScreen(
    loginViewModel: LoginViewModel,
    navController: NavController,
    forgotUPassViewModel: ForgotUPassViewModel
) {

    //para eliminar el fondo de la barra de notificaciones
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent)

    //snackBar---------------------------------------------
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    //-------------------------------------------------------

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            CircularGradientBackground()
            Content(
                paddingValues = paddingValues,
                forgotUPassViewModel = forgotUPassViewModel,
                loginViewModel = loginViewModel,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                navController = navController
            )

        }


    )


}

@Composable
fun Content(
    paddingValues: PaddingValues,
    forgotUPassViewModel: ForgotUPassViewModel,
    loginViewModel: LoginViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)

        ) {

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "RECUPERA TU CONTRASEÑA",
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))
            IconoInterrogacion()
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "¿Olvidaste tu contraseña?",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 5.dp)
            )
            Text(
                "Introduce tu correo y te enviaremos un email\n para realizar el cambio de contraseña",
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 15.sp
            )
            CorreoTextFied(viewModel = forgotUPassViewModel)
            Spacer(modifier = Modifier.height(30.dp))
            BotonRecuperacion(
                loginViewModel = loginViewModel,
                coroutineScope = coroutineScope,
                forgotUPassViewModel = forgotUPassViewModel,
                snackbarHostState = snackbarHostState
            )

            TextButtonNoQuieroRecuperar(navController = navController)

        }
    }

}

@Composable
fun CorreoTextFied(viewModel: ForgotUPassViewModel) {
    var correo by remember { mutableStateOf("") }
    OutlinedTextField(
        value = correo,
        onValueChange = {
            correo = it
            viewModel.setEmail(email = correo)
        },
        label = { Text("Correo") },
        modifier = Modifier.padding(top = 20.dp, start = 30.dp),


        )
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

@Composable
fun BotonRecuperacion(
    loginViewModel: LoginViewModel,
    forgotUPassViewModel: ForgotUPassViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp - 100



    Button(
        onClick = {

            val email = forgotUPassViewModel.getEmail()



            if (isVAlidEmail(email = email)) {

                loginViewModel.sendPasswordResetEmail(email = email) {
                    //si ha sido un éxito:
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Se ha enviado un email a tu correo",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short,
                        )
                    }
                }

            } else
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Email no válido",
                        actionLabel = "OK",
                        duration = SnackbarDuration.Short,
                    )
                }

        },
        shape = RoundedCornerShape(16.dp), // Ajusta el radio para redondear los bordes
        modifier = Modifier
            .size(width = screenWidthPx.dp, height = 50.dp)
            .padding(start = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = UbusTripFilledButton2Color)
    ) {
        Text("Enviar", color = Color.White)
    }
}

fun isVAlidEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:/[a-zA-Z]{2,})?$".toRegex()
    return emailRegex.matches(email)
}

@Composable
fun IconoInterrogacion() {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp / 4

    Row(
        modifier = Modifier.padding(start = screenWidthPx.dp)

    ) {
        Icon(
            painter = painterResource(R.drawable.interrogacion_azul), contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
        )
    }


}

@Composable
fun TextButtonNoQuieroRecuperar(navController: NavController) {
    TextButton(
        onClick = { navController.popBackStack() }
    ) {

         Spacer(modifier = Modifier.width(70.dp))
        Text("¿Olvídalo, la he recordado.")

    }
}