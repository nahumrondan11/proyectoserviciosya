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


@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    navController: NavController,
    loginViewModel: LoginViewModel,
    state: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_serviciosya), // logo correcto en los recursos
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
                if (loginViewModel.validarcredenciales(usuario, password)) {
                    navController.navigate(Ruta.MAIN_SEARCH_SCREEN)
                } else {
                    scope.launch {
                        state.showSnackbar(
                            message = "Usuario y/o password incorrecto",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
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
            modifier = Modifier.clickable { /* Implement forgot password logic */ }
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
        label = { Text("Correo electrónico") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_email), // Reemplaza con el ícono correcto
                contentDescription = "Email Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color.White,
            focusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedLeadingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            unfocusedLeadingIconColor = Color.Gray,
            unfocusedTrailingIconColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color.White,
            focusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.Gray
        )
    )
}
