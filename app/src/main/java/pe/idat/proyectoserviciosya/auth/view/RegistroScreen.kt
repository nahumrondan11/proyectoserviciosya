package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.data.network.request.RegistroRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.Pais
import pe.idat.proyectoserviciosya.auth.viewmodel.LoginViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.RegistroViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@Composable
fun RegistroScreen(navController: NavController, registroViewModel: RegistroViewModel = viewModel()) {
    val paises by registroViewModel.paises.observeAsState(emptyList())
    val departamentos by registroViewModel.departamentos.observeAsState(emptyList())
    val registroResult by registroViewModel.registroResult.observeAsState()

    // Valores para los campos de texto
    var nombreCompleto by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf<Pais?>(null) }
    var departamento by remember { mutableStateOf<Departamento?>(null) }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    // Obtener los países al cargar la pantalla
    LaunchedEffect(Unit) {
        registroViewModel.obtenerPaises()
    }

    // Navegar a la pantalla de login si el registro fue exitoso
    LaunchedEffect(registroResult) {
        if (registroResult == true) {
            navController.navigate(Ruta.LOGIN_SCREEN)
        }
    }

    // Contenido de la pantalla
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2C2C2E))
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Campos de registro
                RegistroField(value = nombreCompleto, onValueChange = { nombreCompleto = it }, label = "Nombre completo")
                Spacer(modifier = Modifier.height(12.dp))
                RegistroField(value = nombreUsuario, onValueChange = { nombreUsuario = it }, label = "Nombre de usuario")
                Spacer(modifier = Modifier.height(12.dp))
                RegistroField(value = correo, onValueChange = { correo = it }, label = "Correo electrónico")
                Spacer(modifier = Modifier.height(12.dp))

                // Dropdown para seleccionar país
                DropdownMenuSample(
                    selectedOption = pais,
                    options = paises,
                    onOptionSelected = {
                        pais = it
                        registroViewModel.obtenerDepartamentos(it.idpais)
                    },
                    label = "País",
                    getText = {it.nombre}
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Dropdown para seleccionar departamento
                DropdownMenuSample(
                    selectedOption = departamento,
                    options = departamentos,
                    onOptionSelected = { departamento = it },
                    label = "Departamento",
                    getText = { it.nombre }
                )


                Spacer(modifier = Modifier.height(12.dp))
                PasswordField(value = password, onValueChange = { password = it }, label = "Contraseña")
                Spacer(modifier = Modifier.height(12.dp))
                PasswordField(value = confirmarPassword, onValueChange = { confirmarPassword = it }, label = "Confirmar contraseña")
                Spacer(modifier = Modifier.height(32.dp))

                // Botón de registro
                Button(
                    onClick = {
                        if (password == confirmarPassword && pais != null && departamento != null) {
                            val registroRequest = RegistroRequest(
                                correoelectronico = correo,
                                contrasena = password,
                                nombre = nombreCompleto,
                                apellido = nombreUsuario,
                                pais = pais!!.idpais,
                                provincia = departamento!!.iddepartamento
                            )
                            registroViewModel.registrarUsuario(registroRequest)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(text = "Registrarse", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun <T> DropdownMenuSample(
    selectedOption: T?,
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    label: String,
    getText:(T) -> String = { it.toString()}
) where T : Any {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { expanded = true }
            .padding(horizontal = 8.dp) // Reduce el padding para hacer el Dropdown más compacto
    ) {
        Text(
            text = selectedOption?.let { getText(it) } ?: label,
            modifier = Modifier.padding(8.dp), // Reduce el padding del texto dentro del Dropdown
            color = Color.Gray,
            fontSize = 14.sp // Disminuye el tamaño de la fuente
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.7f) // Reduce el ancho del Dropdown al 90% del contenedor
                .background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(getText(option), fontSize = 14.sp) }, // Mantén el tamaño de fuente pequeño
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier
                        .padding(vertical = 2.dp) // Reduce el espacio entre los ítems
                        .fillMaxWidth()
                )
            }
        }
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
            unfocusedLabelColor = Color.Gray
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
            unfocusedLabelColor = Color.Gray
        )
    )
}