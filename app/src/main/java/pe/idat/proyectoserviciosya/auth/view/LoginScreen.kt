package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.LoginViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C2E))
                    .padding(paddingValues)
            ) {
                LoginContent(navController, loginViewModel, snackbarHostState, coroutineScope)
            }
        }
    )
}


@Composable
fun LoginContent(
    navController: NavController,
    loginViewModel: LoginViewModel,
    state: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val scope = rememberCoroutineScope()
    val loginResult by loginViewModel.loginResult.observeAsState()

    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            navController.navigate(Ruta.MAIN_SEARCH_SCREEN)
        } else if (loginResult == false) {
            state.showSnackbar("Usuario y/o password incorrecto")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_serviciosya),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "ServiciosYa",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "iniciar sesión",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        val usuario by loginViewModel.usuario.observeAsState("")
        val password by loginViewModel.password.observeAsState("")

        EmailField(usuario) { email ->
            loginViewModel.onLoginValueChanged(email, password)
        }
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(password) { pass ->
            loginViewModel.onLoginValueChanged(usuario, pass)
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                loginViewModel.validarcredenciales()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Iniciar sesión", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color.White,
            modifier = Modifier.clickable { /*  */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Registrarse",
            color = Color.White,
            modifier = Modifier.clickable { navController.navigate(Ruta.REGISTRO_SCREEN) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Correo electrónico", color = Color.White) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor  = Color.White,
            cursorColor = Color.White
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña", color = Color.White) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor  = Color.White,
            cursorColor = Color.White
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
