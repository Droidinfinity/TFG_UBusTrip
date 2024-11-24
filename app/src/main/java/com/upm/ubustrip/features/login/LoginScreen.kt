package com.upm.ubustrip.features.login

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.upm.ubustrip.R
import com.upm.ubustrip.appNavigation.AppNavigation
import com.upm.ubustrip.appNavigation.AppScreens
import com.upm.ubustrip.firebase.LoginViewModel
import com.upm.ubustrip.ui.theme.UbusTripFilledButton1Color
import com.upm.ubustrip.ui.theme.UbusTripFilledButton2Color
import com.upm.ubustrip.ui.theme.UbusTripFilledGoogleButtom


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {


    Scaffold(
        content = { Login(loginViewModel = loginViewModel, navController = navController) }
    )


}


@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavController) {

    var position by remember { mutableStateOf(Offset(0f, 0f)) }

    val modifier = Modifier.fillMaxSize()
    val font = FontFamily(
        Font(R.font.bangers, FontWeight.Normal),
        Font(R.font.bangers, FontWeight.Bold),
        Font(R.font.bangers, FontWeight.Normal)
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        HeaderImage()
        Text(
            text = "INICIA SESIÓN",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )

        CorreoTextFied()
        ContrasenaTextFied()
        OlvidateContrasena()

        Box(modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)) {

            BotonLogin()

        }
        ContinuaCon()
        Box(modifier = Modifier.padding(bottom = 30.dp))
        BotonGoogleLogin(loginViewModel = loginViewModel, navController = navController)
        NoTienesCuneta(navController = navController)


    }


}

@Composable
fun HeaderImage() {

    Box() {

        Image(
            painter = painterResource(
                id = R.drawable.ubustrip_icon_sin_fondo
            ),
            contentDescription = "Header",
            modifier = Modifier
                .padding(top = 20.dp)
                .size(width = 200.dp, 200.dp)
        )

    }


}

@Composable
fun CorreoTextFied() {
    var correo by remember { mutableStateOf("") }

    OutlinedTextField(
        value = correo,
        onValueChange = { correo = it },
        label = { Text("Correo") },
        modifier = Modifier.padding(top = 20.dp)
    )
}

@Composable
fun ContrasenaTextFied() {
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.padding(top = 5.dp)
    )
}

@Composable
fun OlvidateContrasena() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End

    ) {

        ButtomOlvidateC { }

    }

}

@Composable
fun ButtomOlvidateC(onClick: () -> Unit) {
    TextButton(

        modifier = Modifier
            .padding(end = 60.dp),
        onClick = { onClick() }
    ) {
        Text("¿Olvidaste la contraseña?")
    }
}

@Composable
fun BotonLogin() {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp - 50




    Button(
        onClick = {


        },
        shape = RoundedCornerShape(16.dp), // Ajusta el radio para redondear los bordes
        modifier = Modifier
            .size(width = screenWidthPx.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = UbusTripFilledButton2Color)
    ) {
        Text("Iniciar sesión")
    }
}

@Composable
fun BotonGoogleLogin(loginViewModel: LoginViewModel, navController: NavController) {

    val configuration = LocalConfiguration.current
    val buttonWidthPx = configuration.screenWidthDp - 50
    // var position by remember { mutableStateOf(Offset(0f, 0f)) }

    val context = LocalContext.current

    loginViewModel.setNavController(navController = navController)

    //RELACIONADO AL LOGIN Y USUARIO
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .StartActivityForResult()
    ) {

        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {

            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            loginViewModel.sigInWhithGoogleCredential(credential) {


            }

        } catch (ex: Exception) {
        }

    }


    Button(
        onClick = {

            val opciones = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(loginViewModel.getGoogleToken())
                .requestEmail()
                .build()

            val googleSignInCliente = GoogleSignIn.getClient(context, opciones)
            launcher.launch(googleSignInCliente.signInIntent)

            GoogleSignIn.getClient(context, opciones)
                .signOut() //para que no recuerde la cuenta a la hora de hacer log out


        },
        shape = RoundedCornerShape(16.dp), // Ajusta el radio para redondear los bordes
        modifier = Modifier
            .size(width = buttonWidthPx.dp, height = 50.dp)
            .onGloballyPositioned { layoutCoordinates ->
                // Guarda la posición en `position` en coordenadas globales
                //   position = layoutCoordinates
                //   .positionInRoot()
            },
        colors = ButtonDefaults.buttonColors(containerColor = UbusTripFilledButton1Color)
    ) {

        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Logo de Google" // Añade una descripción aquí

        )

        Box(modifier = Modifier.width(10.dp))

        Text("Iniciar con Google")


    }
}


@Composable
fun HorizontalLine() {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp / 3.5

    Column(modifier = Modifier.height(10.dp)) {


        Box(
            modifier = Modifier
                .height(7.dp)
            // Grosor de la línea


        )

        Box(
            modifier = Modifier
                .width(screenWidthPx.dp) // Ancho completo de la pantalla
                .height(1.dp)   // Grosor de la línea
                .background(Color.LightGray) // Color de la línea

        )

    }

}

@Composable
fun ContinuaCon() {

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp

    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        Box(modifier = Modifier.padding(start = 20.dp))
        HorizontalLine()
        Box(modifier = Modifier.padding(start = 5.dp))
        Text(text = "O continua con:")
        Box(modifier = Modifier.padding(end = 5.dp))
        HorizontalLine()
        Box(modifier = Modifier.padding(end = 20.dp))
    }
    //  Box(modifier = Modifier.padding(start = 20.dp))

}


@Composable
fun NoTienesCuneta(navController: NavController) {

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Convertimos la altura de pantalla de dp a px para asegurar consistencia en las unidades
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    var position by remember { mutableStateOf(Offset(0f, 0f)) }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                // Guarda la posición como Offset
                position = layoutCoordinates.positionInRoot()

                val finaly = screenHeightPx.dp - position.y.toInt().dp - 10.dp

            }

        //  .padding(top = ( screenHeightPx.dp - position.y.toInt().dp )-100.dp )
    ) {

        //val finalPosition = position.y.toInt().dp -10.dp - screenHeightPx.dp
        TextButtonRegistrate(onClick = { navController.navigate(AppScreens.RegisterScreen.route)})

    }


}

@Composable
fun TextButtonRegistrate(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("¿No tienes cuenta? Registrate.")
    }
}
