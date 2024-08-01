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
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.LoginViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta


@Composable
fun RegistroScreen(navController: NavController) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2C2C2E))
                .padding(it)
        ) {
            RegistroContent(navController)
        }
    }
}


@Composable
fun RegistroContent(navController: NavController) {
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
            text = "ServicioSYa",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "registrarse",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        // valores de texto para la información de los registro
        var nombreCompleto by remember { mutableStateOf("") }
        var correo by remember { mutableStateOf("") }
        var nombreUsuario by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmarPassword by remember { mutableStateOf("") }

        RegistroField(value = nombreCompleto, onValueChange = { nombreCompleto = it }, label = "Nombre completo")
        Spacer(modifier = Modifier.height(16.dp))
        RegistroField(value = correo, onValueChange = { correo = it }, label = "Correo electrónico")
        Spacer(modifier = Modifier.height(16.dp))
        RegistroField(value = nombreUsuario, onValueChange = { nombreUsuario = it }, label = "Nombre de usuario")
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(value = password, onValueChange = { password = it }, label = "Contraseña")
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(value = confirmarPassword, onValueChange = { confirmarPassword = it }, label = "Confirmar contraseña")
        Spacer(modifier = Modifier.height(32.dp))

        // Botón de registro
        Button(
            onClick = { /* Imp */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text(text = "Registrarse", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Ya tienes una cuenta?",
            color = Color.White,
            modifier = Modifier.clickable { navController.navigate(Ruta.LOGIN_SCREEN) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Iniciar sesión",
            color = Color.White,
            modifier = Modifier.clickable { navController.navigate(Ruta.LOGIN_SCREEN) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
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
fun PasswordField(value: String, onValueChange: (String) -> Unit, label: String) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
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
